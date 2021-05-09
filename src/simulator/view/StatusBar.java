package simulator.view;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class StatusBar extends JPanel implements SimulatorObserver {

    private double currTime; // for current time
    private String currLaws; // for force laws
    private int numOfBodies; // for number of bodies
    private JToolBar toolBar;

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

        JLabel time = new JLabel("Time: " + currTime);
        time.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 100));
        toolBar.add(time);

        JLabel bodies = new JLabel("Bodies: " + numOfBodies);
        bodies.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 100));
        toolBar.add(bodies);

        JLabel laws = new JLabel("Law: " + currLaws);
        laws.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 100));
        toolBar.add(laws);

    }


    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
        currTime = time;
        currLaws = fLawsDesc;
        numOfBodies = bodies.size();
    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
        currTime = time;
        currLaws = fLawsDesc;
        numOfBodies = bodies.size();
    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {
        numOfBodies = bodies.size();
    }

    @Override
    public void onAdvance(List<Body> bodies, double time) {
        currTime = time;
        numOfBodies = bodies.size();
    }

    @Override
    public void onDeltaTimeChanged(double dt) {

    }

    @Override
    public void onForceLawsChanged(String fLawsDesc) {
        currLaws = fLawsDesc;
    }
}
