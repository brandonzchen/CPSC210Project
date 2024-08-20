package ui;

import model.Run;
import model.RunLog;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class MenuUI extends JFrame implements ActionListener, WindowListener {

    private RunLog rl;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private String source = "./data/runlog.json";
    private AddRunUI runUI;
    private EventLog eventLog;

    private ImageIcon runningMan;
    private JLabel imageLabel;
    private JLabel runLogText;
    private Font titleFont;

    private JTextPane runTextPane;
    private JScrollPane runsPane;
    private String runsText;
    private Font textFont;

    private JPanel mainButtonPanel;
    private JPanel topPanel;
    private JPanel centrePanel;

    private JButton addRunButton;
    private JButton selectRunButton;
    private JButton saveRunLogButton;
    private JButton loadRunLogButton;

    public MenuUI() {
        initializeFrame();
        initializeButtons();
        initializeHeaderLabels();
        initializeFields();

        initializeButtonPanel(mainButtonPanel);
        initializeTopPanel(topPanel);
        initializeCentrePanel(centrePanel);

        this.add(mainButtonPanel, BorderLayout.SOUTH);
        this.add(topPanel, BorderLayout.NORTH);
        this.add(centrePanel, BorderLayout.CENTER);

        this.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: sets up the centre panel and panes
    private void initializeCentrePanel(JPanel panel) {
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(new Color(160, 32, 240));
        panel.setPreferredSize(new Dimension(100, 500));

        setupScrollPanes();

        panel.add(runsPane);
    }

    //MODIFIES: this
    //EFFECTS: Initializes and sets up top panel
    private void initializeTopPanel(JPanel panel) {
        panel.setLayout(new FlowLayout());
        panel.setPreferredSize(new Dimension(1000, 100));
        topPanel.add(imageLabel);
        topPanel.add(runLogText);
        panel.add(customizeButton(saveRunLogButton));
        panel.add(customizeButton(loadRunLogButton));
        panel.setBackground(new Color(226, 226, 224));
    }

    //MODIFIES: this
    //EFFECTS: sets up the scroll panes for the center panels
    private void setupScrollPanes() {
        setupTextPanes();

        runsPane = new JScrollPane(runTextPane);
        runsPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        runsPane.setPreferredSize(new Dimension(900, 455));
    }

    //MODIFIES: this
    //EFFECTS: Sets up the text panes for the scroll panes
    private void setupTextPanes() {
        textFont = new Font("Comic Sans", Font.PLAIN, 20);
        runsText = "";
        runTextPane = new JTextPane();
        runTextPane.setFont(textFont);
        runTextPane.setPreferredSize(new Dimension(900, 600));
        runTextPane.setEditable(false);
    }

    //MODIFIES: this
    //EFFECTS: initializes and sets up the button panel
    private void initializeButtonPanel(JPanel panel) {
        panel.setLayout(new GridLayout(1, 2, 0, 0));
        panel.setPreferredSize(new Dimension(1000, 100));
        panel.add(customizeButton(addRunButton));
        panel.add(customizeButton(selectRunButton));
    }


    //MODIFIES: button
    //EFFECTS: customizes the button and returns it
    private JButton customizeButton(JButton button) {
        button.setBorderPainted(true);
        button.setPreferredSize(new Dimension(250, 100));
        button.setHorizontalTextPosition(JButton.CENTER);

        return button;

    }


    //MODIFIES: this
    //EFFECTS: initializes all the fields
    private void initializeFields() {
        jsonWriter = new JsonWriter(source);
        jsonReader = new JsonReader(source);
        rl = new RunLog();
        mainButtonPanel = new JPanel();
        topPanel = new JPanel();
        centrePanel = new JPanel();
        runUI = new AddRunUI(this);

    }

    //MODIFIES: this
    //EFFECTS: initializes the label for the header
    private void initializeHeaderLabels() {
        Image image = Toolkit.getDefaultToolkit().getImage("./data/runningman2.png");
        runningMan = new ImageIcon(image);
        imageLabel = new JLabel();
        imageLabel.setIcon(runningMan);
        runLogText = new JLabel("Run Log      ");
        titleFont = new Font("Comic Sans", Font.BOLD, 50);
        runLogText.setFont(titleFont);
    }

    //MODIFIES: this
    //EFFECTS: instantiates the buttons and sets them up
    private void initializeButtons() {
        addRunButton = new JButton("Add Run");
        selectRunButton = new JButton("View Run");
        saveRunLogButton = new JButton("Save Run Log");
        loadRunLogButton = new JButton("Load Run Log");
        addActionListenerToButtons();

    }

    //MODIFIES: this
    //EFFECTS: adds action listeners to all the buttons
    private void addActionListenerToButtons() {
        addRunButton.addActionListener(this);
        selectRunButton.addActionListener(this);
        saveRunLogButton.addActionListener(this);
        loadRunLogButton.addActionListener(this);

    }

    //MODIFIES: this
    //EFFECTS: customizes the JFrame
    private void initializeFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1000, 700));
        setResizable(false);
        setTitle("RunLog");
        setLayout(new BorderLayout());
        this.addWindowListener(this);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addRunButton) {
            Run newRun = runUI.getRunFromPane();
            rl.addRun(newRun);
            runTextPane.setText(rl.toStringDates());
        }

        if (e.getSource() == selectRunButton) {
            if (rl.getRuns().size() > 0) {
                new SelectRunUI(rl.getRuns().get(chooseRun()), this);
            }
        }

        if (e.getSource() == saveRunLogButton) {
            saveRunLog();
        }
        if (e.getSource() == loadRunLogButton) {
            loadRunLog();
            runTextPane.setText(rl.toStringDates());
        }


    }

    //MODIFIES: this
    //EFFECTS: loads run log from source
    private void loadRunLog() {
        try {
            rl = jsonReader.read();
            JOptionPane.showConfirmDialog(null, "Loaded run log from " + source,
                    "Loaded File", JOptionPane.DEFAULT_OPTION);
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(null, "Unable to read from file: " + source,
                    "Loaded File", JOptionPane.DEFAULT_OPTION);
        }
    }

    //EFFECTS: saves run log to source
    private void saveRunLog() {
        try {
            jsonWriter.open();
            jsonWriter.write(rl);
            jsonWriter.close();
            JOptionPane.showConfirmDialog(null, "Saved run log to " + source,
                    "Saved File", JOptionPane.DEFAULT_OPTION);
        } catch (FileNotFoundException e) {
            JOptionPane.showConfirmDialog(null, "Unable to write to file: " + source,
                    "Saved File", JOptionPane.DEFAULT_OPTION);
        }
    }

    //EFFECTS: returns the integer number of the run chosen
    private int chooseRun() {
        Integer[] numbers = new Integer[rl.getRuns().size()];
        for (int i = 0; i < rl.getRuns().size(); i++) {
            numbers[i] = i + 1;
        }

        JComboBox optionsBox = new JComboBox(numbers);
        Object[] options = {"Choose Run Number: ", optionsBox};

        JOptionPane.showConfirmDialog(null,
                options, "Choose Run To View", JOptionPane.DEFAULT_OPTION);
        int result = optionsBox.getSelectedIndex();
        return result;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        for (Event event: eventLog.getInstance()) {
            System.out.println(event);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
