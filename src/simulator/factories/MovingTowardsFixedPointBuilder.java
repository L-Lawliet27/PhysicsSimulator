package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder<T> extends Builder<ForceLaws> {

    private Vector2D c = new Vector2D();
    private double g = 9.81;

    private final String desc="Force that moves Body towards a Point";
    private final String typeDesc="mtfp";

    public MovingTowardsFixedPointBuilder() {
        super("mtfp");
    }

    @Override
    protected ForceLaws instanceOf(JSONObject data) {

        if(data.has("c")) {
            JSONArray ar = data.getJSONArray("c");

            double x = ar.getDouble(0);
            double y = ar.getDouble(1);
            c = c.plus(new Vector2D(x, y));
        }

        if(data.has("g"))
            g = data.getDouble("g");

        return new MovingTowardsFixedPoint(c,g);
    }


    @Override
    public JSONObject getBuilderInfo() {
        JSONObject b = new JSONObject();
        b.put("c", "[0,0]");
        b.put("g", "9.89");

        return getBuilderInfo(typeDesc,b,desc);
    }
}
