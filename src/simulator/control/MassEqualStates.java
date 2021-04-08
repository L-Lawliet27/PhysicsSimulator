package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

public class MassEqualStates implements StateComparator{

    @Override
    public boolean equal(JSONObject s1, JSONObject s2) {

        boolean times = StateComparator.tEqual(s1,s2);

        JSONArray lb1 = s1.getJSONArray("bodies");
        JSONArray lb2 = s2.getJSONArray("bodies");

        boolean data = dataEqual(lb1, lb2);

        return times && data;
    }

    private boolean dataEqual(JSONArray a1, JSONArray a2){
        boolean x = true;

        if(a1.length() != a2.length()) return false;

        for (int i = 0; i < a1.length(); i++) {
            if( !StateComparator.idEqual(i, a1, a2) && !meq(i, a1, a2)){
                x = false;
                break;
            }
        }
        return x;
    }

    private boolean meq(int i, JSONArray ja1, JSONArray ja2){
        try{
            double m1 = ja1.getJSONObject(i).getDouble("m");
            double m2 = ja2.getJSONObject(i).getDouble("m");
            return m1==m2;
        }catch (NumberFormatException e){
            throw new IllegalArgumentException();
        }
    }



}
