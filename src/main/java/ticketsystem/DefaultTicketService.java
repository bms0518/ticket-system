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
 * @author bstoll
 *
 */
public class DefaultTicketService implements TicketService {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultTicketService.class);

	private final TicketReserver ticketReserver;

	private final ConcurrentHashMap<Integer, TemporarySeatHold> tempSeatHolds = new ConcurrentHashMap<>();

	private final ConcurrentHashMap<Integer, ScheduledFuture<?>> tempSeatHoldFutures = new ConcurrentHashMap<>();

	private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

	public DefaultTicketService(TicketReserver ticketReserver) {
		Preconditions.checkArgument(ticketReserver != null, "Invalid ticketReserver. Must not be null");
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
			TemporarySeatHold tempSeatHold = new TemporarySeatHold(seatHold);

			tempSeatHolds.put(seatHold.getId(), tempSeatHold);
			// Expire this SeatHold in 2 minutes.
			ScheduledFuture<?> tempSeatHoldFuture = executor.schedule(tempSeatHold, 2, TimeUnit.MINUTES);
			tempSeatHoldFutures.put(seatHold.getId(), tempSeatHoldFuture);

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

	private class TemporarySeatHold implements Runnable {

		private final SeatHold seatHold;

		private TemporarySeatHold(SeatHold seatHold) {
			Preconditions.checkArgument(seatHold != null);
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
