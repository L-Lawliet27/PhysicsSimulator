package simulator.factories;

import org.json.JSONObject;
import simulator.model.ForceLaws;

public class MovingTowardsFixedPoint<T> extends Builder<ForceLaws> {

    public MovingTowardsFixedPoint(String type) {
        super("mtcp");
    }

    @Override
    protected ForceLaws instanceOf(JSONObject data) {
        return null;
    }


    @Override
    public JSONObject getBuilderInfo() {
        return null;
    }
}
