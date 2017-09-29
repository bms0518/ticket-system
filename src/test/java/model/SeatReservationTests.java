package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SeatReservationTests {

	@Test
	public void testValidConstruction() {
		SeatState reservation = new SeatState();
		assertFalse(reservation.isHeld());
		assertFalse(reservation.isReserved());
		assertEquals(-1, reservation.getSeatHoldId());

	}

	@Test
	public void testHold() {
		SeatState reservation = new SeatState();
		reservation.hold(1);
		assertTrue(reservation.isHeld());
		assertEquals(1, reservation.getSeatHoldId());
	}

	@Test(expected = IllegalArgumentException.class)
	public void zeroIdOnHoldShouldThrowIllegalArgument() {
		SeatState reservation = new SeatState();
		reservation.hold(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeIdOnHoldShouldThrowIllegalArgument() {
		SeatState reservation = new SeatState();
		reservation.hold(-1);
	}

	@Test(expected = IllegalStateException.class)
	public void ifAlreadyHeldOnHoldShouldThrowIllegalState() {
		SeatState reservation = new SeatState();
		reservation.hold(1);
		reservation.hold(1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void zeroIdOnReserveShouldThrowIllegalArgument() {
		SeatState reservation = new SeatState();
		reservation.hold(1);
		reservation.reserve(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeIdOnReserveShouldThrowIllegalArgument() {
		SeatState reservation = new SeatState();
		reservation.hold(1);
		reservation.reserve(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void idDoesntMatchHeldIdOnReserveShouldThrowIllegalArgument() {
		SeatState reservation = new SeatState();
		reservation.hold(1);
		reservation.reserve(2);
	}

	@Test(expected = IllegalStateException.class)
	public void ifNotAlreadyHeldOnReserveShouldThrowIllegalState() {
		SeatState reservation = new SeatState();
		reservation.reserve(1);
	}

	@Test(expected = IllegalStateException.class)
	public void ifAlreadyReservedOnReserveShouldThrowIllegalState() {
		SeatState reservation = new SeatState();
		reservation.hold(1);
		reservation.reserve(1);
		reservation.reserve(1);
	}

	@Test
	public void testReserve() {
		SeatState reservation = new SeatState();
		reservation.hold(1);
		reservation.reserve(1);

		assertTrue(reservation.isHeld());
		assertEquals(1, reservation.getSeatHoldId());
		assertTrue(reservation.isReserved());
	}

	@Test
	public void testClear() {
		SeatState reservation = new SeatState();
		reservation.hold(1);
		reservation.reserve(1);

		assertTrue(reservation.isHeld());
		assertEquals(1, reservation.getSeatHoldId());
		assertTrue(reservation.isReserved());

		reservation.clear();

		assertFalse(reservation.isHeld());
		assertFalse(reservation.isReserved());
		assertEquals(-1, reservation.getSeatHoldId());

	}

}
