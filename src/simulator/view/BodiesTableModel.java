package simulator.view;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {

    private List<Body> bodies;
    private Controller ctrl;

    public BodiesTableModel(Controller controller){
        bodies = new ArrayList<>();
        ctrl = controller;
    }

    @Override
    public int getRowCount() {
        // TODO complete
        return 0;
    }

    @Override
    public int getColumnCount() {
        // TODO complete
        return 0;
    }

    @Override
    public String getColumnName(int column) {
        // TODO complete
        return null;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // TODO complete
        return null;
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
