package simulator.view;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {

    private Object[][] rowsData;
    private int rowCount;
    private final String[] columnsNm = {"ID", "Mass", "Position", "Velocity", "Force"};
    private final int columnLength = columnsNm.length;

    public BodiesTableModel(Controller controller){
        rowCount = 1;
        rowsData = new Object[20][columnLength];
        controller.addObserver(this);
    }

    @Override
    public int getRowCount() {
        return rowCount;
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
        updateRowCount(bodies);
        updateTable(bodies);
    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
        rowsData = new Object[20][columnLength];
        updateRowCount(bodies);
        updateTable(bodies);
    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {
        updateRowCount(bodies);
        updateTable(bodies);
    }

    @Override
    public void onAdvance(List<Body> bodies, double time) {
        updateTable(bodies);
    }

    @Override
    public void onDeltaTimeChanged(double dt) {

    }

    @Override
    public void onForceLawsChanged(String fLawsDesc) {

    }


    private void updateTable(List<Body> bodyList){
        if(!bodyList.isEmpty()) {
            int i = 0;
            for (Body b : bodyList) {
                rowsData[i][0] = b.getId();
                rowsData[i][1] = b.getMass();
                rowsData[i][2] = b.getPosition();
                rowsData[i][3] = b.getVelocity();
                rowsData[i][4] = b.getForce();
                i++;
            }//foreach
        }//if
        fireTableStructureChanged();
    }

    private void updateRowCount(List<Body> bodyList){
        if(!bodyList.isEmpty()){
            rowCount = bodyList.size();
        }else
            rowCount = 1;
    }

}
