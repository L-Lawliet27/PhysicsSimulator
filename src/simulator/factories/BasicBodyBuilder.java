package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.Vector2D;
import simulator.model.Body;

public class BasicBodyBuilder<T> extends Builder<Body> {

    public BasicBodyBuilder() {
        super("basic");
    }

    @Override
    protected Body instanceOf(JSONObject data) {
        String id;
        Vector2D position;
        Vector2D velocity;
        JSONArray p;
        JSONArray v;
        double mass;
        try{
            id = data.getString("id");

            p = data.getJSONArray("p");
            v = data.getJSONArray("v");

            if(arrayCheck(p,v))
                throw new IllegalArgumentException("Incorrect Number of vector elements");

            position = new Vector2D(p.getDouble(0),p.getDouble(1));
            velocity = new Vector2D(v.getDouble(0),v.getDouble(1));

            mass = data.getDouble("m");

        }catch (NullPointerException | NumberFormatException | ClassCastException e){
            return null;
        }

        return new Body(id,velocity,position,mass);
    }

    private boolean arrayCheck(JSONArray j1, JSONArray j2){
        return j1.length() < 2 && j2.length() < 2;
    }

    @Override
    public JSONObject getBuilderInfo() {
        //TODO
        return null;
    }
}