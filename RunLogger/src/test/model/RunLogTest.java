package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RunLogTest {
    RunLog rl;
    Run r1;
    Run r2;

    @BeforeEach
    void runBefore() {
        rl = new RunLog();
        r1 = new Run("morning run", 5.1, 23, "01/01/2024");
        r2 = new Run("night run", 6.6, 29.3, "01/02/2024");
    }

    @Test
    void testConstructor() {
        assertEquals(0, rl.getNumRuns());
    }

    @Test
    void testAddRun() {
        assertEquals(0, rl.getNumRuns());
        rl.addRun(r1);
        assertEquals(1, rl.getNumRuns());
    }

    @Test
    void testRemoveRun() {
        assertEquals(0, rl.getNumRuns());
        rl.addRun(r1);
        assertEquals(1, rl.getNumRuns());
        rl.removeRun(r1);
        assertEquals(0, rl.getNumRuns());
    }

    @Test
    void testSetSelectedRun() {
        rl.addRun(r1);
        rl.setSelectedRun(r1);
        assertEquals(r1, rl.getSelectedRun());
        rl.setSelectedRun(r2);
        assertEquals(r1, rl.getSelectedRun());

    }

    @Test
    void testGetSelectedRun() {
        rl.addRun(r1);
        rl.addRun(r2);
        rl.setSelectedRun(r2);
        assertEquals(r2, rl.getSelectedRun());
    }

    @Test
    void testGetNumRuns() {
        assertEquals(0, rl.getNumRuns());
        rl.addRun(r1);
        assertEquals(1, rl.getNumRuns());
        rl.addRun(r2);
        assertEquals(2, rl.getNumRuns());
    }

    @Test
    void testGetRuns() {
        ArrayList<Run> testLog = new ArrayList<>();
        rl.addRun(r1);
        testLog.add(r1);
        assertEquals(testLog, rl.getRuns());
        rl.addRun(r2);
        testLog.add(r2);
        assertEquals(testLog, rl.getRuns());
    }

    @Test
    void testToStringDates() {
        rl.addRun(r1);
        assertEquals("1 - 01/01/2024\n", rl.toStringDates());
    }
}
