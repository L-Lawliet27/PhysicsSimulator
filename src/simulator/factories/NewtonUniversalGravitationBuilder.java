package simulator.factories;

import org.json.JSONObject;
import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder<T> extends Builder<ForceLaws> {

    public NewtonUniversalGravitationBuilder() {
        super("nlug");
    }


    @Override
    protected ForceLaws instanceOf(JSONObject data) {
        double g;

        try {
            g = data.getDouble("G");
        }catch (NumberFormatException | NullPointerException e){
            return null;
        }

        return new NewtonUniversalGravitation(g);
    }

    @Override
    public JSONObject getBuilderInfo() {
        return null;
    }
}
