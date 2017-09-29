package ui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * SeatView. A representation of a Seat in the UI.
 * 
 * @author bstoll
 *
 */
public class SeatView extends Rectangle {

	private final BooleanProperty held = new SimpleBooleanProperty();

	private final BooleanProperty reserved = new SimpleBooleanProperty();

	private static final Color DEFAULT_FILL = Color.WHITE;
	private static final Color DEFAULT_STROKE = Color.BLACK;

	/**
	 * Sets up SeatView.
	 * 
	 * @param x
	 *            X location of the SeatView.
	 * @param y
	 *            Y location of the SeatView.
	 * @param width
	 *            Width of the SeatView.
	 * @param height
	 *            Height of the SeatView
	 */
	public SeatView(double x, double y, double width, double height) {
		super(x, y, width, height);
		setFill(DEFAULT_FILL);
		setStroke(DEFAULT_STROKE);

		held.addListener((obs, oldVal, newVal) -> {
			if (newVal) {
				setFill(Color.YELLOW);
			} else {
				setFill(DEFAULT_FILL);
			}
		});

		reserved.addListener((obs, oldVal, newVal) -> {
			if (newVal) {
				setStroke(Color.RED);
			} else {
				setFill(DEFAULT_STROKE);
			}
		});

	}

	/**
	 * @return true if held, false otherwise.
	 */
	public boolean isHeld() {
		return held.get();
	}

	/**
	 * @return true if reserved, false otherwise.
	 */
	public boolean getIsReserved() {
		return reserved.get();
	}

	/**
	 * Sets hold to true.
	 */
	public void hold() {
		held.set(true);
	}

	/**
	 * Sets reserved to true.
	 */
	public void reserve() {
		reserved.set(true);
	}

	/**
	 * Sets held and reserved to false.
	 */
	public void clear() {
		held.set(false);
		reserved.set(false);
	}

}
