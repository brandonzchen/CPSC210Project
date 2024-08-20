package persistence;

import model.Run;
import model.RunLog;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads run log from file and returns it;
    // throws IOException if an error occurs reading data from file
    public RunLog read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseRunLog(jsonObject);
    }

    // EFFECTS: reads source file as a string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses run log from JSON object and returns it
    private RunLog parseRunLog(JSONObject jsonObject) {
        RunLog rl = new RunLog();
        addRuns(rl, jsonObject);
        return rl;
    }

    // MODIFIES: rl
    // EFFECTS: parses runs from JSON object and adds them to run log
    private void addRuns(RunLog rl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Runs");
        for (Object json : jsonArray) {
            JSONObject nextRun = (JSONObject) json;
            addRun(rl, nextRun);
        }
    }

    // MODIFIES: rl
    // EFFECTS: parses run from JSON object and adds it to the run log
    private void addRun(RunLog rl, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double distance = jsonObject.getDouble("distance");
        double time = jsonObject.getDouble("time");
        String date = jsonObject.getString("date");
        Run run = new Run(name, distance, time, date);
        rl.addRun(run);
    }
}
