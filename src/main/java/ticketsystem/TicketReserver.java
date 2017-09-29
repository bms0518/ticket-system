package ticketsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import model.Level;
import model.Seat;
import model.SeatHold;
import model.SeatState;
import model.Venue;

/**
 * Helper class for the Ticket Service. This class is meant to handle all
 * holding and reservation of seats.
 * 
 * <p>
 * This class is not thread safe and is meant to be externally synchronized on.
 * 
 * @author bstoll
 *
 */
public class TicketReserver {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultTicketService.class);

	private final Venue venue;

	private final SortedMap<Seat, SeatState> seatStates = new TreeMap<>();

	private int seatHoldCount = 0;

	/**
	 * Sets up a Ticket Reserver.
	 * 
	 * @param venue
	 *            The Venue to use. Can not be null.
	 */
	public TicketReserver(Venue venue) {
		Preconditions.checkArgument(venue != null, "Invalid Venue, Must not be null");
		this.venue = venue;
		venue.getLevels().forEach((level) -> {
			level.getSeats().forEach((seat) -> {
				seatStates.put(seat, new SeatState());
			});
		});
	}

	/**
	 * Find the Number of seats available in the venue. Optionally find number of
	 * seats for a given level.
	 * 
	 * @param venueLevel
	 *            if present, it will limit search to this venue level. If null it
	 *            will be treated as if it was empty.
	 * @return integer number of seats available. Never negative. 0 if nothing
	 *         found.
	 * @throws IllegalArgumentException
	 *             if Venue does not contain the level passed in.
	 */
	public int numberOfSeatsAvailable(Optional<Integer> venueLevel) {
		LOG.debug("numberOfSeatsAvailable(), venueLevel = {}", venueLevel);
		int numberOfSeatsAvailable = 0;
		if (venueLevel != null && venueLevel.isPresent()) {
			int actualVenueLevel = venueLevel.get();
			if (venue.containsLevelWithId(actualVenueLevel)) {
				Level level = venue.getLevelById(actualVenueLevel);
				numberOfSeatsAvailable = getNumAvailableSeats(level);
			} else {
				throw new IllegalArgumentException("Invalid Level for Venue.");
			}
		} else {
			numberOfSeatsAvailable = getNumAvailableSeatsInVenue();
		}

		return numberOfSeatsAvailable;
	}

	/**
	 * Finds a holds a specific number of seats. Optinally allowed to search on
	 * certain levels. If no min or max is provided then they will default to min
	 * venue level and max venue level respectively.
	 * 
	 * @param numSeats
	 *            The Number of seats to request. Must be greater than 0.
	 * @param minLevel
	 *            The minimum level to search on. Must not be null. Pass in
	 *            Optional.empty() will default to min venue level.
	 * @param maxLevel
	 *            The maximum level to search on. Must not be null. Pass in
	 *            Optional.empty() will default to max venue level.
	 * @param customerEmail
	 *            The customer email to use. Must not be null.
	 * @return SeatHold object containing the seats that were found. This will
	 *         return null if nothing is found.
	 * 
	 * @throws IllegalArgumentException
	 *             If any of the conditions are broken for the arguments.
	 */
	public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel,
			String customerEmail) {
		LOG.debug("findAndHoldSeats(), numSeats = {}, customerEmail = {}", numSeats, customerEmail);
		Preconditions.checkArgument(numSeats > 0, "Invalid numSeats, Number of Seats must be greater than 0");
		Preconditions.checkArgument(customerEmail != null, "Invalid customerEmail, Email can not be null");
		Preconditions.checkArgument(!customerEmail.isEmpty(), "Invalid customerEmail, Email can not be empty");
		Preconditions.checkArgument(minLevel != null,
				"Invalid minLevel, Can not be null. Pass in Optional.empty() to default to min level of venue");
		Preconditions.checkArgument(maxLevel != null,
				"Invalid maxLevel, Can not be null. Pass in Optional.empty() to default to max level of venue");

		int actualMin = getMinLevel(minLevel);
		int actualMax = getMaxLevel(maxLevel);

		Preconditions.checkArgument(actualMax <= venue.getMaxLevel() && actualMax >= venue.getMinLevel(),
				"Invalid Max, Must be less than or equal to venue max and greater than or equal to venue min");
		Preconditions.checkArgument(actualMin >= venue.getMinLevel() && actualMin <= venue.getMaxLevel(),
				"Invalid Min, Must be greater than or equal to venue min and less than or equal to venue max");
		Preconditions.checkArgument(actualMin <= actualMax, "Invalid Min/Max. Min must be less than Max");

		List<Seat> seats = findBestSeats(numSeats, actualMin, actualMax);

		LOG.debug("findAndHoldSeats(), seatsFound = {}", seats.size());

		SeatHold seatHold = null;

		if (seats.size() == numSeats) {
			seatHold = new SeatHold(++seatHoldCount, customerEmail, seats);

			for (Seat seat : seats) {
				SeatState seatState = seatStates.get(seat);
				seatState.hold(seatHold.getId());
			}
		}

		return seatHold;
	}

	/**
	 * Reserve Seats.
	 * 
	 * @param seatHold
	 *            The SeatHold object to reserve(lock in). Must not be null.
	 * @return confirmationNumber for the Reservation.
	 * @throws IllegalArgumentException
	 *             if seatHold is null.
	 * @throws IllegalStateException
	 *             if there is no SeatReservation for the Seat.
	 */
	public String reserveSeats(SeatHold seatHold) {
		LOG.debug("reserveSeats(), seatHold = {}", seatHold);
		Preconditions.checkArgument(seatHold != null, "Invalid seatHold, Must not be null");

		for (Seat seat : seatHold.getSeats()) {
			SeatState seatState = seatStates.get(seat);
			if (seatState != null) {
				seatState.reserve(seatHold.getId());
			} else {
				throw new IllegalStateException("Seat does not exist");
			}
		}

		String confirmationNumber = UUID.randomUUID().toString();

		LOG.debug("reserveSeats(), confirmationNumber = {}", confirmationNumber);

		return confirmationNumber;
	}

	/**
	 * Expire SeatHold. This will void all of the seat reservations that were
	 * contained in this seat hold.
	 * 
	 * @param seatHold
	 *            The SeatHold to expire. Must not be null.
	 * 
	 */
	public void expireSeatHold(SeatHold seatHold) {
		LOG.debug("expireSeatHold(), seatHold = {}", seatHold);
		Preconditions.checkArgument(seatHold != null, "Invalid seatHold, Must not be null");

		for (Seat seat : seatHold.getSeats()) {
			SeatState seatState = seatStates.get(seat);
			if (seatState != null) {

				// Verify they actually have the reservation on this seat.
				if (seatState.getSeatHoldId() == seatHold.getId()) {
					seatState.clear();
				} else {
					LOG.debug("expireSeatHold(), attempted to wipe seat that doesnt belong to seathold with id = {}",
							seatHold.getId());
					throw new IllegalStateException("Reservation doesnt belong to SeatHold");
				}

			} else {
				throw new IllegalStateException("Seat does not actually exist");
			}
		}
	}

	// Loop through each level starting at the minimum. Find best seats then
	// continue up higher to fufill order.
	private List<Seat> findBestSeats(int numSeatsToFind, int minLevel, int maxLevel) {

		List<Seat> seats = new ArrayList<>();

		boolean seatsFound = false;

		int seatsRemaining = numSeatsToFind - seats.size();

		for (int levelId = minLevel; levelId <= maxLevel && !seatsFound; levelId++) {
			Level level = venue.getLevelById(levelId);
			int numSeatsAvailable = getNumAvailableSeats(level);

			if (numSeatsAvailable == 0) {
				continue;
			}
			// Need to take all seats on lowest, then fill in from next row.
			List<Seat> allSeatsAtThisLevel = getAllAvailableSeats(level);

			// Find number of seats to take here.
			int numSeatsToTakeAtThisLevel = (numSeatsAvailable >= seatsRemaining) ? seatsRemaining : numSeatsAvailable;

			for (int i = 0; i < numSeatsToTakeAtThisLevel; i++) {
				seats.add(allSeatsAtThisLevel.get(i));
			}

			seatsRemaining = numSeatsToFind - seats.size();
			seatsFound = seatsRemaining == 0;
		}

		return seats;
	}

	private int getNumAvailableSeatsInVenue() {
		int numberOfSeatsAvaliableInVenue = 0;
		for (int i = venue.getMinLevel(); i <= venue.getMaxLevel(); i++) {
			Level level = venue.getLevelById(i);
			numberOfSeatsAvaliableInVenue += getNumAvailableSeats(level);
		}

		return numberOfSeatsAvaliableInVenue;
	}

	private int getNumAvailableSeats(Level level) {
		int numSeatsAvailable = getAllAvailableSeats(level).size();
		return numSeatsAvailable;
	}

	private List<Seat> getAllAvailableSeats(Level level) {
		List<Seat> allAvailableSeats = seatStates.entrySet().stream().filter(filterOutOtherLevels(level))
				.filter(isSeatAvailable()).map(Map.Entry::getKey).collect(Collectors.toList());
		return allAvailableSeats;
	}

	private Predicate<Entry<Seat, SeatState>> isSeatAvailable() {
		return (entry) -> {
			return entry.getValue().isAvailable();
		};
	}

	private Predicate<Entry<Seat, SeatState>> filterOutOtherLevels(Level level) {
		return (entry) -> {
			return level.getSeats().contains(entry.getKey());
		};
	}

	// Need to check that max level is present. If not set to max level of venue.
	private int getMaxLevel(Optional<Integer> maxLevelOptional) {
		return maxLevelOptional.isPresent() ? maxLevelOptional.get() : venue.getMaxLevel();
	}

	// Need to check that min level is present. If not set to min level of venue.
	private int getMinLevel(Optional<Integer> minLevelOptional) {
		return minLevelOptional.isPresent() ? minLevelOptional.get() : venue.getMinLevel();
	}

}
