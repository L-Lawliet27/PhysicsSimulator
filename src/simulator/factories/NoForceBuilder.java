package simulator.factories;

import org.json.JSONObject;
import simulator.model.ForceLaws;

public class NoForceBuilder<T> extends Builder<ForceLaws> {

    public NoForceBuilder() {
        super("ng");
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
