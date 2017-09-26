package model;

import java.util.Collection;

import com.google.common.base.Preconditions;

/**
 * SeatHold. A temporary reservation on a number of seats for a given venue and
 * level. Note that you can not have one SeatHold that contains seats in
 * multiple levels.
 * 
 * @author bstoll
 *
 */
public final class SeatHold {

	private final int id;
	private final String customerEmail;
	private final int numberOfSeats;
	private final int venueId;
	private final int levelId;

	public SeatHold(int id, String customerEmail, int numberOfSeats, int venueId, int levelId) {

		Preconditions.checkArgument(id > 0, "Invalid seathold id. Must be greater than 0");
		Preconditions.checkArgument(customerEmail != null, "Invalid Customer email. Must not be null");
		Preconditions.checkArgument(!customerEmail.isEmpty(), "Invalid Customer email. Must not be empty string");
		Preconditions.checkArgument(numberOfSeats > 0, "Invalid number of seats. Must be greater than 0");
		Preconditions.checkArgument(venueId > 0, "Invalid venue ID, Must be greater than 0");
		Preconditions.checkArgument(numberOfSeats > 0, "Invalid level ID, Must be greater than 0");

		this.id = id;

		// FIXME Prob need to check customer email for valid email.
		this.customerEmail = customerEmail;
		this.numberOfSeats = numberOfSeats;
		this.venueId = venueId;
		this.levelId = levelId;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the customerEmail
	 */
	public String getCustomerEmail() {
		return customerEmail;
	}

	/**
	 * @return the numberOfSeats
	 */
	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	/**
	 * @return the venueId
	 */
	public int getVenueId() {
		return venueId;
	}

	/**
	 * @return the levelId
	 */
	public int getLevelId() {
		return levelId;
	}

	public static int sum(Collection<SeatHold> seatHolds) {
		int sum = 0;
		for (SeatHold seatHold : seatHolds) {
			sum += seatHold.getNumberOfSeats();
		}

		return sum;

	}

}
