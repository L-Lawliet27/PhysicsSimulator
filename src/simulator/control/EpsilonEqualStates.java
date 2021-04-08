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

        boolean times = StateComparator.tEqual(s1,s2);

        JSONArray lb1 = s1.getJSONArray("bodies");
        JSONArray lb2 = s2.getJSONArray("bodies");

        boolean data = dataEqual(lb1,lb2);

        return times && data;
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

        boolean idSame= StateComparator.idEqual(i, a1, a2);
        boolean massEqual = meq(i, a1, a2);
        boolean posEqual = deq("p", i, a1, a2);
        boolean forEqual = deq("f", i, a1, a2);
        boolean velEqual = deq("v", i, a1, a2);

        return idSame && massEqual && posEqual && forEqual && velEqual;
    }


    private boolean meq(int i, JSONArray ja1, JSONArray ja2){
        try {
            double m1 = ja1.getJSONObject(i).getDouble("m");
            double m2 = ja2.getJSONObject(i).getDouble("m");
            return equalMass(m1, m2);
        }catch (NumberFormatException e){
            throw new IllegalArgumentException();
        }
    }


    private boolean deq(String s, int i, JSONArray ja1, JSONArray ja2){
        try {
            Vector2D v1 = new Vector2D(StateComparator.cord(ja1, i, s, 0), StateComparator.cord(ja1, i, s, 1));
            Vector2D v2 = new Vector2D(StateComparator.cord(ja2, i, s, 0), StateComparator.cord(ja2, i, s, 1));
            return equalEpsDistance(v1, v2);

        }catch (NullPointerException | NumberFormatException e){
            throw new IllegalArgumentException();
        }
    }


    private boolean equalMass(double a, double b){
        return Math.abs(a-b)<=eps;
    }

    private boolean equalEpsDistance(Vector2D v1, Vector2D v2){
        return v1.distanceTo(v2)<=eps;
    }

}
