package model;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;

public class VenueTests {

	private static final Set<Level> DEFAULT_LEVELS;

	static {
		Level level1 = new Level("Orchestra", 100.0, 25, 50);
		Level level2 = new Level("Main", 75.00, 20, 100);
		Level level3 = new Level("Balcony 1", 50.00, 15, 100);
		Level level4 = new Level("Balcony 2", 4.00, 15, 100);

		DEFAULT_LEVELS = ImmutableSet.of(level1, level2, level3, level4);
	}

	@Test
	public void testGetId() {
		Venue venue = new Venue(1, "Test", DEFAULT_LEVELS);
		assertEquals(1, venue.getId());
	}

	@Test
	public void testGetName() {
		Venue venue = new Venue(1, "Test", DEFAULT_LEVELS);
		assertEquals("Test", venue.getName());
	}

	@Test(expected = IllegalArgumentException.class)
	public void emptyNameShouldThrowIllegalArgument() {
		new Venue(1, "", DEFAULT_LEVELS);
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullNameShouldThrowIllegalArgument() {
		new Venue(1, null, DEFAULT_LEVELS);
	}

	@Test(expected = IllegalArgumentException.class)
	public void zeroIdShouldThrowIllegalArgument() {
		new Venue(0, "Test", DEFAULT_LEVELS);
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeIdShouldThrowIllegalArgument() {
		new Venue(-1, "Test", DEFAULT_LEVELS);
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullLevelsShouldThrowIllegalArgument() {
		new Venue(1, "Test", null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void emptyLevelsShouldThrowIllegalArgument() {
		new Venue(1, "Test", Collections.emptySet());
	}

	@Test(expected = NullPointerException.class)
	public void nullLevelInLevelsShouldThrowNullPointer() {
		Level level = null;
		Set<Level> levels = new HashSet<>();
		levels.add(new Level("Test", 1, 1, 1));
		levels.add(level);

		new Venue(1, "Test", levels);
	}

}
