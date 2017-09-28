package model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.common.base.Preconditions;

/**
 * Venue represents a collection of Sections.
 * 
 * <p>
 * The List of Sections that is passed in to the constructor will be mapped
 * based on an integer level starting with 1. For example: if you pass in 3
 * levels you will end up with 3 Sections in the Map identified by levels 1,2,
 * and 3. That means that the "lowest(or best)" seats will always be 1, followed
 * by 2, followed by 3...
 * 
 * <p>
 * NOTE: It is technically possible that the lowest seats are less expensive
 * then seats in the middle. Keep that in mind when passing in the List of
 * Sections.
 * 
 * 
 * @author bstoll
 *
 */
public class Venue {

	private final int id;
	private final String name;
	private final SortedMap<Integer, Level> levelsById;

	/**
	 * Sets up a Venue.
	 * 
	 * @param id
	 *            The Id of the Venue. Must be greater than 0.
	 * @param name
	 *            The Name of the venue. Must not be null or empty.
	 * @param levels
	 *            The List of Levels in the Venue. Must not be null or empty. Each
	 *            element in the collection must not be null. The first level in the
	 *            list must be the lowest level in the venue. The rest of the
	 *            elements should be in ascending order of the venue.
	 */
	public Venue(int id, String name, List<Level> levels) {
		Preconditions.checkArgument(id > 0);

		Preconditions.checkArgument(name != null);
		Preconditions.checkArgument(!name.isEmpty());

		Preconditions.checkArgument(levels != null);
		Preconditions.checkArgument(!levels.isEmpty());
		levels.forEach(Preconditions::checkNotNull);

		this.id = id;
		this.name = name;

		// Map by level
		this.levelsById = new TreeMap<>();

		for (Level level : levels) {

			if (!levelsById.containsKey(level.getId())) {
				this.levelsById.put(level.getId(), level);
			} else {
				throw new IllegalArgumentException("Level already exists for this id");
			}

		}

	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param level
	 *            The Level in the venue. The level must be available in the map of
	 *            levels.
	 * @return the Section for the given level.
	 * @throws IllegalArgumentException
	 *             if level does not exist for this venue.
	 */
	public Level getLevelById(int level) {
		Preconditions.checkArgument(containsLevelWithId(level));
		return levelsById.get(level);
	}

	/**
	 * 
	 * @param level
	 *            to see if section exists for.
	 * @return true if a section for this level exists. False otherwise.
	 */
	public boolean containsLevelWithId(int id) {
		return levelsById.containsKey(id);
	}

	/**
	 * 
	 * @return Unmodifiable collection of all the levels for this venue.
	 */
	public Collection<Level> getLevels() {
		return Collections.unmodifiableCollection(levelsById.values());
	}

	/**
	 * 
	 * @return Integer Minimum Level.
	 */
	public int getMinLevel() {
		return levelsById.firstKey();
	}

	/**
	 * 
	 * @return Integer Maximum Level.
	 */
	public int getMaxLevel() {
		return levelsById.lastKey();
	}

}
