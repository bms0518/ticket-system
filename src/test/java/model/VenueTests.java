package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import util.TestObjectFactory;

/**
 * Unit tests for Venue class.
 * 
 * 
 * @author bstoll
 *
 */
public class VenueTests {

	@Test
	public void testGetId() {
		Venue venue = new Venue(1, TestObjectFactory.TEST_NAME, TestObjectFactory.TEST_LEVELS);
		assertEquals(1, venue.getId());
	}

	@Test
	public void testGetName() {
		Venue venue = new Venue(1, TestObjectFactory.TEST_NAME, TestObjectFactory.TEST_LEVELS);
		assertEquals(TestObjectFactory.TEST_NAME, venue.getName());
	}

	@Test
	public void testGetMinLevel() {
		Venue venue = new Venue(1, TestObjectFactory.TEST_NAME, TestObjectFactory.TEST_LEVELS);
		assertEquals(1, venue.getMinLevel());
	}

	@Test
	public void testGetMaxLevel() {
		Venue venue = new Venue(1, TestObjectFactory.TEST_NAME, TestObjectFactory.TEST_LEVELS);
		assertEquals(4, venue.getMaxLevel());
	}

	@Test
	public void testContainsLevelWithId() {
		Venue venue = new Venue(1, TestObjectFactory.TEST_NAME, TestObjectFactory.TEST_LEVELS);
		assertTrue(venue.containsLevelWithId(1));
		assertTrue(venue.containsLevelWithId(2));
		assertTrue(venue.containsLevelWithId(3));
		assertTrue(venue.containsLevelWithId(4));
		assertFalse(venue.containsLevelWithId(5));
		assertFalse(venue.containsLevelWithId(0));
	}

	@Test
	public void testGetLevelById() {
		Venue venue = new Venue(1, TestObjectFactory.TEST_NAME, TestObjectFactory.TEST_LEVELS);
		assertNotNull(venue.getLevelById(1));
		assertNotNull(venue.getLevelById(2));
		assertNotNull(venue.getLevelById(3));
		assertNotNull(venue.getLevelById(4));
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidLevelThrowsIllegalArgGetLevelByLevel() {
		Venue venue = new Venue(1, TestObjectFactory.TEST_NAME, TestObjectFactory.TEST_LEVELS);
		venue.getLevelById(0);
	}

	@Test
	public void testGetLevels() {
		Venue venue = new Venue(1, TestObjectFactory.TEST_NAME, TestObjectFactory.TEST_LEVELS);
		assertNotNull(venue.getLevels());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void unmodifiableLevels() {
		Venue venue = new Venue(1, TestObjectFactory.TEST_NAME, TestObjectFactory.TEST_LEVELS);
		venue.getLevels().add(new Level(1, TestObjectFactory.TEST_NAME, 1, 1, 1));

	}

	@Test
	public void validConstruction() {
		Venue venue = new Venue(1, TestObjectFactory.TEST_NAME, TestObjectFactory.TEST_LEVELS);
		assertNotNull(venue);
	}

	@Test(expected = IllegalArgumentException.class)
	public void emptyNameShouldThrowIllegalArgument() {
		new Venue(1, "", TestObjectFactory.TEST_LEVELS);
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullNameShouldThrowIllegalArgument() {
		new Venue(1, null, TestObjectFactory.TEST_LEVELS);
	}

	@Test(expected = IllegalArgumentException.class)
	public void zeroIdShouldThrowIllegalArgument() {
		new Venue(0, TestObjectFactory.TEST_NAME, TestObjectFactory.TEST_LEVELS);
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeIdShouldThrowIllegalArgument() {
		new Venue(-1, TestObjectFactory.TEST_NAME, TestObjectFactory.TEST_LEVELS);
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullLevelsShouldThrowIllegalArgument() {
		new Venue(1, TestObjectFactory.TEST_NAME, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void emptyLevelsShouldThrowIllegalArgument() {
		new Venue(1, TestObjectFactory.TEST_NAME, Collections.emptyList());
	}

	@Test(expected = NullPointerException.class)
	public void nullLevelInLevelsShouldThrowNullPointer() {
		Level level = null;
		List<Level> levels = new ArrayList<>();
		levels.add(new Level(1, TestObjectFactory.TEST_NAME, 1, 1, 1));
		levels.add(level);

		new Venue(1, TestObjectFactory.TEST_NAME, levels);
	}

	@Test(expected = IllegalArgumentException.class)
	public void sameIdInLevelsShouldThrowIllegalArgumentException() {
		List<Level> levels = new ArrayList<>();
		levels.add(new Level(1, TestObjectFactory.TEST_NAME, 1, 1, 1));
		levels.add(new Level(1, TestObjectFactory.TEST_NAME, 1, 1, 1));

		new Venue(1, TestObjectFactory.TEST_NAME, levels);
	}

}
