package simulator.view;

import org.json.JSONArray;
import simulator.misc.Vector2D;

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

//    public void setValueC(Object aValue) {
//        this.setValueAt(aValue, 0, 1);
//    }
//
//    public void setValueG(Object aValue){
//        this.setValueAt(aValue,1,1);
//    }

    public JSONArray getValueC() {
        String val = String.valueOf(super.getValueAt(0,1)).replace("[","").replace("]","");
        String[] value = val.split(",");
        if(value.length==1 && value[0].equals("")){
            return new JSONArray();
        }
        return new JSONArray(value);
    }

    public String getValueG() {
        return String.valueOf(super.getValueAt(1,1));
    }
}
