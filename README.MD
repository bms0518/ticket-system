# Ticket System

Ticket System is a basic ticket reservation service that allows users to hold,reserve, and view the total number of seats available.

- Ticket System was built using gradle 3.5, JDK 8 update 144

## Build, Test, Run

Ticket system is built using Gradle. The gradle wrapper is used in this project and is all that is needed to build, test, and run the system. This can be run on Windows and Linux. Download/Clone the project to local machine. Open up a command-window/terminal in the root of the project and run these commands.

- NOTE: JDK 8 Must be installed and on your PATH before running these commands or they will fail.

### Building the project
````
./gradlew build
````

### Testing the project
````
./gradlew test
````

-To view the actual results this file can be opened up in any browser:

````
{project-folder}/build/reports/tests/test/index.html
````

### Running the simulator
````
./gradlew runSimulator
````


## Simulator

The Simulator is a simple UI that shows multiple requests being made to the service. Every 100ms it will attempt to hold and reserve seats. 

- White Squares with Black outline means the seat is available.
- Yellow Squares with Black outline means the seat is held.
- Yellow Squares with Red Outline means the seat is reserved(and held).
- The simulator will continue to run forever. Once all the seats are taken it will still continue to request seats. All of these requests will fail. Closing out of the application will stop it.
- The simulator has an increased timeout of 10 Seconds. The reason for this is that it makes it much easier to see seats expiring.
- The simulator will only reserve seats 50% of the time after holding them. This is not realistic but it was in there to show some seats getting held and reserved and some only getting held.
- It will appear that the first level is getting reserved at a much quicker rate. This is due to how I am randomizing the numbers passed in to Min and Max level. Essentially it would have to randomly get 4 and 4 (4 for min and 4 for max) in order for it to reserve at level 4 at the beginning.
- The UI is extremely primitive. It just shows the levels as sections and each row as a different line of seats.

## Unit Tests

- There is currently 120 unit tests.
- The model package is at 98.7% code coverage.
- The ticketsystem package is at 97.7% code coverage.
- The ui did not make sense to unit test so it was left out of the unit tests.


## Room for Improvement/Extension

- Currently the holds and reservations are only stored in memory. Right now SeatState is the only thing that stores the reservations and holds. Exporting this to a DB would be pretty straight forward. 
- The UI could be greatly improved by showing the actual seat layout of a venue, showing seat numbers and rows on the UI, showing total number of seats left per row.

