package model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Preconditions;

/**
 * SeatHold. A reservation on a selection of seats.
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
	 *            The Seats. Must be greater than 0.
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
	 * @return the seats
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

	/**
	 * Sums the number of seats in a collection of SeatHolds.
	 * 
	 * @param seatHolds
	 *            Collection of SeatHolds. If null then 0 will be returned.
	 * @return sum of all seats in a collection of seat holds.
	 */
	public static int sumNumberOfSeats(Collection<SeatHold> seatHolds) {
		int sum = 0;

		if (seatHolds != null) {
			for (SeatHold seatHold : seatHolds) {
				sum += seatHold.getNumberOfSeats();
			}
		}

		return sum;

	}

}
