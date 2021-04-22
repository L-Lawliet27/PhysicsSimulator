package simulator.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PhysicsSimulator implements Observable<SimulatorObserver>{

    protected double realTPS;
    protected ForceLaws fL;
    protected List<Body> bodies;
    protected double currTime = 0.0;

    protected List<SimulatorObserver> obsList;

    public PhysicsSimulator(double realTPS, ForceLaws law){
        this.realTPS = tpsCheck(realTPS);
        fL = lawCheck(law);
        bodies = new ArrayList<>();
        obsList = new ArrayList<>();
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
        }

        fL.apply(bodies);

        for (Body b : bodies) {
            b.move(realTPS);
        }
        currTime += realTPS;

        for (SimulatorObserver ob : obsList) {
            ob.onAdvance(bodies, currTime);
        }
    }

    public void addBody(Body b){
        if(bodies.contains(b)) throw new IllegalArgumentException("Duplicate Body");

        bodies.add(b);

        for (SimulatorObserver ob : obsList) {
            ob.onBodyAdded(bodies, b);
        }
    }


    public JSONObject getState(){
        JSONObject s = new JSONObject();
        s.put("time", currTime);

        JSONArray b = new JSONArray();
        for (Body o : bodies) {
            b.put(o.getState());
        }

        s.put("bodies", b);

        return s;
    }

    public void reset(){
        bodies.clear();
        realTPS = 0.0;

        for (SimulatorObserver ob : obsList) {
            ob.onReset(bodies, currTime, realTPS, fL.toString());
        }
    }

    public void setDeltaTime(double dt){
        realTPS = tpsCheck(dt);

        for (SimulatorObserver ob : obsList) {
            ob.onDeltaTimeChanged(realTPS);
        }
    }

    public void setForceLaws(ForceLaws f){
        fL = lawCheck(f);

        for (SimulatorObserver ob : obsList) {
            ob.onForceLawsChanged(fL.toString());
        }
    }


    public String toString(){
        return getState().toString();
    }


    @Override
    public void addObserver(SimulatorObserver o) {
        if(!obsList.contains(o)) {
            obsList.add(o);
            obsList.get(obsList.indexOf(o)).onRegister(bodies, currTime, realTPS, fL.toString());
        }
    }
}
