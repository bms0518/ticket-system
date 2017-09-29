package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.javamoney.moneta.Money;
import org.junit.Test;

import util.TestObjectFactory;

public class SeatTests {

	@Test
	public void validConstruction() {
		Seat seat = new Seat(1, 1, 1, TestObjectFactory.TEST_PRICE);
		assertNotNull(seat);
	}

	@Test
	public void testGetLevelId() {
		Seat seat = new Seat(1, 1, 1, TestObjectFactory.TEST_PRICE);
		assertEquals(1, seat.getLevelId());
	}

	@Test
	public void testGetRow() {
		Seat seat = new Seat(1, 1, 1, TestObjectFactory.TEST_PRICE);
		assertEquals(1, seat.getRow());
	}

	@Test
	public void testGetSeatNumber() {
		Seat seat = new Seat(1, 1, 1, TestObjectFactory.TEST_PRICE);
		assertEquals(1, seat.getSeatNumber());
	}

	@Test
	public void testGetPrice() {
		Seat seat = new Seat(1, 1, 1, TestObjectFactory.TEST_PRICE);
		assertEquals(TestObjectFactory.TEST_PRICE, seat.getPricePerTicket());
	}

	@Test(expected = IllegalArgumentException.class)
	public void zeroIdOnConstructionShouldThrowIllegalArgument() {
		new Seat(0, 1, 1, TestObjectFactory.TEST_PRICE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeIdOnConstructionShouldThrowIllegalArgument() {
		new Seat(-1, 1, 1, TestObjectFactory.TEST_PRICE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void zeroRowOnConstructionShouldThrowIllegalArgument() {
		new Seat(1, 0, 1, TestObjectFactory.TEST_PRICE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeRowOnConstructionShouldThrowIllegalArgument() {
		new Seat(1, -1, 1, TestObjectFactory.TEST_PRICE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void zeroSeatNumberOnConstructionShouldThrowIllegalArgument() {
		new Seat(1, 1, 0, TestObjectFactory.TEST_PRICE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeSeatNumberOnConstructionShouldThrowIllegalArgument() {
		new Seat(1, 1, -1, TestObjectFactory.TEST_PRICE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void zeroPriceOnConstructionShouldThrowIllegalArgument() {
		new Seat(1, 1, 1, Money.of(0, "USD"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativePriceOnConstructionShouldThrowIllegalArgument() {
		new Seat(1, 1, 1, Money.of(-1, "USD"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullPriceOnConstructionShouldThrowIllegalArgument() {
		new Seat(1, 1, 1, null);
	}

	@Test
	public void seatEqualityTest() {
		Seat seat1 = new Seat(1, 1, 1, Money.of(1, "USD"));
		Seat seat2 = new Seat(1, 1, 1, Money.of(1, "USD"));
		assertEquals(seat1, seat2);
	}

	@Test
	public void seatHashCodeEqualityTest() {
		Seat seat1 = new Seat(1, 1, 1, Money.of(1, "USD"));
		Seat seat2 = new Seat(1, 1, 1, Money.of(1, "USD"));
		assertEquals(seat1.hashCode(), seat2.hashCode());
	}

}
