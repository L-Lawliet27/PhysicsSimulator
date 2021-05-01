package simulator.view;

import javax.swing.table.DefaultTableModel;

public class ForceTable extends DefaultTableModel {

    public static boolean changed = false;

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
}
