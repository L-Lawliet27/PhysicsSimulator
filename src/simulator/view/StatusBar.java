package simulator.view;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class StatusBar extends JPanel implements SimulatorObserver {

    private JLabel timeStatus;
    private JLabel bodyStatus;
    private JLabel lawStatus;

    private JToolBar toolBar;

    private final String timePrefix = "Time: ";
    private final String bodiesPrefix = "Bodies: ";
    private final String lawsPrefix = "Law: ";


    public StatusBar(Controller ctrl){
        initGUI();
        ctrl.addObserver(this);
        
    }

    private void initGUI() {
        this.setLayout( new FlowLayout( FlowLayout.LEFT ));
        this.setBorder( BorderFactory.createBevelBorder( 1 ));
        // TODO complete the code to build the tool bar
        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        this.add(toolBar, BorderLayout.PAGE_START);

        timeStatus = new JLabel(timePrefix + 0);
        timeStatus.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 100));
        toolBar.add(timeStatus);

        bodyStatus = new JLabel(bodiesPrefix + 0);
        bodyStatus.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 100));
        toolBar.add(bodyStatus);

        lawStatus = new JLabel(lawsPrefix + "NONE");
        lawStatus.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 100));
        toolBar.add(lawStatus);

    }


    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
        timeStatus.setText(timePrefix + time);
        lawStatus.setText(lawsPrefix + fLawsDesc);
        bodyStatus.setText(bodiesPrefix + bodies.size());
    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
        timeStatus.setText(timePrefix + time);
        lawStatus.setText(lawsPrefix + fLawsDesc);
        bodyStatus.setText(bodiesPrefix + bodies.size());
    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {

        bodyStatus.setText(bodiesPrefix + bodies.size());
    }

    @Override
    public void onAdvance(List<Body> bodies, double time) {
        timeStatus.setText(timePrefix + time);
        bodyStatus.setText(bodiesPrefix + bodies.size());
    }

    @Override
    public void onDeltaTimeChanged(double dt) {

    }

    @Override
    public void onForceLawsChanged(String fLawsDesc) {
        lawStatus.setText(lawsPrefix + fLawsDesc);
    }
}
