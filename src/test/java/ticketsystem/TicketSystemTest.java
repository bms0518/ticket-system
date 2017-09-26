package ticketsystem;

import java.util.Optional;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import model.Level;
import model.Venue;

public class TicketSystemTest {

	public static void main(String[] args) {

		Level level1 = new Level("Orchestra", 100.0, 25, 50);
		Level level2 = new Level("Main", 75.00, 20, 100);
		Level level3 = new Level("Balcony 1", 50.00, 15, 100);
		Level level4 = new Level("Balcony 2", 4.00, 15, 100);

		Set<Level> levels = ImmutableSet.of(level1, level2, level3, level4);

		Venue venue = new Venue(1, "Verizon Center", levels);
		TicketService ticketService = new SingleVenueTicketService(venue);

		int numAvailable = ticketService.numSeatsAvailable(Optional.of(1));
		System.err.println("Num Available = " + numAvailable);

		ticketService.findAndHoldSeats(1250, Optional.of(4), Optional.of(4), "bstoll@gmail.com");

		int numAvailableNew = ticketService.numSeatsAvailable(Optional.of(4));
		System.err.println("Num Available = " + numAvailableNew);

	}

}
