package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

/**
 * Unit tests for SeatHold class.
 * 
 * 
 * @author bstoll
 *
 */
public class SeatHoldTests {

	private static final String TEST_EMAIL = "test@email.com";

	private static final List<Seat> DEFAULT_SEATS;

	static {
		Seat seat1 = new Seat(1, 1, 1, 1.0);
		Seat seat2 = new Seat(2, 2, 2, 2.0);
		Seat seat3 = new Seat(3, 3, 3, 3.0);
		Seat seat4 = new Seat(4, 4, 4, 4.0);

		DEFAULT_SEATS = ImmutableList.of(seat1, seat2, seat3, seat4);
	}

	private static final SeatHold TEST_SEAT_HOLD = new SeatHold(1, TEST_EMAIL, DEFAULT_SEATS);

	@Test
	public void testGetId() {
		assertEquals(1, TEST_SEAT_HOLD.getId());
	}

	@Test
	public void testGetCustomerEmail() {
		assertEquals(TEST_EMAIL, TEST_SEAT_HOLD.getCustomerEmail());
	}

	@Test
	public void testGetNumberOfSeats() {
		assertEquals(4, TEST_SEAT_HOLD.getNumberOfSeats());
	}

	@Test
	public void testSumNumberOfSeats() {
		SeatHold seatHold1 = new SeatHold(1, TEST_EMAIL, DEFAULT_SEATS);
		SeatHold seatHold2 = new SeatHold(1, TEST_EMAIL, DEFAULT_SEATS);
		SeatHold seatHold3 = new SeatHold(1, TEST_EMAIL, DEFAULT_SEATS);

		List<SeatHold> seatHolds = new ArrayList<>();
		seatHolds.add(seatHold1);
		seatHolds.add(seatHold2);
		seatHolds.add(seatHold3);

		assertEquals(12, SeatHold.sumNumberOfSeats(seatHolds));
	}

	@Test
	public void testSumNullSeatsEquals0() {
		List<SeatHold> seatHolds = null;
		assertEquals(0, SeatHold.sumNumberOfSeats(seatHolds));
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeIdShouldThrowIllegalArgument() {
		new SeatHold(-1, TEST_EMAIL, DEFAULT_SEATS);
	}

	@Test(expected = IllegalArgumentException.class)
	public void zeroIdShouldThrowIllegalArgument() {
		new SeatHold(0, TEST_EMAIL, DEFAULT_SEATS);
	}

	@Test(expected = IllegalArgumentException.class)
	public void emptySeatsShouldThrowIllegalArgument() {
		new SeatHold(1, TEST_EMAIL, Collections.emptyList());
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullSeatsShouldThrowIllegalArgument() {
		new SeatHold(1, TEST_EMAIL, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void emptyEmailShouldThrowIllegalArgument() {
		new SeatHold(1, "", DEFAULT_SEATS);
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullEmailShouldThrowIllegalArgument() {
		new SeatHold(1, null, DEFAULT_SEATS);
	}

	@Test(expected = NullPointerException.class)
	public void nullSeatInSeatsShouldThrowNullPointer() {
		Seat seat1 = null;
		Seat seat2 = new Seat(2, 2, 2, 2.0);
		List<Seat> seats = new ArrayList<>();
		seats.add(seat1);
		seats.add(seat2);

		new SeatHold(1, TEST_EMAIL, seats);
	}

	@Test
	public void validConstruction() {
		SeatHold hold = new SeatHold(1, TEST_EMAIL, DEFAULT_SEATS);
		assertNotNull(hold);
	}

}
