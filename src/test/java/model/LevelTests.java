package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class LevelTests {

	@Test(expected = IllegalArgumentException.class)
	public void emptyNameShouldThrowIllegalArgument() {
		new Level("", 1.0, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullNameShouldThrowIllegalArgument() {
		new Level(null, 1.0, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void zeroPriceShouldThrowIllegalArgument() {
		new Level("Test", 0, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativePriceShouldThrowIllegalArgument() {
		new Level("Test", -1.0, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void zeroRowsShouldThrowIllegalArgument() {
		new Level("Test", 1, 0, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeRowsShouldThrowIllegalArgument() {
		new Level("Test", 1, -1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeSeatsShouldThrowIllegalArgument() {
		new Level("Test", 1, 1, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void ZeroSeatsShouldThrowIllegalArgument() {
		new Level("Test", 1, 1, 0);
	}

	@Test
	public void validConstruction() {
		Level level = new Level("Test", 1, 1, 1);
		assertNotNull(level);
	}

	@Test
	public void testGetName() {
		Level level = new Level("Test", 1, 1, 1);
		assertEquals("Test", level.getName());
	}

	@Test
	public void testGetPrice() {
		Level level = new Level("Test", 1, 1, 1);
		assertEquals(1, level.getPricePerTicket(), 0.0);
	}

	@Test
	public void testGetNumberOfRows() {
		Level level = new Level("Test", 1, 1, 1);
		assertEquals(1, level.getNumberOfRows());
	}

	@Test
	public void testGetSeatsInRow() {
		Level level = new Level("Test", 1, 1, 1);
		assertEquals(1, level.getSeatsInRow());
	}

	@Test
	public void testGetTotalSeatsInLevel() {
		Level level = new Level("Test", 1, 10, 10);
		assertEquals(10 * 10, level.getTotalNumberOfSeats());
	}

}
