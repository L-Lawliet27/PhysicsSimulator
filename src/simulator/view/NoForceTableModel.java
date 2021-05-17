package simulator.view;

public class NoForceTableModel extends ForceTableModel {
    public NoForceTableModel(){
        this.setValueAt("",0,0);
        this.setValueAt("",0,1);
        this.setValueAt("",0,2);
    }
}
