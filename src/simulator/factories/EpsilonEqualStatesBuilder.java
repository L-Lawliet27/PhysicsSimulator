package simulator.factories;

import org.json.JSONObject;
import simulator.control.EpsilonEqualStates;
import simulator.control.StateComparator;

public class EpsilonEqualStatesBuilder<T> extends Builder<StateComparator> {

    private double eps = 0.0;
    private final String desc="Epsilon Equality Comparator";
    private final String typeDesc="epseq";

    public EpsilonEqualStatesBuilder() {
        super("epseq");
    }


    @Override
    protected StateComparator instanceOf(JSONObject data) {

        if(data.has("eps"))
            eps = data.getDouble("eps");

        return new EpsilonEqualStates(eps);

    }


    public JSONObject getBuilderInfo() {
        JSONObject b = new JSONObject();
        b.put("eps", "0.0");

        return getBuilderInfo(typeDesc,b,desc);
    }
}
