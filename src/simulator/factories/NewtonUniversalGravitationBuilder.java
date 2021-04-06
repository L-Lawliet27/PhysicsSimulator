package simulator.factories;

import org.json.JSONObject;
import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder<T> extends Builder<ForceLaws> {

    private double g = 6.67e10-11;

    public NewtonUniversalGravitationBuilder() {
        super("nlug");
    }


    @Override
    protected ForceLaws instanceOf(JSONObject data) {
        if(data.has("G"))
            g = data.getDouble("G");

        return new NewtonUniversalGravitation(g);
    }

    @Override
    public JSONObject getBuilderInfo() {
        JSONObject o = new JSONObject();
        o.put("type", "nlug");

        JSONObject b = new JSONObject();
        b.put("G", "6.67e10-11");

        o.put("data", b);

        o.put("desc", "Newton’s law of universal gravitation");

        return o;
    }
}
