package simulator.factories;

import org.json.JSONObject;
import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MassLosingBody;

public class MassLosingBodyBuilder<T> extends Builder<Body> {

    public MassLosingBodyBuilder() {
        super("mlb");
    }


    @Override
    protected Body instanceOf(JSONObject data) {
        String id;
        Vector2D position;
        Vector2D velocity;
        double mass;
        double lossFrequency;
        double lossFactor;

        try{
            id = data.getString("id");
            position = (Vector2D) data.get("p");
            velocity = (Vector2D) data.get("v");
            mass = data.getDouble("m");
            lossFrequency = data.getDouble("freq");
            lossFactor = data.getDouble("factor");

        }catch (NullPointerException | NumberFormatException | ClassCastException e){
            return null;
        }

        return new MassLosingBody(id,velocity,position,mass,lossFactor,lossFrequency);
    }


    @Override
    public JSONObject getBuilderInfo() {
        //TODO
        return null;
    }
}
