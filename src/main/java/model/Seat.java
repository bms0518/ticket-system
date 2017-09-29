package model;

import javax.money.MonetaryAmount;

import com.google.common.base.Preconditions;

/**
 * Represents a Unique Seat in a Venue. Identified by Level ID, Row, and Seat
 * number. This is an immutable representation of a specific seat.
 * 
 * @author bstoll
 *
 */
public final class Seat implements Comparable<Seat> {

	private final int levelId;
	private final int row;
	private final int seatNumber;
	private final MonetaryAmount pricePerTicket;

	/**
	 * Sets up a Seat.
	 * 
	 * @param levelId
	 *            The Level ID this Seat belongs to. Must be greater than 0.
	 * @param row
	 *            The Row of this Seat. Row must be greater than 0.
	 * @param seatNumber
	 *            The Number of this seat. Number must be greater than 0.
	 * @param pricePerTicket
	 *            The Price per ticket. Must not be null and must be greater than 0.
	 * @throws IllegalArgumentException
	 *             If the any of the constraints are invalidated.
	 */
	public Seat(int levelId, int row, int seatNumber, MonetaryAmount pricePerTicket) {
		Preconditions.checkArgument(levelId > 0, "Invalid Level ID, Must be greater than 0");
		Preconditions.checkArgument(row > 0, "Invalid Row, Must be greater than 0");
		Preconditions.checkArgument(seatNumber > 0, "Invalid Seat Number, Must be greater than 0");
		Preconditions.checkArgument(pricePerTicket != null, "Invalid Price, Price must not be null");
		Preconditions.checkArgument(pricePerTicket.isPositive(), "Invalid Price, Must be greater than 0");
		this.levelId = levelId;
		this.row = row;
		this.seatNumber = seatNumber;
		this.pricePerTicket = pricePerTicket;
	}

	/**
	 * @return the levelId
	 */
	public int getLevelId() {
		return levelId;
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @return the seatNumber
	 */
	public int getSeatNumber() {
		return seatNumber;
	}

	/**
	 * @return the pricePerTicket
	 */
	public MonetaryAmount getPricePerTicket() {
		return pricePerTicket;
	}

	@Override
	public int compareTo(Seat other) {

		int levelCompare = Integer.compare(levelId, other.levelId);

		if (levelCompare == 0) {
			int rowCompare = Integer.compare(row, other.row);

			if (rowCompare == 0) {
				return Integer.compare(seatNumber, other.seatNumber);
			} else {
				return rowCompare;
			}

		} else {
			return levelCompare;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + row;
		result = prime * result + seatNumber;
		result = prime * result + levelId;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seat other = (Seat) obj;
		if (row != other.row)
			return false;
		if (seatNumber != other.seatNumber)
			return false;
		if (levelId != other.levelId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Seat [ " + levelId + ", " + row + ", " + seatNumber + " ]";
	}

}