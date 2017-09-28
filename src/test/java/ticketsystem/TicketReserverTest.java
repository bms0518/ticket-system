package ticketsystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

import model.Level;
import model.Seat;
import model.SeatHold;
import model.Venue;

/**
 * Ticket Reserver Unit Tests.
 * 
 * @author bstoll
 *
 */
public class TicketReserverTest {

	private static final int LEVEL_1_ROWS = 25;
	private static final int LEVEL_1_SEATS_PER_ROW = 50;
	private static final int LEVEL_1_TOTAL_SEATS = LEVEL_1_ROWS * LEVEL_1_SEATS_PER_ROW;

	private static final int LEVEL_2_ROWS = 20;
	private static final int LEVEL_2_SEATS_PER_ROW = 100;
	private static final int LEVEL_2_TOTAL_SEATS = LEVEL_2_ROWS * LEVEL_2_SEATS_PER_ROW;

	private static final int LEVEL_3_ROWS = 15;
	private static final int LEVEL_3_SEATS_PER_ROW = 100;
	private static final int LEVEL_3_TOTAL_SEATS = LEVEL_3_ROWS * LEVEL_3_SEATS_PER_ROW;

	private static final int LEVEL_4_ROWS = 15;
	private static final int LEVEL_4_SEATS_PER_ROW = 100;
	private static final int LEVEL_4_TOTAL_SEATS = LEVEL_4_ROWS * LEVEL_4_SEATS_PER_ROW;

	private static final int TOTAL_SEATS_IN_VENUE = LEVEL_1_TOTAL_SEATS + LEVEL_2_TOTAL_SEATS + LEVEL_3_TOTAL_SEATS
			+ LEVEL_4_TOTAL_SEATS;

	private static final List<Level> TEST_LEVELS;

	static {
		Level level1 = new Level(1, "Orchestra", 100.0, LEVEL_1_ROWS, LEVEL_1_SEATS_PER_ROW);
		Level level2 = new Level(2, "Main", 75.00, LEVEL_2_ROWS, LEVEL_2_SEATS_PER_ROW);
		Level level3 = new Level(3, "Balcony 1", 50.00, LEVEL_3_ROWS, LEVEL_3_SEATS_PER_ROW);
		Level level4 = new Level(4, "Balcony 2", 4.00, LEVEL_4_ROWS, LEVEL_4_SEATS_PER_ROW);

		TEST_LEVELS = ImmutableList.of(level1, level2, level3, level4);
	}

	private static final Venue TEST_VENUE = new Venue(1, "Verizon Center", TEST_LEVELS);

