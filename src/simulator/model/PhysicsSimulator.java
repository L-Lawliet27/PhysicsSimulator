package simulator.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PhysicsSimulator {

    protected double realTPS;
    protected ForceLaws fL;
    protected List<Body> bodies;
    protected double currTime = 0.0;

    public PhysicsSimulator(double realTPS, ForceLaws law){
        this.realTPS = tpsCheck(realTPS);
        fL = lawCheck(law);
        bodies = new ArrayList<>();
    }

    private double tpsCheck(double t){
        if(t < 0.0)
            throw new IllegalArgumentException("Value is Non-Positive");

        return t;
    }

    private ForceLaws lawCheck(ForceLaws l){
        if(l==null)
            throw new IllegalArgumentException("Force Law is Null");

        return l;
    }

    public void advance(){

        for (Body o : bodies) {
            o.resetForce();
            o.move(realTPS);
            currTime += realTPS;
        }

        fL.apply(bodies);

    }

    public void addBody(Body b){
        if(bodies.contains(b)) throw new IllegalArgumentException("Duplicate Body");

        bodies.add(b);
    }


    public JSONObject getState(){
        JSONObject s = new JSONObject();
        s.put("time", realTPS);

        JSONArray b = new JSONArray();
        for (Body o : bodies) {
            b.put(o.getState());
        }

        s.put("bodies", b);

        return s;
    }

    public String toString(){
        return getState().toString();
    }




}
