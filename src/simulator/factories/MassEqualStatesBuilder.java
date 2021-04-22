package simulator.factories;

import org.json.JSONObject;
import simulator.control.MassEqualStates;
import simulator.control.StateComparator;

public class MassEqualStatesBuilder<T> extends Builder<StateComparator> {

    private final String desc="Mass Equality Comparator";
    private final String typeDesc="masseq";

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
        JSONObject b = new JSONObject();
        return getBuilderInfo(typeDesc,b,desc);
    }
}
