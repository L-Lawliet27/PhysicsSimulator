package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

public class MassEqualStates implements StateComparator{

    @Override
    public boolean equal(JSONObject s1, JSONObject s2) {

        boolean times = tEqual(s1,s2);

        JSONArray lb1 = s1.getJSONArray("bodies");
        JSONArray lb2 = s2.getJSONArray("bodies");

        boolean data = dataEqual(lb1, lb2);

        return times && data;
    }

    private boolean tEqual(JSONObject s1, JSONObject s2){
        return s1.getInt("time") == s2.getInt("time");
    }

    private boolean dataEqual(JSONArray a1, JSONArray a2){
        boolean x = true;

        if(a1.length() != a2.length()) return false;

        for (int i = 0; i < a1.length(); i++) {
            if( neq("id", i, a1, a2) && neq("m", i, a1, a2)){
                x = false;
                break;
            }
        }
        
        return x;
    }

    private boolean neq(String s, int i, JSONArray ja1, JSONArray ja2){
        return !ja1.getJSONObject(i).get(s).equals(ja2.getJSONObject(i).get(s));
    }



}
