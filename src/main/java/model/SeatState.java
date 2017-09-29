package model;

import com.google.common.base.Preconditions;

/**
 * SeatState. Represents the State of the Hold/Reservation of a specific Seat.
 * Keeps track of the SeatHold id who has held/reserved this seat.
 * 
 * @author bstoll
 *
 */
public final class SeatState {
	private boolean held;

	private boolean reserved;

	private int seatHoldId;

	/**
	 * Sets up a Seat Reservation. held and reserved fields both get set to false.
	 * SeatHoldId is set to -1.
	 */
	public SeatState() {
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
	 *             if invalid id passed in.
	 * @throws IllegalStateException
	 *             if seat is already held.
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
	 *             if invalid id passed in or id does not match the one already
	 *             stored.
	 * @throws IllegalStateException
	 *             if seat is not already held or seat is already reserved
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
