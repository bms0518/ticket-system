package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

/**
 * Unit tests for Venue class.
 * 
 * 
 * @author bstoll
 *
 */
public class VenueTests {

	private static final List<Level> DEFAULT_LEVELS;

	static {
		Level level1 = new Level(1, "Orchestra", 100.0, 25, 50);
		Level level2 = new Level(2, "Main", 75.00, 20, 100);
		Level level3 = new Level(3, "Balcony 1", 50.00, 15, 100);
		Level level4 = new Level(4, "Balcony 2", 4.00, 15, 100);

		DEFAULT_LEVELS = ImmutableList.of(level1, level2, level3, level4);
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

	@Test
	public void testGetMinLevel() {
		Venue venue = new Venue(1, "Test", DEFAULT_LEVELS);
		assertEquals(1, venue.getMinLevel());
	}

	@Test
	public void testGetMaxLevel() {
		Venue venue = new Venue(1, "Test", DEFAULT_LEVELS);
		assertEquals(4, venue.getMaxLevel());
	}

	@Test
	public void testContainsLevelWithId() {
		Venue venue = new Venue(1, "Test", DEFAULT_LEVELS);
		assertTrue(venue.containsLevelWithId(1));
		assertTrue(venue.containsLevelWithId(2));
		assertTrue(venue.containsLevelWithId(3));
		assertTrue(venue.containsLevelWithId(4));
		assertFalse(venue.containsLevelWithId(5));
		assertFalse(venue.containsLevelWithId(0));
	}

	@Test
	public void testGetLevelById() {
		Venue venue = new Venue(1, "Test", DEFAULT_LEVELS);
		assertNotNull(venue.getLevelById(1));
		assertNotNull(venue.getLevelById(2));
		assertNotNull(venue.getLevelById(3));
		assertNotNull(venue.getLevelById(4));
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidLevelThrowsIllegalArgGetLevelByLevel() {
		Venue venue = new Venue(1, "Test", DEFAULT_LEVELS);
		venue.getLevelById(0);
	}

	@Test
	public void testGetLevels() {
		Venue venue = new Venue(1, "Test", DEFAULT_LEVELS);
		assertNotNull(venue.getLevels());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void unmodifiableLevels() {
		Venue venue = new Venue(1, "Test", DEFAULT_LEVELS);
		venue.getLevels().add(new Level(1, "Test", 1, 1, 1));

	}

	@Test
	public void validConstruction() {
		Venue venue = new Venue(1, "Test", DEFAULT_LEVELS);
		assertNotNull(venue);
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
		new Venue(1, "Test", Collections.emptyList());
	}

	@Test(expected = NullPointerException.class)
	public void nullLevelInLevelsShouldThrowNullPointer() {
		Level level = null;
		List<Level> levels = new ArrayList<>();
		levels.add(new Level(1, "Test", 1, 1, 1));
		levels.add(level);

		new Venue(1, "Test", levels);
	}

	@Test(expected = IllegalArgumentException.class)
	public void sameIdInLevelsShouldThrowIllegalArgumentException() {
		List<Level> levels = new ArrayList<>();
		levels.add(new Level(1, "Test", 1, 1, 1));
		levels.add(new Level(1, "Test", 1, 1, 1));

		new Venue(1, "Test", levels);
	}

}
