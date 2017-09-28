package ticketsystem;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableList;

import model.Level;
import model.SeatHold;
import model.Venue;

public class TicketSystemTest {

	
	
	
	private static final List<Level> TEST_LEVELS;

	static {
		Level level1 = new Level(1, "Orchestra", 100.0, 25, 50);
		Level level2 = new Level(2, "Main", 75.00, 20, 100);
		Level level3 = new Level(3, "Balcony 1", 50.00, 15, 100);
		Level level4 = new Level(4, "Balcony 2", 4.00, 15, 100);

		TEST_LEVELS = ImmutableList.of(level1, level2, level3, level4);
	}

	private static final Venue VENUE = new Venue(1, "Test", TEST_LEVELS);

	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws InterruptedException {

		TicketReserver ticketReserver = new TicketReserver(VENUE);
		TicketService ticketSystem = new DefaultTicketService(ticketReserver);

		Thread t1 = new Thread(() -> {
			SeatHold seatHold = ticketSystem.findAndHoldSeats(500, Optional.of(1), Optional.of(4), "test@email.com");
			System.err.println(seatHold);

		});

		Thread t2 = new Thread(() -> {
			SeatHold seatHold = ticketSystem.findAndHoldSeats(500, Optional.of(1), Optional.of(4), "test@email.com");
			System.err.println(seatHold);
		});

		Thread t3 = new Thread(() -> {
			SeatHold seatHold = ticketSystem.findAndHoldSeats(500, Optional.of(1), Optional.of(4), "test@email.com");
			System.err.println(seatHold);
		});

		Thread t4 = new Thread(() -> {
			SeatHold seatHold = ticketSystem.findAndHoldSeats(500, Optional.of(1), Optional.of(4), "test@email.com");
			System.err.println(seatHold);
		});

		Thread t5 = new Thread(() -> {
			SeatHold seatHold = ticketSystem.findAndHoldSeats(500, Optional.of(1), Optional.of(4), "test@email.com");
			System.err.println(seatHold);
		});

		Thread t6 = new Thread(() -> {
			SeatHold seatHold = ticketSystem.findAndHoldSeats(500, Optional.of(1), Optional.of(4), "test@email.com");
			System.err.println(seatHold);
		});

		Thread t7 = new Thread(() -> {
			SeatHold seatHold = ticketSystem.findAndHoldSeats(500, Optional.of(1), Optional.of(4), "test@email.com");
			System.err.println(seatHold);
		});

		t1.start();
		Thread.sleep(10005);
		t2.start();
		Thread.sleep(10005);
		t3.start();
		Thread.sleep(10005);
		t4.start();
		Thread.sleep(10005);
		t5.start();
		Thread.sleep(10005);
		t6.start();
		Thread.sleep(10005);
		t7.start();

	}

}
