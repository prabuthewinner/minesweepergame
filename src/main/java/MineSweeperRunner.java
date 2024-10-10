/**
 * The MineSweeperRunner class is the entry point for the Minesweeper game.
 * It handles user interaction and game loop.
 *
 * The main method initializes the user interaction and game service,
 * and runs the game in a loop until the user decides to exit.
 *
 * Methods:
 * - main(String[] args): The main method that starts the Minesweeper game.
 *
 * Usage:
 * Run this class to start the Minesweeper game.
 */
public class MineSweeperRunner {

    public static void main(String[] args) {

        UserInteraction userInteraction = new UserInteraction();

        while (true) {
            System.out.println("Welcome to Minesweeper!");

            int rowSize = userInteraction.getGridSize();
            int minesCount = userInteraction.getMinesCount(rowSize);

            GameService service = new MineSweeperService(rowSize, minesCount);
            service.startGame();

            while (!service.isGameOver()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            if (userInteraction.playAgain().equalsIgnoreCase("exit")) {
                break;
            }
        }
    }
}