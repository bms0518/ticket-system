package ui;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.Level;
import model.Seat;
import model.Venue;

public class VenuePane extends Pane {

	private static final int ROW_GAP = 10;

	private static final int LEVEL_GAP = 50;

	private static final int SEAT_GAP = 2;

	private static final int SEAT_HEIGHT = 5;

	private static final int SEAT_WIDTH = 5;

	private static final Color[] COLORS = new Color[] { Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW };

	private final Map<UUID, SeatView> seatViews = new HashMap<>();

	public VenuePane(Venue venue) {

		int y = 0;

		int i = 0;
		for (Level level : venue.getLevels()) {
			int currentY = handleLevel(level, COLORS[i], y);
			y = currentY;
			y += LEVEL_GAP;
			i++;
		}

	}

	public void holdSeat(Seat seat) {
		SeatView seatView = getSeatView(seat);
		if (seatView != null) {
			seatView.hold();
		}
	}

	public void reserveSeat(Seat seat) {
		SeatView seatView = getSeatView(seat);
		if (seatView != null) {
			seatView.reserve();
		}
	}

	public void clearSeat(Seat seat) {
		SeatView seatView = getSeatView(seat);
		if (seatView != null) {
			seatView.clear();
		}
	}

	private final SeatView getSeatView(Seat seat) {
		SeatView seatView = null;
		if (seat != null) {
			seatView = seatViews.get(seat.getUniqueSeatId());
		}
		return seatView;
	}

	private int handleLevel(Level level, Color color, int y) {
		Multimap<Integer, Seat> seatsByRow = sortSeats(level.getSeats());

		int maxY = y;

		int x = 0;
		for (Integer row : seatsByRow.keySet()) {
			int yForRow = y + (ROW_GAP * row);
			maxY = Math.max(yForRow, maxY);

			for (Seat seat : seatsByRow.get(row)) {
				SeatView seatView = new SeatView(x, yForRow, SEAT_WIDTH, SEAT_HEIGHT);
				seatViews.put(seat.getUniqueSeatId(), seatView);
				x += SEAT_WIDTH + SEAT_GAP;
				super.getChildren().add(seatView);
			}
			x = 0;
		}

		return maxY;

	}

	private Multimap<Integer, Seat> sortSeats(Collection<Seat> seats) {
		Multimap<Integer, Seat> seatsByRow = HashMultimap.create();
		for (Seat seat : seats) {
			seatsByRow.put(seat.getRow(), seat);
		}

		return seatsByRow;

	}

}
