package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RunTest {
    Run r1;
    Run r2;
    Run r3;
    Run r4;

    @BeforeEach
    void runBefore() {
        r1 = new Run("morning run", 5.1, 23, "01/01/2024");
        r2 = new Run("night run", 6.6, 29.3, "01/02/2024");
        r3 = new Run("evening run", 7.2, 35, "01/03/2024");
        r4 = new Run("afternoon run", 8.1, 40.2, "01/04/2024");
    }


    @Test
    void testGetName() {
        assertEquals("morning run", r1.getName());
        assertEquals("night run", r2.getName());
    }

    @Test
    void testGetDistance() {
        assertEquals(5.1, r1.getDistance());
        assertEquals(6.6, r2.getDistance());
        assertEquals(7.2, r3.getDistance());
    }

    @Test
    void testGetTime() {
        assertEquals(23, r1.getTime());
        assertEquals(29.3, r2.getTime());
        assertEquals(35, r3.getTime());
    }
    @Test
    void testGetDate() {
        assertEquals("01/01/2024", r1.getDate());
        assertEquals("01/02/2024", r2.getDate());
        assertEquals("01/03/2024", r3.getDate());
    }

    @Test
    void testChangeName() {
        assertEquals("morning run", r1.getName());
        r1.changeName("new morning run");
        assertEquals("new morning run", r1.getName());
    }

    @Test
    void testChangeDistance() {
        assertEquals(5.1, r1.getDistance());
        r1.changeDistance(7.1);
        assertEquals(7.1, r1.getDistance());
        r1.changeDistance(-5);
        assertEquals(7.1, r1.getDistance());
    }

    @Test
    void testChangeTime() {
        assertEquals(23, r1.getTime());
        r1.changeTime(25);
        assertEquals(25, r1.getTime());
    }

    @Test
    void testChangeDate() {
        assertEquals("01/01/2024", r1.getDate());
        r1.changeDate("01/02/2024");
        assertEquals("01/02/2024", r1.getDate());
    }


}
