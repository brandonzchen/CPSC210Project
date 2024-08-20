package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a Run List with multiple runs
public class RunLog implements Writable {
    private ArrayList<Run> runLog;
    private Run selectedRun;
    private EventLog eventLog;

    // EFFECTS: constructs run log with an empty list of runs
    public RunLog() {
        runLog = new ArrayList<Run>();
    }

    // MODIFIES: this
    // EFFECTS: adds a run to the run log
    public void addRun(Run r) {
        eventLog.getInstance().logEvent(new Event("A run was added to the Run Log"));
        runLog.add(r);
    }

    // MODIFIES: this
    // EFFECTS: removes a run to the run log
    public void removeRun(Run r) {
        eventLog.getInstance().logEvent(new Event("A run was removed to the Run Log"));
        runLog.remove(r);
    }

    // REQUIRES: to-be selected run needs to already by in the run log
    // EFFECTS: sets a selected run in the run log
    public void setSelectedRun(Run r) {
        for (Run r1: runLog) {
            if (r1.equals(r)) {
                selectedRun = r;
                break;
            }
        }
    }

    //EFFECTS: returns all workout dates as a String
    public String toStringDates() {
        String s = "";
        for (int i = 0; i < runLog.size(); i++) {
            s += (i + 1) + " - " + runLog.get(i).getDate() + "\n";
        }
        return s;
    }

    public Run getSelectedRun() {
        return selectedRun;
    }

    public int getNumRuns() {
        return runLog.size();
    }

    public ArrayList<Run> getRuns() {
        return runLog;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Runs", runsToJson());
        return json;
    }


    // EFFECTS: returns runs in the run log as a JSON array
    private JSONArray runsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Run r: runLog) {
            jsonArray.put(r.toJson());
        }
        return jsonArray;
    }


}
