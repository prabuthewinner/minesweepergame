import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.*;

public class MineSweeperServiceTest {

    private final PrintStream systemOut = System.out;
    private ByteArrayOutputStream testOut;

    @Before
    public void setUp() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @After
    public void restoreSystemOutput() {
        System.setOut(systemOut);
    }

    private String getOutput() {
        return testOut.toString();
    }

    @Test
    public void testStartGame() {
        MineSweeperService service = new MineSweeperService(4, 2);
        service.startGame();

        String output = getOutput();
        assertTrue(output.contains("Here is your minefield:"));
    }

    @Test
    public void testRevealMines() {
        MineSweeperService service = new MineSweeperService(4, 2);
        service.startGame();
        service.revealMines();

        String output = getOutput();
        assertTrue(output.contains("Oh no, you detonated a mine! Game over."));
        assertTrue(service.isGameOver());
    }

    @Test
    public void testCheckMine() {
        MineSweeperService service = new MineSweeperService(4, 2);
        service.startGame();
        service.checkMine(0, 0);

        String output = getOutput();
        assertTrue(output.contains("Here is your minefield:"));
    }

    @Test
    public void testWinCondition() {
        MineSweeperService service = new MineSweeperService(2, 1);
        service.startGame();

        // Simulate clicking all non-mine tiles
        service.checkMine(0, 0);
        service.checkMine(0, 1);
        service.checkMine(1, 0);

        String output = getOutput();
        assertTrue(output.contains("Here is your minefield"));
        assertFalse(service.isGameOver());
    }
}