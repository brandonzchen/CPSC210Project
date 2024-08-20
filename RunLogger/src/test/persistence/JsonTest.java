package persistence;

import model.Run;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkRun(String name, Run run) {
        assertEquals(name, run.getName());
    }
}