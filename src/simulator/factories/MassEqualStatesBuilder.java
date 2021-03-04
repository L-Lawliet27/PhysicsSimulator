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
        return null;
    }
}
