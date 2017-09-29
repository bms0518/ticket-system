package ticketsystem;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import model.SeatHold;

/**
 * Default Ticket Service. Allows holding and reserving tickets.
 * 
 * 
 * @author bstoll
 *
 */
public class DefaultTicketService implements TicketService {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultTicketService.class);

	private final TicketReserver ticketReserver;

	private final ConcurrentHashMap<Integer, TemporarySeatHold> tempSeatHolds = new ConcurrentHashMap<>();

	private final ConcurrentHashMap<Integer, ScheduledFuture<?>> tempSeatHoldFutures = new ConcurrentHashMap<>();

	private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

	private final long expirationTime;

	private final TimeUnit expirationUnits;

	/**
	 * Sets up Default Ticket Service. This will use default expiration time of 2
	 * minutes.
	 * 
	 * @param ticketReserver
	 *            The TicketReserver. Must not be null.
	 * @throws IllegalArgumentException
	 *             if ticketReserver is null.
	 */
	public DefaultTicketService(TicketReserver ticketReserver) {
		this(ticketReserver, 2, TimeUnit.MINUTES);
	}

	/**
	 * Sets up Default Ticket Service.
	 * 
	 * @param ticketReserver
	 *            The TicketReserver. Must not be null.
	 * @param expirationTime
	 *            The time it takes to expire. This is in combination with TimeUnit.
	 *            Must be greater than 0.
	 * @param expirationUnits
	 *            The TimeUnit for expiration. Must not be null.
	 * @throws IllegalArgumentException
	 *             if any of the constraints are invalidated.
	 */
	public DefaultTicketService(TicketReserver ticketReserver, long expirationTime, TimeUnit expirationUnits) {
		Preconditions.checkArgument(ticketReserver != null, "Invalid ticketReserver. Must not be null");
		Preconditions.checkArgument(expirationTime > 0, "Invalid expirationTime. Must be greater than 0");
		Preconditions.checkArgument(expirationUnits != null, "Invalid TimeUnit for Expiration. Must not be null");
		this.expirationTime = expirationTime;
		this.expirationUnits = expirationUnits;
		this.ticketReserver = ticketReserver;
	}

	@Override
	public int numSeatsAvailable(Optional<Integer> venueLevel) {
		LOG.debug("numSeatsAvailable(), venueLevel = {}", venueLevel);
		synchronized (ticketReserver) {
			return ticketReserver.numberOfSeatsAvailable(venueLevel);
		}
	}

	@Override
	public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel,
			String customerEmail) {
		LOG.debug("findAndHoldSeats()");

		synchronized (ticketReserver) {

			SeatHold seatHold = ticketReserver.findAndHoldSeats(numSeats, minLevel, maxLevel, customerEmail);
			if (seatHold != null) {
				TemporarySeatHold tempSeatHold = new TemporarySeatHold(seatHold);

				tempSeatHolds.put(seatHold.getId(), tempSeatHold);

				// Expire this SeatHold after set time.
				ScheduledFuture<?> tempSeatHoldFuture = executor.schedule(tempSeatHold, expirationTime,
						expirationUnits);
				tempSeatHoldFutures.put(seatHold.getId(), tempSeatHoldFuture);
			}

			return seatHold;
		}
	}

	@Override
	public String reserveSeats(int seatHoldId, String customerEmail) {
		LOG.debug("reserveSeats(), seatHold = {}, customerEmail ={}", seatHoldId, customerEmail);

		Preconditions.checkArgument(seatHoldId > 0, "Invalid Seat ID, Must be greater than 0");
		Preconditions.checkArgument(customerEmail != null, "Invalid Customer Email, Must not be null");
		Preconditions.checkArgument(!customerEmail.isEmpty(), "Invalid Customer Email, Must not be null");

		TemporarySeatHold tempSeatHold = tempSeatHolds.get(seatHoldId);
		if (tempSeatHold != null) {
			SeatHold seatHold = tempSeatHold.seatHold;

			cancelSeatHoldExpiration(seatHold);

			if (seatHold.getCustomerEmail().equals(customerEmail)) {
				synchronized (ticketReserver) {
					return ticketReserver.reserveSeats(seatHold);
				}
			} else {
				throw new IllegalStateException("Customer Email does not match passed in ID");
			}
		} else {
			throw new IllegalStateException("Seat Hold expired");
		}
	}

	private void cancelSeatHoldExpiration(SeatHold seatHold) {
		tempSeatHolds.remove(seatHold.getId());
		ScheduledFuture<?> seatHoldFuture = tempSeatHoldFutures.remove(seatHold.getId());
		if (seatHoldFuture != null) {
			seatHoldFuture.cancel(true);
		}
	}

	/**
	 * A Temporary SeatHold is one that is meant to expire. When this is run the
	 * SeatHold stored will expire.
	 * 
	 * 
	 * @author bstoll
	 *
	 */
	private class TemporarySeatHold implements Runnable {

		private final SeatHold seatHold;

		private TemporarySeatHold(SeatHold seatHold) {
			this.seatHold = seatHold;
		}

		@Override
		public void run() {
			synchronized (ticketReserver) {
				ticketReserver.expireSeatHold(seatHold);
			}
		}

	}

}
