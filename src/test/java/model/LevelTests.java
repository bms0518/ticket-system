package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Unit tests for Level Class.
 * 
 * @author bstoll
 */
public class LevelTests {

	@Test(expected = IllegalArgumentException.class)
	public void zeroLevelShouldThrowIllegalArgument() {
		new Level(0, "Test", 0, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeLevelShouldThrowIllegalArgument() {
		new Level(-1, "Test", -1.0, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void emptyNameShouldThrowIllegalArgument() {
		new Level(1, "", 1.0, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullNameShouldThrowIllegalArgument() {
		new Level(1, null, 1.0, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void zeroPriceShouldThrowIllegalArgument() {
		new Level(1, "Test", 0, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativePriceShouldThrowIllegalArgument() {
		new Level(1, "Test", -1.0, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void zeroRowsShouldThrowIllegalArgument() {
		new Level(1, "Test", 1, 0, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeRowsShouldThrowIllegalArgument() {
		new Level(1, "Test", 1, -1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeSeatsShouldThrowIllegalArgument() {
		new Level(1, "Test", 1, 1, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void ZeroSeatsShouldThrowIllegalArgument() {
		new Level(1, "Test", 1, 1, 0);
	}

	@Test
	public void validConstruction() {
		Level level = new Level(1, "Test", 1, 1, 1);
		assertNotNull(level);
	}

	@Test
	public void testGetId() {
		Level level = new Level(1, "Test", 1, 1, 1);
		assertEquals(1, level.getId());
	}

	@Test
	public void testGetName() {
		Level level = new Level(1, "Test", 1, 1, 1);
		assertEquals("Test", level.getName());
	}

	@Test
	public void testGetSeats() {
		Level level = new Level(1, "Test", 1, 1, 1);
		assertEquals(1, level.getSeats().size());
	}

	@Test
	public void testGetTotalSeatsInLevel() {
		Level level = new Level(1, "Test", 1, 10, 10);
		assertEquals(10 * 10, level.getTotalNumberOfSeats());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void seatsCollectionIsUnmodifiable() {
		Level level = new Level(1, "Test", 1, 10, 10);
		level.getSeats().add(new Seat(1, 1, 1, 1.0));
	}

}
