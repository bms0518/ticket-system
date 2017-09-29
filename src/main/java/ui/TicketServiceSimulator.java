package ui;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.javamoney.moneta.Money;

import com.google.common.collect.ImmutableList;

import model.Level;
import model.Seat;
import model.SeatHold;
import model.Venue;
import ticketsystem.DefaultTicketService;
import ticketsystem.TicketReserver;
import ticketsystem.TicketService;

/**
 * Ticket Service Simulator. This will attempt to "simulate" the service being
 * hit by multiple users. Every 100 ms it will check if seats are availble. If
 * so it will randomly request number of seats(up to 10) in random levels. After
 * that there is a 50% chance that it will reserve these seats.
 * 
 * <p>
 * For this simulator there is a 10 second timeout for demonstration purposes.
 * 
 * @author bstoll
 *
 */
public class TicketServiceSimulator {
	private final Random rand = new Random();
	private final Venue venue;
	private final TicketService ticketService;
	private final VenuePane venuePane;

	private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(4);

	/**
	 * Sets up the simulator with default data.
	 */
	public TicketServiceSimulator() {
		Level level1 = new Level(1, "Orchestra", Money.of(100.0, "USD"), 25, 50);
		Level level2 = new Level(2, "Main", Money.of(75.0, "USD"), 20, 100);
		Level level3 = new Level(3, "Balcony 1", Money.of(50.0, "USD"), 15, 100);
		Level level4 = new Level(4, "Balcony 2", Money.of(4.0, "USD"), 15, 100);

		List<Level> levels = ImmutableList.of(level1, level2, level3, level4);

		this.venue = new Venue(1, "Test Venue", levels);
		this.ticketService = new DefaultTicketService(new TicketReserver(venue), 10, TimeUnit.SECONDS,
				new Consumer<SeatHold>() {

					@Override
					public void accept(SeatHold seatHold) {
						if (seatHold != null) {
							for (Seat seat : seatHold.getSeats()) {
								venuePane.clearSeat(seat);
							}
						}
					}
				});

		this.venuePane = new VenuePane(venue);

	}

	/**
	 * @return the venuePane
	 */
	public VenuePane getVenuePane() {
		return venuePane;
	}

	/**
	 * Starts the threads.
	 */
	public void start() {
		executor.scheduleAtFixedRate(new SeatHolder(), 0, 100, TimeUnit.MILLISECONDS);
	}


	private class SeatHolder implements Runnable {

		@Override
		public void run() {
			int numSeatsAvailable = ticketService.numSeatsAvailable(Optional.empty());

			if (numSeatsAvailable > 0) {

				int numSeats = rand.nextInt(10) + 1;

				int range1 = rand.nextInt(4) + 1;
				int range2 = rand.nextInt(4) + 1;

				SeatHold seatHold = ticketService.findAndHoldSeats(numSeats, Optional.of(Math.min(range1, range2)),
						Optional.of(Math.max(range1, range2)), "test@email.com");

				if (seatHold != null) {
					for (Seat seat : seatHold.getSeats()) {
						venuePane.holdSeat(seat);
					}

					// 50% of the time reserve it.
					if (rand.nextDouble() > .5) {
						ticketService.reserveSeats(seatHold.getId(), seatHold.getCustomerEmail());

						for (Seat seat : seatHold.getSeats()) {
							venuePane.reserveSeat(seat);
						}
					}
				}

			}
		}

	}

}
