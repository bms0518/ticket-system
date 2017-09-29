package util;

import java.util.List;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;

import com.google.common.collect.ImmutableList;

import model.Level;
import model.Seat;
import model.SeatHold;
import model.Venue;
import ticketsystem.DefaultTicketService;
import ticketsystem.TicketReserver;
import ticketsystem.TicketService;

public class TestObjectFactory {

	public static final int LEVEL_1_ROWS = 25;
	public static final int LEVEL_1_SEATS_PER_ROW = 50;
	public static final int LEVEL_1_TOTAL_SEATS = LEVEL_1_ROWS * LEVEL_1_SEATS_PER_ROW;

	public static final int LEVEL_2_ROWS = 20;
	public static final int LEVEL_2_SEATS_PER_ROW = 100;
	public static final int LEVEL_2_TOTAL_SEATS = LEVEL_2_ROWS * LEVEL_2_SEATS_PER_ROW;

	public static final int LEVEL_3_ROWS = 15;
	public static final int LEVEL_3_SEATS_PER_ROW = 100;
	public static final int LEVEL_3_TOTAL_SEATS = LEVEL_3_ROWS * LEVEL_3_SEATS_PER_ROW;

	public static final int LEVEL_4_ROWS = 15;
	public static final int LEVEL_4_SEATS_PER_ROW = 100;
	public static final int LEVEL_4_TOTAL_SEATS = LEVEL_4_ROWS * LEVEL_4_SEATS_PER_ROW;

	public static final int TOTAL_SEATS_IN_VENUE = LEVEL_1_TOTAL_SEATS + LEVEL_2_TOTAL_SEATS + LEVEL_3_TOTAL_SEATS
			+ LEVEL_4_TOTAL_SEATS;

	public static final String TEST_NAME = "test";

	public static final String TEST_EMAIL = "test@email.com";

	public static final MonetaryAmount TEST_PRICE = Money.of(1, "USD");

	public static final List<Level> TEST_LEVELS;

	public static final List<Seat> TEST_SEATS;

	static {

		Level level1 = new Level(1, "Orchestra", Money.of(100.0, "USD"), LEVEL_1_ROWS, LEVEL_1_SEATS_PER_ROW);
		Level level2 = new Level(2, "Main", Money.of(75.0, "USD"), LEVEL_2_ROWS, LEVEL_2_SEATS_PER_ROW);
		Level level3 = new Level(3, "Balcony 1", Money.of(50.0, "USD"), LEVEL_3_ROWS, LEVEL_3_SEATS_PER_ROW);
		Level level4 = new Level(4, "Balcony 2", Money.of(4.0, "USD"), LEVEL_4_ROWS, LEVEL_4_SEATS_PER_ROW);

		TEST_LEVELS = ImmutableList.of(level1, level2, level3, level4);

		Seat seat1 = new Seat(1, 1, 1, Money.of(1, "USD"));
		Seat seat2 = new Seat(2, 2, 2, Money.of(2, "USD"));
		Seat seat3 = new Seat(3, 3, 3, Money.of(3, "USD"));
		Seat seat4 = new Seat(4, 4, 4, Money.of(4, "USD"));

		TEST_SEATS = ImmutableList.of(seat1, seat2, seat3, seat4);
	}

	public static final Venue TEST_VENUE = new Venue(1, TEST_NAME, TEST_LEVELS);

	public static SeatHold newSeatHold() {
		return new SeatHold(1, TEST_EMAIL, TEST_SEATS);
	}

	public static TicketReserver newReserver() {
		return new TicketReserver(TEST_VENUE);
	}

	public static TicketService newService() {
		return new DefaultTicketService(newReserver());
	}

}
