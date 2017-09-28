package ticketsystem;

import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Test;

import model.SeatHold;
import util.TestObjectFactory;

/**
 * Unit Tests for Ticket Service.
 * 
 * @author bstoll
 *
 */
public class TicketServiceTest {

	@Test
	public void validConstruction() {
		TicketService ticketService = TestObjectFactory.newService();
		assertNotNull(ticketService);
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullReserverOnConstruction() {
		new DefaultTicketService(null);
	}

	@Test
	public void testNumSeatsAvailable() {
		TicketService ticketService = TestObjectFactory.newService();
		ticketService.numSeatsAvailable(Optional.of(1));
	}

	@Test
	public void testFindAndHold() {
		TicketService ticketService = TestObjectFactory.newService();
		ticketService.findAndHoldSeats(1, Optional.empty(), Optional.empty(), TestObjectFactory.TEST_EMAIL);
	}

	@Test
	public void testReserve() {
		TicketService ticketService = TestObjectFactory.newService();
		SeatHold seatHold = ticketService.findAndHoldSeats(1, Optional.empty(), Optional.empty(),
				TestObjectFactory.TEST_EMAIL);
		ticketService.reserveSeats(seatHold.getId(), seatHold.getCustomerEmail());
	}

	// public static void main(String[] args) throws InterruptedException {
	//
	// TicketReserver ticketReserver = new TicketReserver(VENUE);
	// TicketService ticketSystem = new DefaultTicketService(ticketReserver);
	//
	// Thread t1 = new Thread(() -> {
	// SeatHold seatHold = ticketSystem.findAndHoldSeats(500, Optional.of(1),
	// Optional.of(4),
	// TestObjectFactory.TEST_EMAIL);
	// System.err.println(seatHold);
	//
	// });
	//
	// Thread t2 = new Thread(() -> {
	// SeatHold seatHold = ticketSystem.findAndHoldSeats(500, Optional.of(1),
	// Optional.of(4),
	// TestObjectFactory.TEST_EMAIL);
	// System.err.println(seatHold);
	// });
	//
	// Thread t3 = new Thread(() -> {
	// SeatHold seatHold = ticketSystem.findAndHoldSeats(500, Optional.of(1),
	// Optional.of(4),
	// TestObjectFactory.TEST_EMAIL);
	// System.err.println(seatHold);
	// });
	//
	// Thread t4 = new Thread(() -> {
	// SeatHold seatHold = ticketSystem.findAndHoldSeats(500, Optional.of(1),
	// Optional.of(4),
	// TestObjectFactory.TEST_EMAIL);
	// System.err.println(seatHold);
	// });
	//
	// Thread t5 = new Thread(() -> {
	// SeatHold seatHold = ticketSystem.findAndHoldSeats(500, Optional.of(1),
	// Optional.of(4),
	// TestObjectFactory.TEST_EMAIL);
	// System.err.println(seatHold);
	// });
	//
	// Thread t6 = new Thread(() -> {
	// SeatHold seatHold = ticketSystem.findAndHoldSeats(500, Optional.of(1),
	// Optional.of(4),
	// TestObjectFactory.TEST_EMAIL);
	// System.err.println(seatHold);
	// });
	//
	// Thread t7 = new Thread(() -> {
	// SeatHold seatHold = ticketSystem.findAndHoldSeats(500, Optional.of(1),
	// Optional.of(4),
	// TestObjectFactory.TEST_EMAIL);
	// System.err.println(seatHold);
	// });
	//
	// t1.start();
	// Thread.sleep(10005);
	// t2.start();
	// Thread.sleep(10005);
	// t3.start();
	// Thread.sleep(10005);
	// t4.start();
	// Thread.sleep(10005);
	// t5.start();
	// Thread.sleep(10005);
	// t6.start();
	// Thread.sleep(10005);
	// t7.start();
	//
	// }

}
