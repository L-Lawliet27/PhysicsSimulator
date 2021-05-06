package simulator.view;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StatusBar extends JPanel implements SimulatorObserver {

    private JLabel currTime; // for current time
    private JLabel currLaws; // for force laws
    private JLabel numOfBodies; // for number of bodies
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
        toolBar.add(time);

        JLabel bodies = new JLabel("Bodies: " + numOfBodies);
        toolBar.add(bodies);

        JLabel laws = new JLabel("Law: " + currLaws);
        toolBar.add(laws);

    }


    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
        currTime.setText(String.valueOf(time));
        currLaws.setText(fLawsDesc);
        numOfBodies.setText(String.valueOf(bodies.size()));
    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
        currTime.setText(String.valueOf(time));
        currLaws.setText(fLawsDesc);
        numOfBodies.setText(String.valueOf(bodies.size()));
    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {
        numOfBodies.setText(String.valueOf(bodies.size()));
    }

    @Override
    public void onAdvance(List<Body> bodies, double time) {
        currTime.setText(String.valueOf(time));
        numOfBodies.setText(String.valueOf(bodies.size()));
    }

    @Override
    public void onDeltaTimeChanged(double dt) {

    }

    @Override
    public void onForceLawsChanged(String fLawsDesc) {
        currLaws.setText(fLawsDesc);
    }
}
