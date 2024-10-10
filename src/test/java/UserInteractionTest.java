import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserInteractionTest {

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
    public void testGetGridSizeValidInput() {
        provideInput("4\n");
        UserInteraction ui = new UserInteraction();
        int gridSize = ui.getGridSize();
        assertEquals(4, gridSize);
    }

    @Test
    public void testGetGridSizeInvalidInput() {
        provideInput("abc\n4\n");
        UserInteraction ui = new UserInteraction();
        int gridSize = ui.getGridSize();
        assertEquals(4, gridSize);
        String output = getOutput();
        assertTrue(output.contains("Invalid input. Please enter an integer."));
    }

    @Test
    public void testGetMinesCountValidInput() {
        provideInput("3\n");
        UserInteraction ui = new UserInteraction();
        int minesCount = ui.getMinesCount(4);
        assertEquals(3, minesCount);
    }

    @Test
    public void testGetMinesCountInvalidInput() {
        provideInput("abc\n3\n");
        UserInteraction ui = new UserInteraction();
        int minesCount = ui.getMinesCount(4);
        assertEquals(3, minesCount);
        String output = getOutput();
        assertTrue(output.contains("Invalid input. Please enter an integer."));
    }
}