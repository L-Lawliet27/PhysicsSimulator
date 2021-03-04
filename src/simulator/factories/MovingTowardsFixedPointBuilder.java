package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder<T> extends Builder<ForceLaws> {

    public MovingTowardsFixedPointBuilder(String type) {
        super("mtcp");
    }

    @Override
    protected ForceLaws instanceOf(JSONObject data) {
        JSONArray ar;
        Vector2D c;
        double g;

        try{
            ar = data.getJSONArray("c");
            c = new Vector2D(ar.getDouble(0), ar.getDouble(1));
            g = data.getDouble("g");

        }catch (NullPointerException | NumberFormatException e) {
            return null;
        }

        return new MovingTowardsFixedPoint(c,g);
    }


    @Override
    public JSONObject getBuilderInfo() {
        return null;
    }
}
