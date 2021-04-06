package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder<T> extends Builder<ForceLaws> {

    private Vector2D c = new Vector2D();
    private double g = 9.81;

    public MovingTowardsFixedPointBuilder() {
        super("mtcp");
    }

    @Override
    protected ForceLaws instanceOf(JSONObject data) {

        if(data.has("c")) {
            JSONArray ar = data.getJSONArray("c");
            try{
                double x = ar.getDouble(0);
                double y = ar.getDouble(1);
                c = c.plus(new Vector2D(x, y));
            } catch (NumberFormatException e){
                System.out.println("Not a Double");
            }
        }

        if(data.has("g"))
            g = data.getDouble("g");

        return new MovingTowardsFixedPoint(c,g);
    }


    @Override
    public JSONObject getBuilderInfo() {
        JSONObject o = new JSONObject();
        o.put("type", "mtcp");

        JSONObject b = new JSONObject();
        b.put("c", "[0,0]");
        b.put("g", "9.89");

        o.put("data", b);

        o.put("desc", "Force that moves Body towards a Point");

        return o;
    }
}
