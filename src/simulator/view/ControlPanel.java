package simulator.view;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ControlPanel extends JPanel implements SimulatorObserver {

    private Controller ctrl;
    private boolean stopped;

    private List<JButton> buttonList;

    private JToolBar toolBar;
    private JFileChooser fChoose;
    private JButton fileButton;
    private JButton forceButton;
    private JButton startButton;
    private JButton stopButton;
    private JButton exitButton;
    private JSpinner stepSpinner;
    private JTextField deltaTimeField;

    public ControlPanel(Controller controller){
        ctrl = controller;
        stopped = true;
        buttonList = new ArrayList<>();
        initGUI();
        ctrl.addObserver(this);
    }

    private void initGUI() {
        // TODO build the tool bar by adding buttons, etc.

        toolBar = new JToolBar();
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 12,5 ));
        this.add(toolBar, BorderLayout.WEST);


        //File Button
        fChoose = new JFileChooser();

        File resources = new File("resources");
        fChoose.setCurrentDirectory(resources);

        fileButton = new JButton();
        fileButton.setToolTipText("Select a File to Load into the Simulator");
        fileButton.setIcon(createImageIcon("resources/icons/open.png"));
        fileButton.setEnabled(true);
        fileButton.addActionListener(e -> loadFile());
        buttonList.add(fileButton);
        toolBar.add(fileButton);
        //File Button

        toolBar.addSeparator();

        //Force Laws Button
        forceButton = new JButton();
        forceButton.setToolTipText("Select a Force Law to apply into the Simulator");
        forceButton.setIcon(createImageIcon("resources/icons/physics.png"));
        forceButton.setEnabled(true);

        ForceDialog fc = new ForceDialog(ctrl);

        forceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fc.setV();
//                new ForceDialog(ctrl);
            }
        });
        buttonList.add(forceButton);
        toolBar.add(forceButton);
        //Force Laws Button

        toolBar.addSeparator();

        //Start Button
        startButton = new JButton();
        startButton.setToolTipText("Run the Simulation");
        startButton.setIcon(createImageIcon("resources/icons/run.png"));
        startButton.setEnabled(true);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disableButtons();
                stopButton.setEnabled(true);
                Integer val = (Integer) stepSpinner.getValue();
                double time = Double.parseDouble(String.valueOf(deltaTimeField.getText()));
                stopped = false;
                //delta
                ctrl.setDeltaTime(time);
                run_sim(val);
            }
        });
        buttonList.add(startButton);
        toolBar.add(startButton);
        //Start Button

        //Stop Button
        stopButton = new JButton();
        stopButton.setToolTipText("Stop the Simulation");
        stopButton.setIcon(createImageIcon("resources/icons/stop.png"));
        stopButton.setEnabled(true);
        stopButton.addActionListener(e -> stopped=true);
        buttonList.add(stopButton);
        toolBar.add(stopButton);
        //Stop Button


        //Steps JSpinner
        JLabel stepsLabel = new JLabel("Steps:");
        toolBar.add(stepsLabel);
        stepSpinner = new JSpinner(new SpinnerNumberModel(1000, 0, null, 50));
        stepSpinner.setEditor(new JSpinner.NumberEditor(stepSpinner, "###,###,###"));
        stepSpinner.setPreferredSize(new Dimension(100,30));
        toolBar.add(stepSpinner);
        //Steps JSpinner

        //Delta-Time Field
        JLabel deltaLabel = new JLabel("Delta-Time");
        toolBar.add(deltaLabel);
        deltaTimeField = new JTextField(6);
        toolBar.add(deltaTimeField);
        //Delta-Time Field

        //Exit Button
        exitButton = new JButton();
        exitButton.setToolTipText("Exit Simulator");
        exitButton.setIcon(createImageIcon("resources/icons/exit.png"));
        exitButton.setEnabled(true);
        exitButton.addActionListener(e -> exit());
        buttonList.add(exitButton);
        toolBar.add(exitButton);
        //Exit Button

        this.setVisible(true);
    }

    private void disableButtons(){
        for (JButton b : buttonList) {
                b.setEnabled(false);
        }
    }

    private void enableButtons(){
        for (JButton b : buttonList) {
            b.setEnabled(true);
        }
    }

    private void loadFile(){
        int op = fChoose.showOpenDialog(this.getParent());
        if(op==JFileChooser.APPROVE_OPTION){
            try {
                File getFile = fChoose.getSelectedFile();
                ctrl.reset();
                ctrl.loadBodies(new FileInputStream(getFile));

            }catch (FileNotFoundException e){
                JOptionPane.showMessageDialog(this, "Couldn't Open file - " + e.getMessage());
            }
        }
    }


    private Icon createImageIcon(String source) {
        return new ImageIcon(Toolkit.getDefaultToolkit().createImage(source));
    }

    private void run_sim(int n){
        if(n>0 && !stopped){
            try{
                ctrl.run(n);

            }catch (Exception e){
                JOptionPane.showMessageDialog(this, "Error, Cannot Run Simulation - " + e.getMessage());
                enableButtons();
                stopped = true;
            }

            SwingUtilities.invokeLater(() -> run_sim(n-1));
        }else {
            stopped=true;
            enableButtons();
        }
    }

    private void exit(){
        int n = JOptionPane.showOptionDialog(this, "Do you Want to Exit?",
                "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, null, null);
        if(n==0)
            System.exit(0);
    }



    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
        deltaTimeField.setText(String.valueOf(dt));
    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
//        deltaTimeField.setText(String.valueOf(dt));
    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {

    }

    @Override
    public void onAdvance(List<Body> bodies, double time) {

    }

    @Override
    public void onDeltaTimeChanged(double dt) {
        deltaTimeField.setText(String.valueOf(dt));
    }

    @Override
    public void onForceLawsChanged(String fLawsDesc) {

    }
}
