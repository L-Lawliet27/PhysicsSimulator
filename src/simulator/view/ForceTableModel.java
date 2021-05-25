package simulator.view;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ForceTableModel extends DefaultTableModel {

    private final Object[] columns = {"Key", "Value", "Description"};
    private String lawType;
    private List<String> keyList;

    public ForceTableModel(){
        super();
        setRowCount(5);
        setColumnCount(3);
        setColumnIdentifiers(columns);
        keyList = new ArrayList<>();
        noLaw();
    }

    public ForceTableModel(JSONObject law){
        this();
        processLaw(law);
    }

    private void processLaw(JSONObject law){
        if(!law.isEmpty()) {
            lawType = law.get("type").toString();

            JSONObject data = law.getJSONObject("data");

            if(data.length() >= 1) {
                updateRows(data);
            }else{
                noLaw();
            }//else

        }//if
    }

    private void updateRows(JSONObject data){
        Iterator<String> keys = data.keys();
        int row = 0;

        while(keys.hasNext()){
            String key = keys.next();
            keyList.add(key);
            this.setValueAt(key, row, 0);
            this.setValueAt("", row, 1);
            this.setValueAt(data.get(key).toString(), row,2);
            row++;
        }
    }

    private void noLaw(){
        this.setValueAt("", 0, 0);
        this.setValueAt("", 0, 1);
        this.setValueAt("", 0,2);
    }


    public JSONObject setValues(JSONObject info) throws IllegalArgumentException{

        if(lawType.equals("mtfp")){
            JSONArray cValue = getJSONArray(0);
            setJsonData(cValue, keyList.get(0), info);
            String gValue = getValue(1);
            setJsonData(gValue, keyList.get(1), info);
        }

        if(lawType.equals("nlug")){
            String newtonValue = getValue(0);
            setJsonData(newtonValue, keyList.get(0), info);
        }

        return info;
    }


    private JSONArray getJSONArray(int row){
        String val = String.valueOf(super.getValueAt(row,1)).replace("[","").replace("]","");
        String[] value = val.split(",");
        if(value.length==1 && value[0].equals("")){
            return new JSONArray();
        }
        return new JSONArray(value);
    }

    private String getValue(int row){
        return String.valueOf(super.getValueAt(row,1));
    }


    private void setJsonData(String tVal, String jsonKey,JSONObject info){
        info.getJSONObject("data").put(jsonKey, tVal);
    }

    private void setJsonData(JSONArray jVal, String jsonKey, JSONObject info) {
        if (jVal.length() > 2 ) {
            throw new IllegalArgumentException( "\"" + jsonKey + "\"" + " Can't Have more than 2 Values");
        } else {
            info.getJSONObject("data").put(jsonKey, jVal);
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 1;
    }



}
