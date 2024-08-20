package ui;

import model.Run;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectRunUI extends JFrame implements ActionListener {

    private Run run;
    private MenuUI ui;

    protected JButton doneButton;

    private JScrollPane pane;
    private JTextPane text;
    private JPanel buttonPanel;
    private Font textFont;

    private JPanel namePanel;
    private JLabel nameText;
    private Font nameFont;

    public SelectRunUI(Run run, MenuUI menuUI) {
        this.ui = menuUI;
        this.run = run;

        initializeFrame();
        initializeButtonPanel();
        initializeNameHeader(run.getName());
        initializePane();

        this.add(pane);
        this.add(buttonPanel);
        this.setVisible(true);

    }

    //MODIFIES: this
    //EFFECTS: initializes and sets up the name header
    private void initializeNameHeader(String name) {
        nameFont = new Font("Arial", Font.BOLD, 20);
        namePanel = new JPanel();
        namePanel.setPreferredSize(new Dimension(500, 30));
        nameText = new JLabel(name);
        nameText.setFont(nameFont);
        namePanel.add(nameText);
    }

    //MODIFIES:this
    //EFFECTS: initializes the center pane
    private void initializePane() {
        initializeText();

        pane = new JScrollPane(text);
        pane.setPreferredSize(new Dimension(480, 570));
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    //MODIFIES: this
    //EFFECTS: initializes and sets up the button panel
    public void initializeButtonPanel() {
        initializeButtons();
        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(500, 100));

        buttonPanel.add(customizeButton(doneButton));
    }

    //MODIFIES: this
    //EFFECTS: initializes and sets up all buttons and adds action listeners to them
    private void initializeButtons() {
        doneButton = new JButton("Done Viewing");
        doneButton.addActionListener(this);

    }


    //MODIFIES: button
    //EFFECTS: customizes the buttons
    public JButton customizeButton(JButton button) {
        button.setBorderPainted(true);
        button.setPreferredSize(new Dimension(400, 60));
        button.setHorizontalTextPosition(JButton.CENTER);

        return button;
    }

    //MODIFIES: this
    //EFFECTS: initializes and sets up the text pane
    private void initializeText() {
        textFont = new Font("Arial", Font.PLAIN, 20);
        text = new JTextPane();
        text.setPreferredSize(new Dimension(450, 600));
        text.setEditable(false);
        text.setFont(textFont);
        StyledDocument doc = text.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        text.setText("Run Name: " + run.getName() + "\n"
                 + "Date: " + run.getDate() + "\n"
                 + "Distance: " + run.getDistance() + "km\n"
                 + "Time: " + run.getTime() + "min\n"
                 + "Pace: " + (run.getTime()) / (run.getDistance()) + "min/km");
    }


    //MODIFIES: this
    //EFFECTS: initializes the JFrame window
    private void initializeFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(500, 720));
        setResizable(false);
        setTitle("Selected Run");
        setLayout(new FlowLayout());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == doneButton) {
            this.dispose();
        }

    }
}
