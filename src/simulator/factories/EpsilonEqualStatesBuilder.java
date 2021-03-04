package simulator.factories;

import org.json.JSONObject;
import simulator.control.EpsilonEqualStates;
import simulator.control.StateComparator;

public class EpsilonEqualStatesBuilder<T> extends Builder<StateComparator> {

    public EpsilonEqualStatesBuilder() {
        super("epseq");
    }


    @Override
    protected StateComparator instanceOf(JSONObject data) {
        double eps;

        if(!data.has("eps")) throw new IllegalArgumentException("No Epsilon");

        try{
            eps = data.getDouble("eps");
        }catch (NullPointerException | NumberFormatException e){
            return null;
        }

        return new EpsilonEqualStates(eps);
    }

    @Override
    public JSONObject getBuilderInfo() {
        return null;
    }
}
