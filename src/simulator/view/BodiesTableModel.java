package simulator.view;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {

    private List<Body> bodyList;
    private Object[][] rowsData;

    private final String[] columnsNm = {"ID", "Mass", "Position", "Velocity", "Force"};
    private final int columnLength = columnsNm.length;

    public BodiesTableModel(Controller controller){
        bodyList = new ArrayList<>();
        controller.addObserver(this);
    }

    @Override
    public int getRowCount() {
        if (!bodyList.isEmpty()){
            return bodyList.size();
        }else{
            return 1;
        }
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
        updateRowsData();
        updateTable();
    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
        bodyList.clear();
        updateRowsData();
        updateTable();
    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {
        bodyList.add(b);
        updateRowsData();
        updateTable();
    }

    @Override
    public void onAdvance(List<Body> bodies, double time) {
        bodyList.clear();
        bodyList.addAll(bodies);
        updateTable();
    }

    @Override
    public void onDeltaTimeChanged(double dt) {

    }

    @Override
    public void onForceLawsChanged(String fLawsDesc) {

    }


    private void updateTable(){
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

    private void updateRowsData(){
        if(!bodyList.isEmpty()) {
            rowsData = new Object[bodyList.size()][columnLength];
        }else{
            rowsData = new Object[0][columnLength];
        }
    }

}
