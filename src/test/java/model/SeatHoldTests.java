package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.javamoney.moneta.Money;
import org.junit.Test;

import util.TestObjectFactory;

/**
 * Unit tests for SeatHold class.
 * 
 * 
 * @author bstoll
 *
 */
public class SeatHoldTests {

	@Test
	public void testGetId() {
		assertEquals(1, TestObjectFactory.newSeatHold().getId());
	}

	@Test
	public void testGetCustomerEmail() {
		assertEquals(TestObjectFactory.TEST_EMAIL, TestObjectFactory.newSeatHold().getCustomerEmail());
	}

	@Test
	public void testGetNumberOfSeats() {
		assertEquals(4, TestObjectFactory.newSeatHold().getNumberOfSeats());
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeIdShouldThrowIllegalArgument() {
		new SeatHold(-1, TestObjectFactory.TEST_EMAIL, TestObjectFactory.TEST_SEATS);
	}

	@Test(expected = IllegalArgumentException.class)
	public void zeroIdShouldThrowIllegalArgument() {
		new SeatHold(0, TestObjectFactory.TEST_EMAIL, TestObjectFactory.TEST_SEATS);
	}

	@Test(expected = IllegalArgumentException.class)
	public void emptySeatsShouldThrowIllegalArgument() {
		new SeatHold(1, TestObjectFactory.TEST_EMAIL, Collections.emptyList());
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullSeatsShouldThrowIllegalArgument() {
		new SeatHold(1, TestObjectFactory.TEST_EMAIL, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void emptyEmailShouldThrowIllegalArgument() {
		new SeatHold(1, "", TestObjectFactory.TEST_SEATS);
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullEmailShouldThrowIllegalArgument() {
		new SeatHold(1, null, TestObjectFactory.TEST_SEATS);
	}

	@Test(expected = NullPointerException.class)
	public void nullSeatInSeatsShouldThrowNullPointer() {
		Seat seat1 = null;
		Seat seat2 = new Seat(2, 2, 2, Money.of(2, "USD"));
		List<Seat> seats = new ArrayList<>();
		seats.add(seat1);
		seats.add(seat2);

		new SeatHold(1, TestObjectFactory.TEST_EMAIL, seats);
	}

	@Test
	public void validConstruction() {
		SeatHold hold = new SeatHold(1, TestObjectFactory.TEST_EMAIL, TestObjectFactory.TEST_SEATS);
		assertNotNull(hold);
	}

}
