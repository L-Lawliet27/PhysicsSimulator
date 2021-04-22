package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

public class Controller {

    private PhysicsSimulator phy;
    private Factory<Body> fb;
    private Factory<ForceLaws> ff;


    public Controller(PhysicsSimulator physicsSimulator, Factory<Body> bodyFactory, Factory<ForceLaws> forceFactory){
        phy=physicsSimulator;
        fb=bodyFactory;
        ff=forceFactory;
    }

    public void loadBodies(InputStream in){
        JSONObject jInput = new JSONObject(new JSONTokener(in));
        JSONArray jar = jInput.getJSONArray("bodies");

        for (int i = 0; i < jar.length(); i++) {
            Body b = fb.createInstance(jar.getJSONObject(i));
            phy.addBody(b);
        }

    }


    public void run(int steps, OutputStream out, InputStream expOut, StateComparator cmp) throws StatesNotEqualException {
        PrintStream p = new PrintStream(out);
        p.println("{");
        p.println("\"states\": [");

        if(expOut != null) {
            JSONObject jEx = new JSONObject(new JSONTokener(expOut));
            JSONArray arrEx = jEx.getJSONArray("states");

            for (int i = 0; i < steps; i++) {
                if (i != 0)
                    p.println("," + phy.getState());
                else
                    p.println(phy.getState());

                if (!cmp.equal(phy.getState(), arrEx.getJSONObject(i)))
                    throw new StatesNotEqualException(phy.getState(), arrEx.getJSONObject(i), i);

                phy.advance();
            }//for
        } else runOut(steps,p);

        p.println("]");
        p.println("}");
        p.close();
    }


    private void runOut(int steps, PrintStream p){
        for (int i = 0; i < steps; i++) {
            if(i!=0)
                p.println(","+ phy.getState());
            else
                p.println(phy.getState());
            phy.advance();
        }//for
    }


    public void run(int n){
        for (int i = 0; i < n; i++) {
            phy.advance();
        }//for
    }


    public void reset(){
        phy.reset();
    }

    public void setDeltaTime(double dt){
        phy.setDeltaTime(dt);
    }

    public void addObserver(SimulatorObserver o){
        phy.addObserver(o);
    }

    public List<JSONObject> getForceLawsInfo(){
        return ff.getInfo();
    }

    public void setForceLaws(JSONObject info){
        ForceLaws nf = ff.createInstance(info);
        phy.setForceLaws(nf);
    }


}
