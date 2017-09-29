package ui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SeatView extends Rectangle {

	private final BooleanProperty isHeld = new SimpleBooleanProperty();

	private final BooleanProperty isReserved = new SimpleBooleanProperty();

	private static final Color DEFAULT_FILL = Color.WHITE;
	private static final Color DEFAULT_STROKE = Color.BLACK;

	public SeatView(double x, double y, double width, double height) {
		super(x, y, width, height);
		setFill(DEFAULT_FILL);
		setStroke(DEFAULT_STROKE);

		isHeld.addListener((obs, oldVal, newVal) -> {
			if (newVal) {
				setFill(Color.ORANGE);
			} else {
				setFill(DEFAULT_FILL);
			}
		});

		isReserved.addListener((obs, oldVal, newVal) -> {
			if (newVal) {
				setStroke(Color.RED);
			} else {
				setFill(DEFAULT_STROKE);
			}
		});

	}

	public void hold() {
		isHeld.set(true);
	}

	public void reserve() {
		isReserved.set(true);
	}

	public void clear() {
		isHeld.set(false);
		isReserved.set(false);
	}

}
