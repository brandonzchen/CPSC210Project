package ui;

import model.Run;
import model.RunLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


// Initializes the application and starts user stories
public class RunApp extends JPanel {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private static final String JSON_STORE = "./data/runlog.json";
    private Scanner input;
    private RunLog rl;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the run application
    public RunApp() {
        runRun();
    }


    // MODIFIES: this
    // EFFECTS: processes user input
    private void runRun() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAddRun();
        } else if (command.equals("s")) {
            doSelectRun();
        } else if (command.equals("r")) {
            doRemoveRun();
        } else if (command.equals("sv")) {
            saveRunLog();
        } else if (command.equals("l")) {
            loadRunLog();
        } else {
            System.out.println("\nSelection not valid... ");
        }

    }


    // MODIFIES: this
    // EFFECTS: prompts user for the name of run to remove
    private void doRemoveRun() {
        System.out.println("\nPlease type the name of the run you would like to remove");
        input.nextLine();
        String removeRunName = input.nextLine();
        int initial = rl.getNumRuns();
        for (Run r: rl.getRuns()) {
            if (r.getName().equals(removeRunName)) {
                rl.removeRun(r);
                System.out.println("Run removed successfully");
                break;
            }
        }
        if (initial == rl.getNumRuns()) {
            System.out.println("Run not found in run log");
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user for the name of run to select and selects run
    private void doSelectRun() {
        if (rl.getNumRuns() == 0) {
            System.out.println("\nThere are currently no runs in the run log");
        } else {
            System.out.println("\nPlease type the name of the run you would like to select");
            input.nextLine();
            String selectRunName = input.nextLine();
            boolean runFound = false;
            for (Run r: rl.getRuns()) {
                if (r.getName().equals(selectRunName)) {
                    runFound = true;
                    rl.setSelectedRun(r);
                    System.out.println("Run selected successfully");
                    displayRun();
                    processEditCommand();
                }
            }
            if (!runFound) {
                System.out.println("No run with given name found in the run log");
            }

        }

    }

    // REQUIRES: run selected
    // MODIFIES: this
    // EFFECTS: processes edit command
    private void processEditCommand() {
        String editCommand = input.next();
        if (editCommand.equals("dt")) {
            changeDate();
        } else if (editCommand.equals("n")) {
            changeName();
        } else if (editCommand.equals("ds")) {
            changeDistance();
        } else if (editCommand.equals("t")) {
            changeTime();
        }  else if (editCommand.equals("b")) {
            ;
        } else {
            System.out.println("\nSelection not valid... ");
        }

    }

    // MODIFIES: this
    // EFFECTS: prompts user the name of run they would like to change
    private void changeName() {
        System.out.println("Please enter the new name");
        input.nextLine();
        String newName = input.nextLine();
        rl.getSelectedRun().changeName(newName);
        System.out.println("Name changed successfully");
    }

    // MODIFIES: this
    // EFFECTS: prompts user to input a new time for selected run
    private void changeTime() {
        System.out.println("Please enter the new time");
        double newTime = input.nextDouble();
        rl.getSelectedRun().changeTime(newTime);
        System.out.println("Time changed successfully");
    }

    // MODIFIES: this
    // EFFECTS: prompts user to input new distance for selected run
    private void changeDistance() {
        System.out.println("Please enter the new distance");
        double newDistance = input.nextDouble();
        rl.getSelectedRun().changeDistance(newDistance);
        System.out.println("Distance changed successfully");
    }

    // MODIFIES: this
    // EFFECTS: prompts user to input new date for selected run
    private void changeDate() {
        System.out.println("Please enter the new date (MM/DD/YYYY)");
        String newDate = input.next();
        rl.getSelectedRun().changeDate(newDate);
        System.out.println("Date changed successfully");
    }

    // EFFECTS: displays selected run
    private void displayRun() {
        System.out.println(rl.getSelectedRun().getName());
        System.out.println("\nDate: " + rl.getSelectedRun().getDate());
        System.out.println("Distance: " + rl.getSelectedRun().getDistance());
        System.out.println("Time: " + rl.getSelectedRun().getTime());
        System.out.println("Pace: " + (rl.getSelectedRun().getTime() / rl.getSelectedRun().getDistance()) + "min/km");
        System.out.println("Which part of the run would you like to edit?");
        System.out.println("n -> Name");
        System.out.println("dt -> Date");
        System.out.println("ds -> Distance");
        System.out.println("t -> Time");
        System.out.println("b -> Back");

    }

    // MODIFIES: this
    // EFFECTS: prompts user to add details about new run
    private void doAddRun() {
        System.out.println("\nPlease type the name your run");
        input.nextLine();
        String runName = input.nextLine();
        System.out.println("Please enter the date of the run (MM/DD/YYYY)");
        String runDate = input.next();
        System.out.println("Please enter the distance of the run in kilometers");
        double runDistance = input.nextDouble();
        System.out.println("Please enter the time taken for the run in minutes");
        double runTime = input.nextDouble();
        Run newRun = new Run(runName, runDistance, runTime, runDate);
        rl.addRun(newRun);
        System.out.println("Run added successfully");
    }

    // EFFECTS: initializes run log, scanner, and json writer and reader
    private void init() {
        rl = new RunLog();
        input = new Scanner(System.in);
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);

    }

    // EFFECTS: displays main menu
    private void displayMenu() {
        if (rl.getNumRuns() != 0) {
            for (Run r: rl.getRuns()) {
                System.out.println(r.getDate() + "  " + r.getName() + ": ");
                System.out.println(r.getDistance() + " km, " + r.getTime() + " minutes\n");
            }
        }
        System.out.println("Select from: ");
        System.out.println("a -> add run");
        System.out.println("s -> select to view and edit run");
        System.out.println("r -> remove run");
        System.out.println("sv -> save Run Log");
        System.out.println("l -> load Run Log");
        System.out.println("q -> quit program");
    }

    // MODIFIES: this
    // EFFECTS: loads run log from file
    private void loadRunLog() {
        try {
            rl = jsonReader.read();
            System.out.println("Loaded Run Log successfully from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: saves run log to file
    private void saveRunLog() {
        try {
            jsonWriter.open();
            jsonWriter.write(rl);
            jsonWriter.close();
            System.out.println("Successfully saved Run Log to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

}


