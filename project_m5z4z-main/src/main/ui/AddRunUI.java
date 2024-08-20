package ui;

import model.Run;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddRunUI {

    private Object[] fields;
    private Integer[] years;
    private String[] months;
    private Integer[] days;

    private JTextField nameBox;
    private JComboBox yearsBox;
    private JComboBox monthsBox;
    private  JComboBox daysBox;
    private JTextField distanceBox;
    private JTextField timeBox;


    public AddRunUI(MenuUI menuUI) {
        initializeOptions();
        initializeBoxes();

        fields = new Object[]{"Name:", nameBox, "Year:", yearsBox, "Month:", monthsBox,
                "Day: ", daysBox, "Distance (km): ", distanceBox, "Time (min): ", timeBox};

    }

    //MODIFIES: this
    //EFFECTS: sets up and initializes the combo boxes
    private void initializeBoxes() {
        nameBox = new JTextField();
        nameBox.setEditable(true);
        yearsBox = new JComboBox(years);
        monthsBox = new JComboBox(months);
        daysBox = new JComboBox(days);
        distanceBox = new JTextField();
        distanceBox.setEditable(true);
        timeBox = new JTextField();
        timeBox.setEditable(true);
    }

    //MODIFIES: this
    //EFFECTS: initializes the options and creates arrays for them
    private void initializeOptions() {
        years = new Integer[]{2020, 2021, 2022, 2023, 2024, 2025, 2026};
        months = new String[]{"January", "February", "March", "April",
                "May", "June", "July", "August", "September",
                "October", "November", "December",};
        days = new Integer[31];
        for (int i = 0; i < 31; i++) {
            days[i] = i + 1;
        }

    }

    //EFFECTS: returns the run with the given information on the pane
    @SuppressWarnings("methodlength")
    public Run getRunFromPane() {
        int result = JOptionPane.showConfirmDialog(null,
                fields, "Enter Run Information", JOptionPane.DEFAULT_OPTION);

        String name = "";
        String distance = "";
        String time = "";
        int year = 0;
        int day = 0;
        double distanceDouble = 0;
        double timeDouble = 0;
        String dateString = "";

        if (result == 0) {
            name = nameBox.getText();
            distance = distanceBox.getText();
            distanceDouble = Double.valueOf(distance);
            time = timeBox.getText();
            timeDouble = Double.valueOf(time);
            year = (int) yearsBox.getSelectedItem();
            day = (int) daysBox.getSelectedItem();
            String monthString = (String) monthsBox.getSelectedItem();
            String yearString = Integer.toString(year);
            String dayString = Integer.toString(day);
            dateString = monthString + " " + dayString + " " + yearString;
        }
        return new Run(name, distanceDouble, timeDouble, dateString);
    }
}
