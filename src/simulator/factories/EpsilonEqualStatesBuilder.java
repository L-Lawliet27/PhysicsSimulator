package simulator.factories;

import org.json.JSONObject;
import simulator.control.StateComparator;

public class EpsilonEqualStatesBuilder<T> extends Builder<StateComparator> {

    public EpsilonEqualStatesBuilder() {
        super("epseq");
    }


    @Override
    protected StateComparator instanceOf(JSONObject data) {
        return null;
    }

    @Override
    public JSONObject getBuilderInfo() {
        return null;
    }
}
