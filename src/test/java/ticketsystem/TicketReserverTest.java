package ticketsystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import model.Seat;
import model.SeatHold;
import util.TestObjectFactory;

/**
 * Ticket Reserver Unit Tests.
 * 
 * @author bstoll
 *
 */
public class TicketReserverTest {

	@Test
	public void validConstruction() {
		TicketReserver reserver = TestObjectFactory.newReserver();
		assertNotNull(reserver);
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullVenueOnConstructionShouldThrowIllegalArgument() {
		new TicketReserver(null);
	}

	@Test
	public void numberOfSeatsAvailable() {
		TicketReserver reserver = TestObjectFactory.newReserver();

		int numberOfSeatsLevel1 = reserver.numberOfSeatsAvailable(Optional.of(1));
		assertEquals(TestObjectFactory.LEVEL_1_TOTAL_SEATS, numberOfSeatsLevel1);

		int numberOfSeatsLevel2 = reserver.numberOfSeatsAvailable(Optional.of(2));
		assertEquals(TestObjectFactory.LEVEL_2_TOTAL_SEATS, numberOfSeatsLevel2);

		int numberOfSeatsLevel3 = reserver.numberOfSeatsAvailable(Optional.of(3));
		assertEquals(TestObjectFactory.LEVEL_3_TOTAL_SEATS, numberOfSeatsLevel3);

		int numberOfSeatsLevel4 = reserver.numberOfSeatsAvailable(Optional.of(4));
		assertEquals(TestObjectFactory.LEVEL_4_TOTAL_SEATS, numberOfSeatsLevel4);
	}

	@Test
	public void testFindAndHoldAllSeats() {
		TicketReserver reserver = TestObjectFactory.newReserver();
		reserver.findAndHoldSeats(TestObjectFactory.TOTAL_SEATS_IN_VENUE, Optional.of(1), Optional.of(4),
				TestObjectFactory.TEST_EMAIL);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindAndHoldAllSeatsInvalidRange() {
		TicketReserver reserver = TestObjectFactory.newReserver();
		reserver.findAndHoldSeats(TestObjectFactory.TOTAL_SEATS_IN_VENUE, Optional.of(5), Optional.of(4),
				TestObjectFactory.TEST_EMAIL);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindAndHoldAllSeatsMinGreaterThanVenueMax() {
		TicketReserver reserver = TestObjectFactory.newReserver();
		reserver.findAndHoldSeats(TestObjectFactory.TOTAL_SEATS_IN_VENUE, Optional.of(5), Optional.of(4),
				TestObjectFactory.TEST_EMAIL);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindAndHoldAllSeatsMinLessThanVenueMin() {
		TicketReserver reserver = TestObjectFactory.newReserver();
		reserver.findAndHoldSeats(TestObjectFactory.TOTAL_SEATS_IN_VENUE, Optional.of(0), Optional.of(4),
				TestObjectFactory.TEST_EMAIL);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindAndHoldAllSeatsMaxLessThanVenueMin() {
		TicketReserver reserver = TestObjectFactory.newReserver();
		reserver.findAndHoldSeats(TestObjectFactory.TOTAL_SEATS_IN_VENUE, Optional.of(1), Optional.of(0),
				TestObjectFactory.TEST_EMAIL);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindAndHoldAllSeatsMaxGreaterThanVenueMax() {
		TicketReserver reserver = TestObjectFactory.newReserver();
		reserver.findAndHoldSeats(TestObjectFactory.TOTAL_SEATS_IN_VENUE, Optional.of(1), Optional.of(5),
				TestObjectFactory.TEST_EMAIL);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindAndHoldAllSeatsMinHigherThanMax() {
		TicketReserver reserver = TestObjectFactory.newReserver();
		reserver.findAndHoldSeats(TestObjectFactory.TOTAL_SEATS_IN_VENUE, Optional.of(3), Optional.of(2),
				TestObjectFactory.TEST_EMAIL);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindAndHoldAllSeatsNullMin() {
		TicketReserver reserver = TestObjectFactory.newReserver();
		reserver.findAndHoldSeats(TestObjectFactory.TOTAL_SEATS_IN_VENUE, null, Optional.of(4),
				TestObjectFactory.TEST_EMAIL);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindAndHoldAllSeatsNullMax() {
		TicketReserver reserver = TestObjectFactory.newReserver();
		reserver.findAndHoldSeats(TestObjectFactory.TOTAL_SEATS_IN_VENUE, Optional.of(1), null,
				TestObjectFactory.TEST_EMAIL);
	}

	@Test
	public void testFindAndHoldAllSeatsEmptyConstraints() {
		TicketReserver reserver = TestObjectFactory.newReserver();
		reserver.findAndHoldSeats(TestObjectFactory.TOTAL_SEATS_IN_VENUE, Optional.empty(), Optional.empty(),
				TestObjectFactory.TEST_EMAIL);
	}

	@Test(expected = RuntimeException.class)
	public void testFindAndHoldAllSeatsPlus1() {
		TicketReserver reserver = TestObjectFactory.newReserver();
		reserver.findAndHoldSeats(TestObjectFactory.TOTAL_SEATS_IN_VENUE + 1, Optional.of(1), Optional.of(4),
				TestObjectFactory.TEST_EMAIL);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindAndHoldSeatsZeroSeats() {
		TicketReserver reserver = TestObjectFactory.newReserver();
		reserver.findAndHoldSeats(0, Optional.of(1), Optional.of(4), TestObjectFactory.TEST_EMAIL);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindAndHoldSeatsNegativeSeats() {
		TicketReserver reserver = TestObjectFactory.newReserver();
		reserver.findAndHoldSeats(-1, Optional.of(1), Optional.of(4), TestObjectFactory.TEST_EMAIL);
	}

	@Test
	public void testFindAndHoldSeatsFirstLevelAlreadyTaken() {
		TicketReserver reserver = TestObjectFactory.newReserver();
		SeatHold seatHold1 = reserver.findAndHoldSeats(TestObjectFactory.LEVEL_1_TOTAL_SEATS, Optional.of(1),
				Optional.of(4), TestObjectFactory.TEST_EMAIL);
		SeatHold seatHold2 = reserver.findAndHoldSeats(TestObjectFactory.LEVEL_1_TOTAL_SEATS, Optional.of(1),
				Optional.of(4), TestObjectFactory.TEST_EMAIL);

		assertNotNull(seatHold1);
		assertNotNull(seatHold2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindAndHoldSeatsEmptyEmail() {
		TicketReserver reserver = TestObjectFactory.newReserver();
		reserver.findAndHoldSeats(1, Optional.of(1), Optional.of(4), "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindAndHoldSeatsNullEmail() {
		TicketReserver reserver = TestObjectFactory.newReserver();
		reserver.findAndHoldSeats(1, Optional.of(1), Optional.of(4), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidLevelShouldThrowIllegalArgument() {
		TicketReserver reserver = TestObjectFactory.newReserver();
		reserver.numberOfSeatsAvailable(Optional.of(5));
	}

	@Test
	public void nullOptionalLevelShouldReturnTotalSeatsInVenue() {
		TicketReserver reserver = TestObjectFactory.newReserver();
		int numberOfSeatsInVenue = reserver.numberOfSeatsAvailable(null);
		assertEquals(TestObjectFactory.TOTAL_SEATS_IN_VENUE, numberOfSeatsInVenue);
	}

	@Test
	public void emptyOptionalLevelShouldReturnTotalSeatsInVenue() {
		TicketReserver reserver = TestObjectFactory.newReserver();
		int numberOfSeatsInVenue = reserver.numberOfSeatsAvailable(Optional.empty());
		assertEquals(TestObjectFactory.TOTAL_SEATS_IN_VENUE, numberOfSeatsInVenue);
	}

	@Test
	public void reserveSeats() {
		TicketReserver reserver = TestObjectFactory.newReserver();
		SeatHold seatHold = reserver.findAndHoldSeats(10, Optional.empty(), Optional.empty(),
				TestObjectFactory.TEST_EMAIL);
		String confirmationNumber = reserver.reserveSeats(seatHold);
		assertNotNull(confirmationNumber);
	}

	@Test(expected = IllegalArgumentException.class)
	public void reserveSeatsNullSeatHold() {
		TicketReserver reserver = TestObjectFactory.newReserver();
		SeatHold seatHold = null;
		String confirmationNumber = reserver.reserveSeats(seatHold);
		assertNotNull(confirmationNumber);
	}

	@Test(expected = IllegalStateException.class)
	public void reserveSeatsFakeSeatHold() {
		TicketReserver reserver = TestObjectFactory.newReserver();

		// Make a seat that i dont actually have a hold to.
		Seat seat = new Seat(3, 3, 3, 22.00);

		List<Seat> seats = new ArrayList<>();
		seats.add(seat);

		SeatHold seatHold = new SeatHold(66, TestObjectFactory.TEST_EMAIL, seats);
		reserver.reserveSeats(seatHold);

	}

	@Test(expected = IllegalStateException.class)
	public void reserveSeatsFakeSeatAndSeatHold() {
		TicketReserver reserver = TestObjectFactory.newReserver();

		// Make a seat that doesnt actually exist
		// Level 6 doesnt exist.
		Seat seat = new Seat(6, 6, 6, 22.00);

		List<Seat> seats = new ArrayList<>();
		seats.add(seat);

		SeatHold seatHold = new SeatHold(66, TestObjectFactory.TEST_EMAIL, seats);
		reserver.reserveSeats(seatHold);

	}

	@Test
	public void expireSeatHold() {
		TicketReserver reserver = TestObjectFactory.newReserver();
		SeatHold seatHold1 = reserver.findAndHoldSeats(100, Optional.of(1), Optional.of(4),
				TestObjectFactory.TEST_EMAIL);
		reserver.expireSeatHold(seatHold1);

	}

	@Test(expected = IllegalStateException.class)
	public void expireSeatHoldBadSeatHold() {
		TicketReserver reserver = TestObjectFactory.newReserver();
		// Make a seat that i dont actually have a hold to.
		Seat seat = new Seat(3, 3, 3, 22.00);

		List<Seat> seats = new ArrayList<>();
		seats.add(seat);

		SeatHold seatHold = new SeatHold(66, TestObjectFactory.TEST_EMAIL, seats);
		reserver.expireSeatHold(seatHold);

	}

	@Test(expected = IllegalStateException.class)
	public void expireSeatHoldBadSeatAndSeatHold() {
		TicketReserver reserver = TestObjectFactory.newReserver();

		// Make a seat that doesnt actually exist
		// Level 6 doesnt exist.
		Seat seat = new Seat(6, 6, 6, 22.00);

		List<Seat> seats = new ArrayList<>();
		seats.add(seat);

		SeatHold seatHold = new SeatHold(66, TestObjectFactory.TEST_EMAIL, seats);
		reserver.expireSeatHold(seatHold);

	}

	@Test(expected = IllegalArgumentException.class)
	public void expireSeatHoldNullSeatHold() {
		TicketReserver reserver = TestObjectFactory.newReserver();
		reserver.expireSeatHold(null);

	}

}
