package model;

import com.google.common.base.Preconditions;

/**
 * Seat Reservation. Represents the State of the Hold/Reservation of a specific
 * Seat. Keeps track of the SeatHold id who has reserved this seat.
 * 
 * @author bstoll
 *
 */
public class SeatReservation {
	private boolean held;

	private boolean reserved;

	private int seatHoldId;

	/**
	 * Sets up a Seat Reservation. held and reserved fields both get set to false.
	 * SeatHoldId is set to -1.
	 */
	public SeatReservation() {
		this.reserved = false;
		this.held = false;
		this.seatHoldId = -1;
	}

	/**
	 * Holds this seat for a particular SeatHold ID. The seat must not already be
	 * held or this will throw illegal argument exception.
	 * 
	 * @param seatHoldId
	 *            The SeatHoldId. Must be greater than 0.
	 * @throws IllegalArgumentException
	 *             if seat is already held or invalid id passed in.
	 */
	public void hold(int seatHoldId) {
		Preconditions.checkArgument(seatHoldId > 0, "Seat Hold ID must be greater than 0");
		Preconditions.checkState(!held, "Seat must not already be held");
		this.seatHoldId = seatHoldId;
		this.held = true;
	}

	/**
	 * Reserves this seat for a particular SeatHold ID. The seat must have been held
	 * by the same SeatHoldID and it must not already be reserved or this will throw
	 * illegal argument exception.
	 * 
	 * @param seatHoldId
	 *            The SeatHoldId. Must be greater than 0.
	 * @throws IllegalArgumentException
	 *             if seat is not already held, seat is already reserved, or invalid
	 *             id passed in.
	 */
	public void reserve(int seatHoldId) {
		Preconditions.checkArgument(seatHoldId > 0, "Seat Hold ID must be greater than 0");
		Preconditions.checkState(held, "Seat must already be held");
		Preconditions.checkState(!reserved, "Seat must not already be reserved");
		Preconditions.checkArgument(seatHoldId == this.seatHoldId, "Seat Hold ID must match seatHoldId");
		this.reserved = true;
	}

	/**
	 * Clears all held and reservation data.
	 */
	public void clear() {
		this.held = false;
		this.reserved = false;
		this.seatHoldId = -1;

	}

	/**
	 * @return the seatHoldId, -1 if not held by anyone.
	 */
	public int getSeatHoldId() {
		return seatHoldId;
	}

	/**
	 * @return true if reserved, false otherwise.
	 */
	public boolean isReserved() {
		return reserved;
	}

	/**
	 * @return true if available (not held or reserved), false otherwise.
	 */
	public boolean isAvailable() {
		return !reserved && !held;
	}

	/**
	 * @return true if held, false otherwise.
	 */
	public boolean isHeld() {
		return held;
	}

}
