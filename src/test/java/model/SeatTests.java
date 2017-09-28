package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class SeatTests {

	@Test
	public void validConstruction() {
		Seat seat = new Seat(1, 1, 1, 1.0);
		assertNotNull(seat);
	}

	@Test
	public void testGetLevelId() {
		Seat seat = new Seat(1, 1, 1, 1.0);
		assertEquals(1, seat.getLevelId());
	}

	@Test
	public void testGetRow() {
		Seat seat = new Seat(1, 1, 1, 1.0);
		assertEquals(1, seat.getRow());
	}

	@Test
	public void testGetSeatNumber() {
		Seat seat = new Seat(1, 1, 1, 1.0);
		assertEquals(1, seat.getSeatNumber());
	}

	@Test
	public void testGetPrice() {
		Seat seat = new Seat(1, 1, 1, 1.0);
		assertEquals(1.0, seat.getPricePerTicket(), 0.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void zeroIdOnConstructionShouldThrowIllegalArgument() {
		new Seat(0, 1, 1, 1.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeIdOnConstructionShouldThrowIllegalArgument() {
		new Seat(-1, 1, 1, 1.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void zeroRowOnConstructionShouldThrowIllegalArgument() {
		new Seat(1, 0, 1, 1.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeRowOnConstructionShouldThrowIllegalArgument() {
		new Seat(1, -1, 1, 1.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void zeroSeatNumberOnConstructionShouldThrowIllegalArgument() {
		new Seat(1, 1, 0, 1.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeSeatNumberOnConstructionShouldThrowIllegalArgument() {
		new Seat(1, 1, -1, 1.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void zeroPriceOnConstructionShouldThrowIllegalArgument() {
		new Seat(1, 1, 1, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativePriceOnConstructionShouldThrowIllegalArgument() {
		new Seat(1, 1, 1, -1.0);
	}

}
