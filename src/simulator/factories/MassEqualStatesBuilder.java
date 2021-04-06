package simulator.factories;

import org.json.JSONObject;
import simulator.control.MassEqualStates;
import simulator.control.StateComparator;

public class MassEqualStatesBuilder<T> extends Builder<StateComparator> {

    public MassEqualStatesBuilder() {
        super("masseq");
    }

    @Override
    protected StateComparator instanceOf(JSONObject data) {

        if(!data.isEmpty()) throw new IllegalArgumentException("Data is not Empty");

        return new MassEqualStates();
    }

    @Override
    public JSONObject getBuilderInfo() {
        JSONObject o = new JSONObject();
        o.put("type", "masseq");

        JSONObject b = new JSONObject();

        o.put("data", b);

        o.put("desc", "Mass Equality Comparator");

        return o;
    }
}
