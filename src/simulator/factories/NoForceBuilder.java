package simulator.factories;

import org.json.JSONObject;
import simulator.model.ForceLaws;
import simulator.model.NoForce;

public class NoForceBuilder<T> extends Builder<ForceLaws> {

    public NoForceBuilder() {
        super("ng");
    }

    @Override
    protected ForceLaws instanceOf(JSONObject data) {
        if(!data.isEmpty()) throw new IllegalArgumentException("Data is not Empty");
        return new NoForce();
    }

    @Override
    public JSONObject getBuilderInfo() {
        //todo
        return null;
    }
}
