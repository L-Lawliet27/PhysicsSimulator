package simulator.view;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

import javax.swing.*;
import java.util.List;

public class ControlPanel extends JPanel implements SimulatorObserver {

    private Controller ctrl;
    private boolean stopped;

    public ControlPanel(Controller controller){
        ctrl = controller;
        stopped = true;
        initGUI();
        ctrl.addObserver(this);
    }

    private void initGUI() {
        // TODO build the tool bar by adding buttons, etc.
    }

    private void run_sim(int n){
        if(n>0 && !stopped){
            try{
                ctrl.run(n);
            }catch (Exception e){
                // TODO show the error in a dialog box
                // TODO enable all buttons
                stopped = true;
            }

            SwingUtilities.invokeLater(() -> run_sim(n-1));
        }else {
            stopped=true;
            // TODO enable all buttons
        }
    }


    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {

    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {

    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {

    }

    @Override
    public void onAdvance(List<Body> bodies, double time) {

    }

    @Override
    public void onDeltaTimeChanged(double dt) {

    }

    @Override
    public void onForceLawsChanged(String fLawsDesc) {

    }
}
