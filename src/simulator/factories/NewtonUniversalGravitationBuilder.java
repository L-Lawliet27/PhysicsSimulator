package simulator.factories;

import org.json.JSONObject;
import simulator.model.ForceLaws;

public class NewtonUniversalGravitationBuilder<T> extends Builder<ForceLaws> {

    public NewtonUniversalGravitationBuilder() {
        super("nlug");
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
