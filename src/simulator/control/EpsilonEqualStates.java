package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.Vector2D;

public class EpsilonEqualStates implements StateComparator {

    private double eps;

    public EpsilonEqualStates(double eps){
        this.eps=eps;
    }

    @Override
    public boolean equal(JSONObject s1, JSONObject s2) {

        boolean times = tEqual(s1,s2);

        JSONArray lb1 = s1.getJSONArray("bodies");
        JSONArray lb2 = s2.getJSONArray("bodies");

        boolean data = dataEqual(lb1,lb2);

        return times && data;
    }

    private boolean tEqual(JSONObject s1, JSONObject s2){
        return s1.getInt("time") == s2.getInt("time");
    }

    private boolean equal(double a, double b){
        return Math.abs(a-b)<=eps;
    }

    private boolean equal(Vector2D v1, Vector2D v2){
        return v1.distanceTo(v2)<=eps;
    }


    private boolean dataEqual(JSONArray a1, JSONArray a2){
        boolean x = true;

        if(a1.length() != a2.length()) return false;

        for (int i = 0; i < a1.length(); i++) {
            if(!eve(i,a1,a2)){
                x = false;
                break;
            }//if
        }//for
        return x;
    }


    private boolean eve(int i, JSONArray a1, JSONArray a2){

        boolean idSame= ieq(i, a1, a2);
        boolean massEqual = deq("m", i, a1, a2);
        boolean posEqual = deq("p", i, a1, a2);
        boolean forzEqual = deq("f", i, a1, a2);
        boolean velEqual = deq("v", i, a1, a2);

        return idSame && massEqual && posEqual && forzEqual && velEqual;
    }


    private boolean ieq(int i, JSONArray ja1, JSONArray ja2){

        return ja1.getJSONObject(i).get("id").equals(ja2.getJSONObject(i).get("id"));
    }


    private boolean deq(String s, int i, JSONArray ja1, JSONArray ja2){
        if(s.equals("m")){
            try {
                double m1 = ja1.getJSONObject(i).getDouble(s);
                double m2 = ja2.getJSONObject(i).getDouble(s);
                return equal(m1, m2);
            }catch (NumberFormatException e){
                throw new IllegalArgumentException();
            }
        }//if

        try {

            Vector2D v1 = new Vector2D(cord(ja1, i, s, 0), cord(ja1, i, s, 1));
            Vector2D v2 = new Vector2D(cord(ja2, i, s, 0), cord(ja2, i, s, 1));
            return equal(v1, v2);

        }catch (NullPointerException | NumberFormatException e){
            throw new IllegalArgumentException();
        }
    }

    private double cord(JSONArray j, int i, String s, int c){
        return j.getJSONObject(i).getJSONArray(s).getDouble(c);
    }




}
