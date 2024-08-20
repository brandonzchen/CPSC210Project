package persistence;

import model.Run;
import model.RunLog;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            RunLog rl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyRunLog.json");
        try {
            RunLog rl = reader.read();
            assertEquals(0, rl.getNumRuns());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralRunLog.json");
        try {
            RunLog rl = reader.read();
            List<Run> runs = rl.getRuns();
            assertEquals(2, runs.size());
            checkRun("morning run", runs.get(0));
            checkRun("night run", runs.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}