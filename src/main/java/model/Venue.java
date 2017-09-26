package model;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.common.base.Preconditions;

/**
 * Venue represents a collection of Levels
 * 
 * @author bstoll
 *
 */
public class Venue {

	private final int id;
	private final String name;
	private final SortedMap<Integer, Level> levels;

	public Venue(int id, String name, Set<Level> levels) {
		Preconditions.checkArgument(id > 0);

		Preconditions.checkArgument(name != null);
		Preconditions.checkArgument(!name.isEmpty());

		Preconditions.checkArgument(levels != null);
		Preconditions.checkArgument(!levels.isEmpty());
		levels.forEach(Preconditions::checkNotNull);

		this.id = id;
		this.name = name;

		this.levels = new TreeMap<>();
		int i = 1;
		for (Level level : levels) {
			this.levels.put(i, level);
			i++;
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
	 * @param levelId
	 *            The Level Id.
	 * @return the Level for the given id.
	 */
	public Level getLevel(int levelId) {
		Preconditions.checkArgument(containsLevel(levelId));
		return levels.get(levelId);
	}

	public boolean containsLevel(int levelId) {
		return levels.containsKey(levelId);
	}

	public Collection<Level> getLevels() {
		return Collections.unmodifiableCollection(levels.values());
	}

	public Integer getMinLevel() {
		return levels.firstKey();
	}

	public Integer getMaxLevel() {
		return levels.lastKey();
	}

}
