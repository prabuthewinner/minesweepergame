import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * The MineSweeperService class implements the IService interface and provides
 * the core functionality for a Minesweeper game. It manages the game state,
 * initializes the game board, handles user interactions, and determines the
 * game outcome.
 *
 * Key functionalities include:
 * - Initializing the game board with a specified number of rows and mines.
 * - Preparing the initial game panel and tile panel.
 * - Handling user interactions with the game tiles.
 * - Placing mines randomly on the board.
 * - Checking for mines around a tile and updating the game state.
 * - Revealing all mines when the game is over.
 * - Printing the current state of the board to the console.
 *
 * The class uses a 2D array of Tiles objects to represent the game board and
 * a list to keep track of tiles containing mines. It also manages the game
 * frame and label for displaying game messages.
 */
public class MineSweeperService implements GameService {

    private final int numRows;
    private final int numCols;
    private final int minesCount;

    private final JFrame frame = new JFrame("Minesweeper");
    private final JLabel label;

    private final Tiles[][] board;
    private List<Tiles> tilesList;

    private int tilesClicked = 0;
    private boolean gameOver = false;

    private static final int[][] DIRECTIONS = {
            { -1, -1 }, { -1, 0 }, { -1, 1 },
            { 0, -1 }, { 0, 1 },
            { 1, -1 }, { 1, 0 }, { 1, 1 }
    };

