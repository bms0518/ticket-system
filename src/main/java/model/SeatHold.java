package model;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Preconditions;

/**
 * SeatHold represents a specific hold on selected number of seats. They are
 * tied directly to a users email. This object is Immutable.
 * 
 * @author bstoll
 *
 */
public final class SeatHold {

	private final int id;
	private final String customerEmail;
	private final List<Seat> seats;

	/**
	 * Sets up a SeatHold.
	 * 
	 * @param id
	 *            The int id of the SeatHold. Must be greater than 0.
	 * @param customerEmail
	 *            The Customer email. Must not be null or empty.
	 * @param seats
	 *            The Seats. Must not be null or empty. Each Seat in the List must
	 *            not be null.
	 * @throws IllegalArgumentException
	 *             If any of the constraints are invalidated.
	 * @throws NullPointerException
	 *             If any of the Seats in the List are null.
	 */
	public SeatHold(int id, String customerEmail, List<Seat> seats) {

		Preconditions.checkArgument(id > 0, "Invalid seathold id. Must be greater than 0");
		Preconditions.checkArgument(customerEmail != null, "Invalid Customer email. Must not be null");
		Preconditions.checkArgument(!customerEmail.isEmpty(), "Invalid Customer email. Must not be empty string");
		Preconditions.checkArgument(seats != null, "Invalid seats. Must not be null");
		Preconditions.checkArgument(!seats.isEmpty(), "Invalid seats. Must not be empty");
		seats.forEach(Preconditions::checkNotNull);

		this.id = id;
		this.customerEmail = customerEmail;
		this.seats = seats;
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
	 * @return UnmodifiableCollection of the seats
	 */
	public List<Seat> getSeats() {
		return Collections.unmodifiableList(seats);
	}

	/**
	 * @return the numberOfSeats
	 */
	public int getNumberOfSeats() {
		return seats.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SeatHold [id=" + id + ", customerEmail=" + customerEmail + ", numberSeats=" + seats.size()
				+ ", numberSeats=" + seats + "]";
	}

}
