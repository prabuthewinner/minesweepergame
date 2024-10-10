import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;

public class MineSweeperRunnerTest {

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private ByteArrayOutputStream testOut;

    @Before
    public void setUp() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @After
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    private String getOutput() {
        return testOut.toString();
    }

    @Test
    public void testMainGameLoop() {
        String input = "4\n1\nexit\n";
        provideInput(input);

        MineSweeperRunner.main(new String[]{});

        String output = getOutput();
        assertTrue(output.contains("Welcome to Minesweeper!"));
        assertTrue(output.contains("Here is your minefield:"));
    }

    @Test
    public void testPlayAgain() {
        String input = "4\n1\nexit\n";
        provideInput(input);

        MineSweeperRunner.main(new String[]{});

        String output = getOutput();
        assertTrue(output.contains("Welcome to Minesweeper!"));
        assertTrue(output.contains("Here is your minefield:"));
    }
}