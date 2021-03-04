package simulator.factories;

import org.json.JSONObject;
import simulator.control.StateComparator;

public class MassEqualStatesBuilder<T> extends Builder<StateComparator> {

    public MassEqualStatesBuilder() {
        super("masseq");
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
