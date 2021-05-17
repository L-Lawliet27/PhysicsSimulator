package simulator.view;

public class MovingTableModel extends ForceTableModel {

    public MovingTableModel(String cDescr, String gDescr){
        super();
        this.setValueAt("c",0,0);
        this.setValueAt("",0,1);
        this.setValueAt(cDescr,0,2);

        this.setValueAt("g",1,0);
        this.setValueAt("",1,1);
        this.setValueAt(gDescr, 1,2);

    }

    public void setValueC(Object aValue) {
        this.setValueAt(aValue, 0, 1);
        changed = true;
    }

    public void setValueG(Object aValue){
        this.setValueAt(aValue,1,1);
        changed = true;
    }

    public String getValueC() {
        return String.valueOf(super.getValueAt(0,1));
    }

    public String getValueG() {
        return String.valueOf(super.getValueAt(1,1));
    }
}
