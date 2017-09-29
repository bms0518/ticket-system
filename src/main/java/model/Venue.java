package model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.common.base.Preconditions;

/**
 * Venue represents a collection of Levels. The List of Levels passed in will be
 * mapped by their Level ID. Each Level passed in must be unique to this Venue.
 * 
 * This class is Immutable.
 * 
 * 
 * 
 * @author bstoll
 *
 */
public final class Venue {

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
	 *            element in the collection must not be null.
	 * @throws IllegalArgumentException
	 *             If any of the constraints are invalidated.
	 * @throws IllegalStateException
	 *             if one of the Levels passed in is null.
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
	 * @param id
	 *            The id to get the level for.
	 * @return the Level for the given id
	 * @throws IllegalArgumentException
	 *             if level does not exist for the given id.
	 */
	public Level getLevelById(int id) {
		Preconditions.checkArgument(containsLevelWithId(id));
		return levelsById.get(id);
	}

	/**
	 * 
	 * @param id
	 *            to see if level exists for.
	 * @return true if a level for this id exists. False otherwise.
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