    public MineSweeperService(int numRows, int minesCount) {
        this.numRows = numRows;
        this.numCols = numRows;
        this.minesCount = minesCount;
        this.board = new Tiles[this.numRows][this.numCols];
        this.label = new JLabel();
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Starts the Minesweeper game by preparing the initial game panel, setting up
     * the tile panel,
     * placing mines on the board, and printing the initial state of the minefield
     * to the console.
     */
    @Override
    public void startGame() {
        this.prepareInitialPanel();
        this.prepareTilePanel();
        setMines();
        System.out.println("Here is your minefield:");
        printTrace(this.board);
    }

    /**
     * Prepares the initial panel for the Minesweeper game.
     *
     * This method sets up the main frame with a specified size based on the number
     * of columns and rows.
     * It configures the frame's properties such as size, location, resizability,
     * default close operation,
     * and layout. It also sets up a label with a specific font and text to display
     * the Minesweeper title
     * and mines count. The label is added to a panel, which is then added to the
     * frame.
     */
    private void prepareInitialPanel() {
        int titleSize = 70;
        int borderWidth = this.numCols * titleSize;
        int borderHeight = this.numRows * titleSize;
        frame.setSize(borderWidth, borderHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocation(0, 0);

        label.setFont(new Font("Arial", Font.BOLD, 25));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setText("Minesweeper: " + minesCount);
        label.setOpaque(true);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(label);
        frame.add(panel);
        frame.add(panel, BorderLayout.NORTH);
    }

    /**
     * Prepares the tile panel for the Minesweeper game.
     * This method initializes a JPanel with a grid layout based on the number of
     * rows and columns.
     * It sets the background color of the panel to green and adds it to the frame.
     * Then, it iterates through each cell in the grid and adds a tile to the board.
     * Finally, it makes the frame visible.
     */
    private void prepareTilePanel() {
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(this.numRows, this.numCols));
        boardPanel.setBackground(Color.GREEN);
        frame.add(boardPanel);

        for (int row = 0; row < this.numRows; row++) {
            for (int col = 0; col < this.numCols; col++) {
                addTileToBoard(row, col, boardPanel);
            }
        }

        frame.setVisible(true);
    }

    /**
     * Adds a tile to the game board at the specified row and column.
     *
     * @param row        the row index where the tile will be added
     * @param col        the column index where the tile will be added
     * @param boardPanel the JPanel representing the game board
     */
    private void addTileToBoard(int row, int col, JPanel boardPanel) {
        Tiles tile = new Tiles(row, col);
        board[row][col] = tile;

        tile.setFocusable(false);
        tile.setMargin(new Insets(0, 0, 0, 0));
        tile.setFont(new Font("Arial Unicode MS", Font.PLAIN, 45));
        tile.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                moveEventCallBack(e);
            }
        });
        boardPanel.add(tile);
    }

    /**
     * Handles the mouse event callback for a move action in the Minesweeper game.
     *
     * @param e the MouseEvent triggered by the user's action
     *
     *          If the game is over, the method returns immediately.
     *          If the left mouse button is clicked on a tile, the method checks if
     *          the tile is empty.
     *          If the tile is part of the mines list, it reveals all mines.
     *          Otherwise, it checks the tile for mines, prints the number of
     *          adjacent mines,
     *          and checks if the game is completed.
     */
    private void moveEventCallBack(MouseEvent e) {
        if (this.gameOver) {
            return;
        }
        Tiles tile = (Tiles) e.getSource();

        if (e.getButton() == MouseEvent.BUTTON1) {
            if (tile.getText().equalsIgnoreCase("")) {
                if (tilesList.contains(tile)) {
                    revealMines();
                } else {
                    checkMine(tile.row, tile.col);
                    System.out.println("This square contains " + tile.getText() + " adjacent mines.");
                    printTrace(board);

                    gameompletedCheck();
                }
            }
        }
    }

    /**
     * Checks if the game is completed by comparing the number of clicked tiles
     * with the total number of tiles minus the number of tiles in the tilesList.
     * If the game is completed and not already marked as over, it prints a
     * congratulatory message, updates the label, sets the gameOver flag to true,
     * and disposes of the game frame.
     */
    private void gameompletedCheck() {
        if ((this.tilesClicked == (this.numRows * this.numCols) - this.tilesList.size()) && !this.gameOver) {
            System.out.println("Congratulations, you have won the game!");
            label.setText("Congratulations, you have won the game!");
            this.gameOver = true;
            disposeFrame();
        }
    }

    /**
     * Randomly places mines on the board. The method ensures that the specified
     * number of mines
     * are placed on unique tiles. It uses a random number generator to select tiles
     * and adds them
     * to the tilesList if they do not already contain a mine.
     */
    private void setMines() {
        Random random = new Random();
        this.tilesList = new ArrayList<>();

        int minesLeft = minesCount;
        while (minesLeft > 0) {
            int r = random.nextInt(this.numRows);
            int c = random.nextInt(this.numCols);

            Tiles tile = board[r][c];
            if (!tilesList.contains(tile)) {
                tilesList.add(tile);
                minesLeft -= 1;
            }
        }
    }

    /**
     * Reveals all mines on the game board by setting the text of each tile to a
     * bomb icon.
     * Displays a game over message and updates the game state to indicate that the
     * game is over.
     * Disposes of the game frame.
     */
    public void revealMines() {
        for (Tiles tile : tilesList) {
            tile.setText("💣");
        }

        System.out.println("Oh no, you detonated a mine! Game over.");
        label.setText("Oh no, you detonated a mine! Game over.");
        this.gameOver = true;
        disposeFrame();
    }

    /**
     * Disposes the current frame after a specified delay.
     * A timer is set to trigger the disposal action after 3000 milliseconds.
     * The timer does not repeat.
     */
    private void disposeFrame() {
        int delay = 3000;
        Timer timer = new Timer(delay, e -> frame.dispose());
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Checks the tile at the specified row and column for a mine.
     * If the tile is out of bounds or already disabled, the method returns
     * immediately.
     * If the tile is enabled, it disables the tile and increments the count of
     * clicked tiles.
     * If mines are found around the tile, it sets the tile's text to the number of
     * mines.
     * If no mines are found, it sets the tile's text to "0" and recursively checks
     * surrounding tiles.
     *
     * @param r the row index of the tile to check
     * @param c the column index of the tile to check
     */
    public void checkMine(int r, int c) {
        if (isOutOfBounds(r, c) || !board[r][c].isEnabled()) {
            return;
        }

        Tiles tile = board[r][c];
        tile.setEnabled(false);
        tilesClicked++;

        int minesFound = getMinesCount(r, c);
        tile.setText(minesFound > 0 ? Integer.toString(minesFound) : "0");

        if (minesFound == 0) {
            checkMineRecursion(r, c);
        }
    }

    /**
     * Checks if the given row and column are out of bounds.
     *
     * @param r the row index to check
     * @param c the column index to check
     * @return true if the position is out of bounds, false otherwise
     */
    private boolean isOutOfBounds(int r, int c) {
        return r < 0 || r >= this.numRows || c < 0 || c >= this.numCols;
    }

    /**
     * Calculates the number of mines surrounding a given cell in the Minesweeper
     * grid.
     *
     * @param r the row index of the cell
     * @param c the column index of the cell
     * @return the count of mines surrounding the specified cell
     */
    private int getMinesCount(int r, int c) {
        return Arrays.stream(DIRECTIONS)
                .mapToInt(direction -> countMine(r + direction[0], c + direction[1]))
                .sum();
    }

    /**
     * Checks the surrounding cells of a given cell (r, c) for mines.
     * This method calls the checkMine method for each of the eight neighboring
     * cells.
     *
     * @param r the row index of the cell to check around
     * @param c the column index of the cell to check around
     */
    private void checkMineRecursion(int r, int c) {
        Arrays.stream(DIRECTIONS).forEach(direction -> checkMine(r + direction[0], c + direction[1]));
    }

    /**
     * Counts the number of mines at the specified position on the board.
     *
     * @param r the row index of the position to check
     * @param c the column index of the position to check
     * @return 1 if the position contains a mine, 0 otherwise or if the position is
     *         out of bounds
     */
    private int countMine(int r, int c) {
        return (r >= 0 && r < this.numRows && c >= 0 && c < this.numCols && tilesList.contains(board[r][c])) ? 1 : 0;
    }

    /**
     * Prints the current state of the minefield to the console.
     * The minefield is represented by a 2D array of Tiles.
     * The method displays the minefield with row and column headers.
     * Empty tiles are represented by an underscore ("_").
     *
     * @param board A 2D array of Tiles representing the minefield.
     */
    public void printTrace(Tiles[][] board) {
        System.out.println("");
        System.out.println("Here is your updated minefield:");

        String[][] arr = new String[board.length + 1][board.length + 1];

        IntStream.rangeClosed(1, board.length).forEach(i -> arr[0][i] = Integer.toString(i));

        IntStream.rangeClosed(1, board.length).forEach(i -> arr[i][0] = Character.toString((char) ('A' + i - 1)));

        IntStream.rangeClosed(1, board.length).forEach(i -> IntStream.rangeClosed(1, board.length).forEach(j -> {
            Tiles tile = board[i - 1][j - 1];
            arr[i][j] = tile.getText().isEmpty() ? "_" : tile.getText();
        }));

        IntStream.rangeClosed(1, board.length).forEach(i -> IntStream.rangeClosed(1, board.length).forEach(j -> {
            Tiles tile = board[i - 1][j - 1];
            arr[i][j] = tile.getText().isEmpty() ? "_" : tile.getText();
        }));

        IntStream.rangeClosed(0, board.length).forEach(i -> {
            IntStream.rangeClosed(0, board.length)
                    .forEach(j -> System.out.print((arr[i][j] == null ? "  " : arr[i][j] + " ")));
            System.out.println();
        });
        System.out.println("");
    }
}