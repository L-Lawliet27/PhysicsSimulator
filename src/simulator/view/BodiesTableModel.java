package simulator.view;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {

    private List<Body> bodyList;
    private Object[][] rowsData;

    private final String[] columnsNm = {"ID", "Mass", "Position", "Velocity", "Force"};

    public BodiesTableModel(Controller controller){
        bodyList = new ArrayList<>();
        controller.addObserver(this);
    }

    @Override
    public int getRowCount() {
        return bodyList.size();
    }

    @Override
    public int getColumnCount() {
        return columnsNm.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnsNm[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return rowsData[rowIndex][columnIndex];
    }


    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
        bodyList.clear();
        bodyList.addAll(bodies);
        rowsData = new Object[bodies.size()][columnsNm.length];

        for (Body b : bodies) {
            for (int i = 0; i < bodies.size(); i++) {
                rowsData[i][0] = b.getId();
                rowsData[i][1] = b.getMass();
                rowsData[i][2] = b.getPosition();
                rowsData[i][3] = b.getVelocity();
                rowsData[i][4] = b.getForce();
            }
        }
        
        fireTableStructureChanged();
    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
        bodyList.clear();
        fireTableStructureChanged();
    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {
        bodyList.add(b);
        fireTableStructureChanged();
    }

    @Override
    public void onAdvance(List<Body> bodies, double time) {
        bodyList.clear();
        bodyList.addAll(bodies);
        fireTableStructureChanged();
    }

    @Override
    public void onDeltaTimeChanged(double dt) {

    }

    @Override
    public void onForceLawsChanged(String fLawsDesc) {

    }
}
