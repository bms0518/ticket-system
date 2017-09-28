package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Preconditions;

/**
 * Level represents a section of seats inside of a venue. They are identified by
 * id and name.
 * 
 * 
 * @author bstoll
 *
 */
public final class Level {

	private final int id;

	private final String name;

	private final List<Seat> seats;

	/**
	 * 
	 * @param name
	 *            name of the level. Must not be empty or null.
	 * @param pricePerTicket
	 *            The Price of a ticket at this level. Must be greater than 0.
	 * @param numberOfRows
	 *            The number of rows in this level. Must be greater than 0.
	 * @param seatsInRow
	 *            The number of seats in a row. Must be greater than 0.
	 */
	public Level(int id, String name, double pricePerTicket, int numberOfRows, int seatsInRow) {
		Preconditions.checkArgument(id > 0, "Invalid level, Must be greater than 0");
		Preconditions.checkArgument(numberOfRows > 0, "Invalid Number Of Rows, Must be greater than 0");
		Preconditions.checkArgument(name != null && !name.isEmpty(), "Invalid Name, Must not be null or empty");
		Preconditions.checkArgument(pricePerTicket > 0.00, "Invalid Price, Must be greater than 0");
		Preconditions.checkArgument(numberOfRows > 0, "Invalid Number Of Rows, Must be greater than 0");
		Preconditions.checkArgument(seatsInRow > 0, "Invalid Seats in Row, Must be greater than 0");

		this.id = id;
		this.name = name;
		this.seats = new ArrayList<>();

		for (int row = 1; row <= numberOfRows; row++) {
			for (int seat = 1; seat <= seatsInRow; seat++) {
				seats.add(new Seat(id, row, seat, pricePerTicket));
			}
		}

	}

	/**
	 * @return the id of the Level.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name of the Level.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return Unmodifiable list of Seats.
	 */
	public List<Seat> getSeats() {
		return Collections.unmodifiableList(seats);
	}

	/**
	 * @return the total number of seats on this level.
	 */
	public int getTotalNumberOfSeats() {
		return seats.size();
	}

}
