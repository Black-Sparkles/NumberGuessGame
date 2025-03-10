package com.studentapp;

import org.junit.Before;
import org.junit.Test;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.*;

public class NumberGuessServletTest {

    private NumberGuessServlet servlet;

    @Before
    public void setUp() throws ServletException {
        servlet = new NumberGuessServlet();
        servlet.init(); // Initialize servlet (sets up target number)
    }

    @Test
    public void testTargetNumberWithinRange() {
        int target = servlet.getTargetNumber();
        assertTrue("Target number should be between 1 and 100", target >= 1 && target <= 100);
    }

    @Test
    public void testCorrectGuess() throws ServletException, IOException {
        int target = servlet.getTargetNumber();
        assertEquals(target, servlet.checkGuess(target));
    }

    @Test
    public void testGuessTooLow() throws ServletException, IOException {
        int target = servlet.getTargetNumber();
        assertEquals(-1, servlet.checkGuess(target - 1)); // Expect -1 for low guess
    }

    @Test
    public void testGuessTooHigh() throws ServletException, IOException {
        int target = servlet.getTargetNumber();
        assertEquals(1, servlet.checkGuess(target + 1)); // Expect 1 for high guess
    }
}

