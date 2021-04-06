package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.PhysicsSimulator;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class Controller {

    private PhysicsSimulator phy;
    private Factory<Body> fb;


    public Controller(PhysicsSimulator physicsSimulator, Factory<Body> bodyFactory){
        phy=physicsSimulator;
        fb=bodyFactory;
    }

    public void loadBodies(InputStream in){
        JSONObject jInput = new JSONObject(new JSONTokener(in));
        JSONArray jar = jInput.getJSONArray("bodies");

        for (int i = 0; i < jar.length(); i++) {
            Body b = fb.createInstance(jar.getJSONObject(i));
            phy.addBody(b);
        }

    }


    public void run(int steps, OutputStream out, InputStream expOut, StateComparator cmp) throws StatesNotEqualException{

        PrintStream p = new PrintStream(out);
        p.println("{");
        p.println("\"states\": [");

        JSONObject jEx = new JSONObject(new JSONTokener(expOut));
        JSONArray arrEx = jEx.getJSONArray("states");

        for (int i = 0; i < steps; i++) {
            p.println(phy.getState());

            if(!cmp.equal(phy.getState(), arrEx.getJSONObject(i)))
                throw new StatesNotEqualException(phy.getState(), arrEx.getJSONObject(i), i);

            phy.advance();
        }//for

        p.println("]");
        p.println("}");
        p.close();
    }

    public void run(int steps, OutputStream out){

        PrintStream p = new PrintStream(out);
        p.println("{");
        p.println("\"states\": [");

        for (int i = 0; i < steps; i++) {
            p.println(phy.getState());
            phy.advance();
        }//for

        p.println("]");
        p.println("}");
        p.close();
    }

}
