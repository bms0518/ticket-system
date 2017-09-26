package ticketsystem;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import model.Level;
import model.SeatHold;
import model.Venue;

public class SingleVenueTicketService implements TicketService {

	private final Venue venue;

	private final Multimap<Integer, SeatHold> seatHoldsByLevel = HashMultimap.create();

	private final Multimap<Integer, SeatHold> seatReservationsByLevel = HashMultimap.create();

	private final Map<String, String> seatHoldIdByConfirmation = new HashMap<>();

	private int seatHoldCount = 0;

	public SingleVenueTicketService(Venue venue) {
		Preconditions.checkArgument(venue != null, "Invalid Venue. Must not be null");
		this.venue = venue;

	}

	@Override
	public int numSeatsAvailable(Optional<Integer> venueLevel) {

		int numberOfSeatsAvailable = 0;

		if (venueLevel != null && venueLevel.isPresent()) {
			int actualVenueLevel = venueLevel.get();
			if (venue.containsLevel(actualVenueLevel)) {
				numberOfSeatsAvailable = getNumSeatsAvailableByLevel(actualVenueLevel);
			} else {
				throw new IllegalArgumentException("Invalid Venue Level, Venue does not contain Level with that ID");
			}
		} else {
			for (int i = venue.getMinLevel(); i <= venue.getMaxLevel(); i++) {
				numberOfSeatsAvailable += getNumSeatsAvailableByLevel(i);
			}
		}

		return numberOfSeatsAvailable;
	}

	@Override
	public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel,
			String customerEmail) {
		Preconditions.checkArgument(numSeats > 0, "Invalid numSeats, Number of Seats must be greater than 0");
		Preconditions.checkArgument(customerEmail != null, "Invalid customerEmail, Email can not be null");
		Preconditions.checkArgument(!customerEmail.isEmpty(), "Invalid customerEmail, Email can not be empty");

		int actualMin = minLevel.orElseGet(venue::getMinLevel);
		int actualMax = maxLevel.orElseGet(venue::getMaxLevel);

		SeatHold seatHold = null;

		boolean seatFound = false;

		for (int i = actualMin; i <= actualMax && !seatFound; i++) {
			int numSeatsAvailable = getNumSeatsAvailableByLevel(i);
			if (numSeats <= numSeatsAvailable) {
				// Found best seats.
				seatHold = new SeatHold(++seatHoldCount, customerEmail, numSeats, venue.getId(), i);
				seatHoldsByLevel.put(i, seatHold);
				seatFound = true;
			}
		}

		return seatHold;
	}

	@Override
	public String reserveSeats(int seatHoldId, String customerEmail) {
		// TODO Auto-generated method stub
		return null;
	}

	private int getNumSeatsAvailableByLevel(int levelId) {
		int numberHeld = getNumberOfSeatsHeldByLevel(levelId);
		int numberReserved = getNumberSeatsReservedByLevel(levelId);

		int numberReservedAndHeld = numberHeld + numberReserved;

		Level level = venue.getLevel(levelId);
		int numAvailable = level.getTotalNumberOfSeats() - numberReservedAndHeld;

		return numAvailable;
	}

	private int getNumberSeatsReservedByLevel(int level) {
		Collection<SeatHold> reservedByLevel = seatReservationsByLevel.get(level);
		int sum = SeatHold.sum(reservedByLevel);
		return sum;
	}

	private int getNumberOfSeatsHeldByLevel(int level) {
		Collection<SeatHold> holdsForLevel = seatHoldsByLevel.get(level);
		int sum = SeatHold.sum(holdsForLevel);
		return sum;
	}

}
