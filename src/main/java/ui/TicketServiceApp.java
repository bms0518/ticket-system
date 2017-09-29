package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Application which wraps the simulator and launches it.
 * 
 * @author bstoll
 *
 */
public class TicketServiceApp extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		TicketServiceSimulator simulator = new TicketServiceSimulator();

		VenuePane venuePane = simulator.getVenuePane();

		Scene scene = new Scene(venuePane);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setHeight(1000);
		stage.setWidth(1000);
		stage.setTitle("Ticket Service Application");

		stage.show();

		stage.setOnCloseRequest((event) -> {
			System.exit(0);

		});

		simulator.start();

	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
