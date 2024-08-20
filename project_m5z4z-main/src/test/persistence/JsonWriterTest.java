package persistence;

import model.Run;
import model.RunLog;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            RunLog rl = new RunLog();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {

        }
    }

    @Test
    void testWriterEmptyRunLog() {
        try {
            RunLog rl = new RunLog();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyRunLog.json");
            writer.open();
            writer.write(rl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyRunLog.json");
            rl = reader.read();
            assertEquals(0, rl.getNumRuns());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            RunLog rl = new RunLog();
            rl.addRun(new Run("morning run", 15, 45, "01/01/2024"));
            rl.addRun(new Run("night run", 10, 30, "01/02/2024"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralRunLog.json");
            writer.open();
            writer.write(rl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralRunLog.json");
            rl = reader.read();
            List<Run> runs = rl.getRuns();
            assertEquals(2, runs.size());
            checkRun("morning run",  runs.get(0));
            checkRun("night run", runs.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}