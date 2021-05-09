package simulator.view;

import javax.swing.table.DefaultTableModel;

public class ForceTable extends DefaultTableModel {

    public static boolean changed = false;
    private final Object[] columns = {"Key", "Value", "Description"};

    public ForceTable(){
        super();
        setRowCount(5);
        setColumnCount(3);
        setColumnIdentifiers(columns);

    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 1;
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        super.setValueAt(aValue, row, column);
        changed = true;
    }

    public static void resetC(){
        changed = false;
    }

    public void clearRows(){
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColumnCount(); j++) {
                setValueAt("",i,j);
            }
        }
    }
}
