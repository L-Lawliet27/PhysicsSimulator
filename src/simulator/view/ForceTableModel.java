package simulator.view;

import javax.swing.table.DefaultTableModel;

public class ForceTableModel extends DefaultTableModel {

    protected static boolean changed = false;
    private final Object[] columns = {"Key", "Value", "Description"};

    public ForceTableModel(){
        super();
        setRowCount(5);
        setColumnCount(3);
        setColumnIdentifiers(columns);

    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 1;
    }


    public static void resetC(){
        changed = false;
    }

//    public void clearRows(){
//        for (int i = 0; i < getRowCount(); i++) {
//            for (int j = 0; j < getColumnCount(); j++) {
//                setValueAt("",i,j);
//            }
//        }
//    }
}
