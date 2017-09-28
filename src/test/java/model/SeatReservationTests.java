package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SeatReservationTests {

	@Test
	public void testValidConstruction() {
		SeatReservation reservation = new SeatReservation();
		assertFalse(reservation.isHeld());
		assertFalse(reservation.isReserved());
		assertEquals(-1, reservation.getSeatHoldId());

	}

	@Test
	public void testHold() {
		SeatReservation reservation = new SeatReservation();
		reservation.hold(1);
		assertTrue(reservation.isHeld());
		assertEquals(1, reservation.getSeatHoldId());
	}

	@Test(expected = IllegalArgumentException.class)
	public void zeroIdOnHoldShouldThrowIllegalArgument() {
		SeatReservation reservation = new SeatReservation();
		reservation.hold(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeIdOnHoldShouldThrowIllegalArgument() {
		SeatReservation reservation = new SeatReservation();
		reservation.hold(-1);
	}

	@Test(expected = IllegalStateException.class)
	public void ifAlreadyHeldOnHoldShouldThrowIllegalState() {
		SeatReservation reservation = new SeatReservation();
		reservation.hold(1);
		reservation.hold(1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void zeroIdOnReserveShouldThrowIllegalArgument() {
		SeatReservation reservation = new SeatReservation();
		reservation.hold(1);
		reservation.reserve(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeIdOnReserveShouldThrowIllegalArgument() {
		SeatReservation reservation = new SeatReservation();
		reservation.hold(1);
		reservation.reserve(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void idDoesntMatchHeldIdOnReserveShouldThrowIllegalArgument() {
		SeatReservation reservation = new SeatReservation();
		reservation.hold(1);
		reservation.reserve(2);
	}

	@Test(expected = IllegalStateException.class)
	public void ifNotAlreadyHeldOnReserveShouldThrowIllegalState() {
		SeatReservation reservation = new SeatReservation();
		reservation.reserve(1);
	}

	@Test(expected = IllegalStateException.class)
	public void ifAlreadyReservedOnReserveShouldThrowIllegalState() {
		SeatReservation reservation = new SeatReservation();
		reservation.hold(1);
		reservation.reserve(1);
		reservation.reserve(1);
	}

	@Test
	public void testReserve() {
		SeatReservation reservation = new SeatReservation();
		reservation.hold(1);
		reservation.reserve(1);

		assertTrue(reservation.isHeld());
		assertEquals(1, reservation.getSeatHoldId());
		assertTrue(reservation.isReserved());
	}

	@Test
	public void testClear() {
		SeatReservation reservation = new SeatReservation();
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