	@Test
	public void validConstruction() {
		TicketReserver reserver = new TicketReserver(TEST_VENUE);
		assertNotNull(reserver);
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullVenueOnConstructionShouldThrowIllegalArgument() {
		new TicketReserver(null);
	}

	@Test
	public void numberOfSeatsAvailable() {
		TicketReserver reserver = new TicketReserver(TEST_VENUE);

		int numberOfSeatsLevel1 = reserver.numberOfSeatsAvailable(Optional.of(1));
		assertEquals(LEVEL_1_TOTAL_SEATS, numberOfSeatsLevel1);

		int numberOfSeatsLevel2 = reserver.numberOfSeatsAvailable(Optional.of(2));
		assertEquals(LEVEL_2_TOTAL_SEATS, numberOfSeatsLevel2);

		int numberOfSeatsLevel3 = reserver.numberOfSeatsAvailable(Optional.of(3));
		assertEquals(LEVEL_3_TOTAL_SEATS, numberOfSeatsLevel3);

		int numberOfSeatsLevel4 = reserver.numberOfSeatsAvailable(Optional.of(4));
		assertEquals(LEVEL_4_TOTAL_SEATS, numberOfSeatsLevel4);
	}

	@Test
	public void testFindAndHoldAllSeats() {
		TicketReserver reserver = new TicketReserver(TEST_VENUE);
		reserver.findAndHoldSeats(TOTAL_SEATS_IN_VENUE, Optional.of(1), Optional.of(4), "test@email.com");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindAndHoldAllSeatsInvalidRange() {
		TicketReserver reserver = new TicketReserver(TEST_VENUE);
		reserver.findAndHoldSeats(TOTAL_SEATS_IN_VENUE, Optional.of(5), Optional.of(4), "test@email.com");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindAndHoldAllSeatsMinGreaterThanVenueMax() {
		TicketReserver reserver = new TicketReserver(TEST_VENUE);
		reserver.findAndHoldSeats(TOTAL_SEATS_IN_VENUE, Optional.of(5), Optional.of(4), "test@email.com");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindAndHoldAllSeatsMinLessThanVenueMin() {
		TicketReserver reserver = new TicketReserver(TEST_VENUE);
		reserver.findAndHoldSeats(TOTAL_SEATS_IN_VENUE, Optional.of(0), Optional.of(4), "test@email.com");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindAndHoldAllSeatsMaxLessThanVenueMin() {
		TicketReserver reserver = new TicketReserver(TEST_VENUE);
		reserver.findAndHoldSeats(TOTAL_SEATS_IN_VENUE, Optional.of(1), Optional.of(0), "test@email.com");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindAndHoldAllSeatsMaxGreaterThanVenueMax() {
		TicketReserver reserver = new TicketReserver(TEST_VENUE);
		reserver.findAndHoldSeats(TOTAL_SEATS_IN_VENUE, Optional.of(1), Optional.of(5), "test@email.com");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindAndHoldAllSeatsMinHigherThanMax() {
		TicketReserver reserver = new TicketReserver(TEST_VENUE);
		reserver.findAndHoldSeats(TOTAL_SEATS_IN_VENUE, Optional.of(3), Optional.of(2), "test@email.com");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindAndHoldAllSeatsNullMin() {
		TicketReserver reserver = new TicketReserver(TEST_VENUE);
		reserver.findAndHoldSeats(TOTAL_SEATS_IN_VENUE, null, Optional.of(4), "test@email.com");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindAndHoldAllSeatsNullMax() {
		TicketReserver reserver = new TicketReserver(TEST_VENUE);
		reserver.findAndHoldSeats(TOTAL_SEATS_IN_VENUE, Optional.of(1), null, "test@email.com");
	}

	@Test
	public void testFindAndHoldAllSeatsEmptyConstraints() {
		TicketReserver reserver = new TicketReserver(TEST_VENUE);
		reserver.findAndHoldSeats(TOTAL_SEATS_IN_VENUE, Optional.empty(), Optional.empty(), "test@email.com");
	}

	@Test(expected = RuntimeException.class)
	public void testFindAndHoldAllSeatsPlus1() {
		TicketReserver reserver = new TicketReserver(TEST_VENUE);
		reserver.findAndHoldSeats(TOTAL_SEATS_IN_VENUE + 1, Optional.of(1), Optional.of(4), "test@email.com");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindAndHoldSeatsZeroSeats() {
		TicketReserver reserver = new TicketReserver(TEST_VENUE);
		reserver.findAndHoldSeats(0, Optional.of(1), Optional.of(4), "test@email.com");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindAndHoldSeatsNegativeSeats() {
		TicketReserver reserver = new TicketReserver(TEST_VENUE);
		reserver.findAndHoldSeats(-1, Optional.of(1), Optional.of(4), "test@email.com");
	}

	@Test
	public void testFindAndHoldSeatsFirstLevelAlreadyTaken() {
		TicketReserver reserver = new TicketReserver(TEST_VENUE);
		SeatHold seatHold1 = reserver.findAndHoldSeats(LEVEL_1_TOTAL_SEATS, Optional.of(1), Optional.of(4),
				"test@email.com");
		SeatHold seatHold2 = reserver.findAndHoldSeats(LEVEL_1_TOTAL_SEATS, Optional.of(1), Optional.of(4),
				"test@email.com");

		assertNotNull(seatHold1);
		assertNotNull(seatHold2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindAndHoldSeatsEmptyEmail() {
		TicketReserver reserver = new TicketReserver(TEST_VENUE);
		reserver.findAndHoldSeats(1, Optional.of(1), Optional.of(4), "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindAndHoldSeatsNullEmail() {
		TicketReserver reserver = new TicketReserver(TEST_VENUE);
		reserver.findAndHoldSeats(1, Optional.of(1), Optional.of(4), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidLevelShouldThrowIllegalArgument() {
		TicketReserver reserver = new TicketReserver(TEST_VENUE);
		reserver.numberOfSeatsAvailable(Optional.of(5));
	}

	@Test
	public void nullOptionalLevelShouldReturnTotalSeatsInVenue() {
		TicketReserver reserver = new TicketReserver(TEST_VENUE);
		int numberOfSeatsInVenue = reserver.numberOfSeatsAvailable(null);
		assertEquals(TOTAL_SEATS_IN_VENUE, numberOfSeatsInVenue);
	}

	@Test
	public void emptyOptionalLevelShouldReturnTotalSeatsInVenue() {
		TicketReserver reserver = new TicketReserver(TEST_VENUE);
		int numberOfSeatsInVenue = reserver.numberOfSeatsAvailable(Optional.empty());
		assertEquals(TOTAL_SEATS_IN_VENUE, numberOfSeatsInVenue);
	}

	@Test
	public void reserveSeats() {
		TicketReserver reserver = new TicketReserver(TEST_VENUE);
		SeatHold seatHold = reserver.findAndHoldSeats(10, Optional.empty(), Optional.empty(), "test@email.com");
		String confirmationNumber = reserver.reserveSeats(seatHold);
		assertNotNull(confirmationNumber);
	}

	@Test(expected = IllegalArgumentException.class)
	public void reserveSeatsNullSeatHold() {
		TicketReserver reserver = new TicketReserver(TEST_VENUE);
		SeatHold seatHold = null;
		String confirmationNumber = reserver.reserveSeats(seatHold);
		assertNotNull(confirmationNumber);
	}

	@Test(expected = IllegalStateException.class)
	public void reserveSeatsFakeSeatHold() {
		TicketReserver reserver = new TicketReserver(TEST_VENUE);

		// Make a seat that i dont actually have a hold to.
		Seat seat = new Seat(3, 3, 3, 22.00);

		List<Seat> seats = new ArrayList<>();
		seats.add(seat);

		SeatHold seatHold = new SeatHold(66, "test@email.com", seats);
		reserver.reserveSeats(seatHold);

	}

	@Test(expected = IllegalStateException.class)
	public void reserveSeatsFakeSeatAndSeatHold() {
		TicketReserver reserver = new TicketReserver(TEST_VENUE);

		// Make a seat that doesnt actually exist
		// Level 6 doesnt exist.
		Seat seat = new Seat(6, 6, 6, 22.00);

		List<Seat> seats = new ArrayList<>();
		seats.add(seat);

		SeatHold seatHold = new SeatHold(66, "test@email.com", seats);
		reserver.reserveSeats(seatHold);

	}

	@Test
	public void expireSeatHold() {
		TicketReserver reserver = new TicketReserver(TEST_VENUE);
		SeatHold seatHold1 = reserver.findAndHoldSeats(100, Optional.of(1), Optional.of(4), "test@email.com");
		reserver.expireSeatHold(seatHold1);

	}

	@Test(expected = IllegalStateException.class)
	public void expireSeatHoldBadSeatHold() {
		TicketReserver reserver = new TicketReserver(TEST_VENUE);
		// Make a seat that i dont actually have a hold to.
		Seat seat = new Seat(3, 3, 3, 22.00);

		List<Seat> seats = new ArrayList<>();
		seats.add(seat);

		SeatHold seatHold = new SeatHold(66, "test@email.com", seats);
		reserver.expireSeatHold(seatHold);

	}

	@Test(expected = IllegalStateException.class)
	public void expireSeatHoldBadSeatAndSeatHold() {
		TicketReserver reserver = new TicketReserver(TEST_VENUE);

		// Make a seat that doesnt actually exist
		// Level 6 doesnt exist.
		Seat seat = new Seat(6, 6, 6, 22.00);

		List<Seat> seats = new ArrayList<>();
		seats.add(seat);

		SeatHold seatHold = new SeatHold(66, "test@email.com", seats);
		reserver.expireSeatHold(seatHold);

	}

	@Test(expected = IllegalArgumentException.class)
	public void expireSeatHoldNullSeatHold() {
		TicketReserver reserver = new TicketReserver(TEST_VENUE);
		reserver.expireSeatHold(null);

	}

}
