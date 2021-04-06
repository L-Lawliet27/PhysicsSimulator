package simulator.factories;

import org.json.JSONObject;
import simulator.control.EpsilonEqualStates;
import simulator.control.StateComparator;

public class EpsilonEqualStatesBuilder<T> extends Builder<StateComparator> {

    private double eps = 0.0;

    public EpsilonEqualStatesBuilder() {
        super("epseq");
    }


    @Override
    protected StateComparator instanceOf(JSONObject data) {

        if(data.has("eps"))
            eps = data.getDouble("eps");

        return new EpsilonEqualStates(eps);

    }

    @Override
    public JSONObject getBuilderInfo() {
        JSONObject o = new JSONObject();
        o.put("type", "epseq");

        JSONObject b = new JSONObject();
        b.put("eps", "0.0");

        o.put("data", b);

        o.put("desc", "Epsilon Equality Comparator");

        return o;
    }
}
