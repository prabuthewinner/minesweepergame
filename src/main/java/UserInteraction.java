import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The UserInteraction class handles user input for configuring and playing the
 * Minesweeper game.
 * It provides methods to get the grid size, the number of mines, and to prompt
 * the user to play again.
 *
 * Methods:
 * - getGridSize(): Prompts the user to enter the size of the grid and validates
 * the input.
 * - getMinesCount(int rowSize): Prompts the user to enter the number of mines
 * and validates the input based on the grid size.
 * - playAgain(): Prompts the user to press any key to play again.
 */
public class UserInteraction {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Prompts the user to enter the size of the grid for the Minesweeper game.
     * The method ensures that the input is a valid integer between 2 and 26.
     * If the input is invalid, it will prompt the user to enter the value again.
     *
     * @return the size of the grid as an integer.
     */
    public int getGridSize() {
        int rowSize = 0;
        boolean isValid = false;

        while (!isValid) {
            try {
                System.out.println("Enter the size of the grid (e.g. 4 for a 4x4 grid): ");
                rowSize = scanner.nextInt();

                if (rowSize < 2 || rowSize > 26) {
                    System.out.println("Enter the valid size of the grid > 1 and <= 26");
                } else {
                    isValid = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next(); // Clear the invalid input
            }
        }

        return rowSize;
    }

    /**
     * Prompts the user to enter the number of mines to place on the grid.
     * The number of mines must be between 1 and 35% of the total squares on the
     * grid.
     *
     * @param rowSize the size of the grid (number of rows/columns)
     * @return the number of mines to place on the grid
     */
    public int getMinesCount(int rowSize) {
        int minesCount = 0;
        boolean isValid = false;
        int eligibleMineCount = (int) Math.floor((rowSize * rowSize) * (35.0f / 100));

        while (!isValid) {
            try {
                System.out.println(
                        "Enter the number of mines to place on the grid (maximum is 35% of the total squares): ");
                minesCount = scanner.nextInt();

                if (minesCount > eligibleMineCount || minesCount < 1) {
                    System.out.println("Enter a valid number of mines between 1 and " + eligibleMineCount);
                } else {
                    isValid = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next(); // Clear the invalid input
            }
        }

        return minesCount;
    }

    /**
     * Prompts the user to press any key to play again and waits for the user's
     * input.
     *
     * @return A string containing the user's input.
     */
    public String playAgain() {
        System.out.println("Press any key to play again... ");
        scanner.nextLine();
        return scanner.nextLine();
    }
}