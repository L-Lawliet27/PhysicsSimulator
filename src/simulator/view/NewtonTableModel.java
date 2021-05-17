package simulator.view;

public class NewtonTableModel extends ForceTableModel {

    public NewtonTableModel(String descr){
        super();
        this.setValueAt("G",0,0);
        this.setValueAt("",0,1);
        this.setValueAt(descr,0,2);
    }

    public void setValue(Object aValue) {
        this.setValueAt(aValue, 0, 1);
        changed = true;
    }

    public String getValue() {
        return String.valueOf(super.getValueAt(0,1));
    }
}
