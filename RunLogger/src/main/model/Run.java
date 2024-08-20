package model;

import org.json.JSONObject;
import persistence.Writable;


// Represents a run with an id, name, distance, time and date
public class Run implements Writable {

    private String name;              // run name
    private double distance;          // run distance
    private double time;              // run time
    private String date;              // run date

    /*
     * REQUIRES: runName and runDate have a non-zero length
     * EFFECTS: name of the run is set to runName; run id is a positive
     *          integer not assigned to any other account;
     *          distance is set to runDistance; time is set to runTime;
     *          date is set to runDate.
     */
    public Run(String runName, double runDistance, double runTime, String runDate) {
        name = runName;
        distance = runDistance;
        time = runTime;
        date = runDate;
    }


    public String getName() {
        return name;
    }

    public double getDistance() {
        return distance;
    }

    public double getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    /*
     * REQUIRES: newName has non-zero length
     * MODIFIES: this
     * EFFECTS: changes the name of the run to newName
     */
    public String changeName(String newName) {
        name = newName;
        return name;
    }

    /*
     * REQUIRES: newDistance > 0
     * MODIFIES: this
     * EFFECTS: adds distanceToAdd to distance
     */
    public double changeDistance(double newDistance) {
        if (newDistance > 0) {
            distance = newDistance;
            return distance;
        } else {
            return distance;
        }
    }

    /*
     * REQUIRES: newTime > 0
     * MODIFIES: this
     * EFFECTS: adds timeToAdd to time
     */
    public double changeTime(double newTime) {
        time = newTime;
        return time;
    }

    /*
     * REQUIRES: newDate has non-zero length
     * MODIFIES: this
     * EFFECTS: changes the date of the run to newDate
     */
    public String changeDate(String newDate) {
        date = newDate;
        return date;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("distance", distance);
        json.put("time", time);
        json.put("date", date);
        return json;
    }
}
