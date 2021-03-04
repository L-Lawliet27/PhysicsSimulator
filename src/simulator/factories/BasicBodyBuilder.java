package simulator.factories;

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
        double mass;
        try{
            id = data.getString("id");
            position = (Vector2D) data.get("p");
            velocity = (Vector2D) data.get("v");
            mass = data.getDouble("m");

        }catch (NullPointerException | NumberFormatException | ClassCastException e){
            return null;
        }

        return new Body(id,velocity,position,mass);
    }

    @Override
    public JSONObject getBuilderInfo() {
        //TODO
        return null;
    }
}
