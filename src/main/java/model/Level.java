package model;

import com.google.common.base.Preconditions;

/**
 * Level represents a section of seats inside of a venue.
 * 
 * 
 * @author bstoll
 *
 */
public final class Level {

	private final String name;
	// FIXME Should not use double here.
	private final double pricePerTicket;

	private final int numberOfRows;
	private final int seatsInRow;

	/**
	 * 
	 * @param name
	 *            name of the level. Must not be empty or null.
	 * @param pricePerTicket
	 *            The Price of a ticket at this level. Must be greater than 0.
	 * @param numberOfRows
	 *            The number of rows in this section. Must be greater than 0.
	 * @param seatsInRow
	 *            The number of seats in a row. Must be greater than 0.
	 */
	public Level(String name, double pricePerTicket, int numberOfRows, int seatsInRow) {
		Preconditions.checkArgument(name != null && !name.isEmpty(), "Invalid Name, Must not be null or empty");
		Preconditions.checkArgument(pricePerTicket > 0.00, "Invalid Price, Must be greater than 0");
		Preconditions.checkArgument(numberOfRows > 0, "Invalid Number Of Rows, Must be greater than 0");
		Preconditions.checkArgument(seatsInRow > 0, "Invalid Seats in Row, Must be greater than 0");

		this.name = name;
		this.pricePerTicket = pricePerTicket;
		this.numberOfRows = numberOfRows;
		this.seatsInRow = seatsInRow;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the pricePerTicket
	 */
	public double getPricePerTicket() {
		return pricePerTicket;
	}

	/**
	 * @return the numberOfRows
	 */
	public int getNumberOfRows() {
		return numberOfRows;
	}

	/**
	 * @return the seatsInRow
	 */
	public int getSeatsInRow() {
		return seatsInRow;
	}

	/**
	 * @return the total number of seats.
	 */
	public int getTotalNumberOfSeats() {
		return numberOfRows * seatsInRow;
	}

}
