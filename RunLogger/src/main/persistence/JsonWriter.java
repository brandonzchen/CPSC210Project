package persistence;

import model.RunLog;
import org.json.JSONObject;

import java.io.*;


public class JsonWriter {

    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs a writer to write to a destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens run log, throws FileNotFoundException if destination file cannot be
    // opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of run log to file
    public void write(RunLog rl) {
        JSONObject json = rl.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

}
