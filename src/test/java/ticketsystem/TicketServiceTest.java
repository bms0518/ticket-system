package ticketsystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

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

	@Test(expected = IllegalArgumentException.class)
	public void negativeExpirationTimeOnConstruction() {
		new DefaultTicketService(TestObjectFactory.newReserver(), -1, TimeUnit.SECONDS);
	}

	@Test(expected = IllegalArgumentException.class)
	public void zeroExpirationTimeOnConstruction() {
		new DefaultTicketService(TestObjectFactory.newReserver(), 0, TimeUnit.SECONDS);
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullExpirationUnitOnConstruction() {
		new DefaultTicketService(TestObjectFactory.newReserver(), 1, null);
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

	@Test(expected = IllegalArgumentException.class)
	public void negativeIdOnReserveShouldThrowIllegalArgument() {
		TicketService ticketService = TestObjectFactory.newService();
		SeatHold seatHold = ticketService.findAndHoldSeats(1, Optional.empty(), Optional.empty(),
				TestObjectFactory.TEST_EMAIL);
		ticketService.reserveSeats(-1, seatHold.getCustomerEmail());
	}

	@Test(expected = IllegalArgumentException.class)
	public void zeroIdOnReserveShouldThrowIllegalArgument() {
		TicketService ticketService = TestObjectFactory.newService();
		SeatHold seatHold = ticketService.findAndHoldSeats(1, Optional.empty(), Optional.empty(),
				TestObjectFactory.TEST_EMAIL);
		ticketService.reserveSeats(0, seatHold.getCustomerEmail());
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullEmailOnReserveShouldThrowIllegalArgument() {
		TicketService ticketService = TestObjectFactory.newService();
		SeatHold seatHold = ticketService.findAndHoldSeats(1, Optional.empty(), Optional.empty(),
				TestObjectFactory.TEST_EMAIL);
		ticketService.reserveSeats(seatHold.getId(), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void emptyEmailOnReserveShouldThrowIllegalArgument() {
		TicketService ticketService = TestObjectFactory.newService();
		SeatHold seatHold = ticketService.findAndHoldSeats(1, Optional.empty(), Optional.empty(),
				TestObjectFactory.TEST_EMAIL);
		ticketService.reserveSeats(seatHold.getId(), "");
	}

	@Test(expected = IllegalStateException.class)
	public void badEmailOnReserveShouldThrowIllegalArgument() {
		TicketService ticketService = TestObjectFactory.newService();
		SeatHold seatHold = ticketService.findAndHoldSeats(1, Optional.empty(), Optional.empty(),
				TestObjectFactory.TEST_EMAIL);
		ticketService.reserveSeats(seatHold.getId(), "fake@email.com");
	}

	@Test(expected = IllegalStateException.class)
	public void noHoldsBeforeReserveShouldThrowIllegalState() {
		TicketService ticketService = TestObjectFactory.newService();
		ticketService.reserveSeats(1, TestObjectFactory.TEST_EMAIL);
	}

	@Test
	public void testReserveTooManySeats() throws InterruptedException {
		TicketService ticketService = new DefaultTicketService(TestObjectFactory.newReserver(), 5, TimeUnit.SECONDS);
		SeatHold seatHold = ticketService.findAndHoldSeats(TestObjectFactory.TOTAL_SEATS_IN_VENUE + 1, Optional.empty(),
				Optional.empty(), TestObjectFactory.TEST_EMAIL);

		assertNull(seatHold);
	}

	@Test
	public void testExpiration() throws InterruptedException {
		TicketService ticketService = new DefaultTicketService(TestObjectFactory.newReserver(), 2, TimeUnit.SECONDS);
		ticketService.findAndHoldSeats(1, Optional.empty(), Optional.empty(), TestObjectFactory.TEST_EMAIL);
		assertEquals(TestObjectFactory.TOTAL_SEATS_IN_VENUE - 1, ticketService.numSeatsAvailable(Optional.empty()));

		Thread.sleep(3000);

		assertEquals(TestObjectFactory.TOTAL_SEATS_IN_VENUE, ticketService.numSeatsAvailable(Optional.empty()));

	}

}
