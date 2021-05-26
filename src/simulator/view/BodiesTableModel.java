package simulator.view;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

import javax.swing.table.AbstractTableModel;
import java.util.*;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {

    private int rowCount;
    private final String[] columnsNm = {"ID", "Mass", "Position", "Velocity", "Force"};
    private final int columnLength = columnsNm.length;
    private Map<Integer, Vector<Object>> rData;

    public BodiesTableModel(Controller controller){
        rowCount = 1;
        rData = new HashMap<>();
        controller.addObserver(this);
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public int getColumnCount() {
        return columnLength;
    }

    @Override
    public String getColumnName(int column) {
        return columnsNm[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        if(rData.isEmpty()) return "";

        return rData.get(rowIndex).get(columnIndex);
    }


    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
        updateRowCount(bodies);
        updateTable(bodies);
    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
        rData.clear();
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
        advanceTable(bodies);
    }

    @Override
    public void onDeltaTimeChanged(double dt) {

    }

    @Override
    public void onForceLawsChanged(String fLawsDesc) {

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);
    }

    private void updateTable(List<Body> bodyList){
        if(!bodyList.isEmpty()) {
            int i = 0;

            for (Body b : bodyList) {
                Vector<Object> data = new Vector<>();

                data.add(b.getId());
                data.add(b.getMass());
                data.add(b.getPosition());
                data.add(b.getVelocity());
                data.add(b.getForce());

                rData.put(i,data);
                i++;
            }//foreach
        }//if
        fireTableStructureChanged();
    }

    private void advanceTable(List<Body> bodyList){
        if(!bodyList.isEmpty()) {
            int i = 0;
            for (Body b : bodyList) {
                rData.get(i).set(1, b.getMass());
                rData.get(i).set(2, b.getPosition());
                rData.get(i).set(3, b.getVelocity());
                rData.get(i).set(4, b.getForce());

                i++;
            }//foreach
        }//if
        fireTableDataChanged();
    }

    private void updateRowCount(List<Body> bodyList){
        if(!bodyList.isEmpty()){
            rowCount = bodyList.size();
        }else
            rowCount = 1;
    }

}
