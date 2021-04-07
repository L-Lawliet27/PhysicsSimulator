package simulator.factories;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BuilderBasedFactory<T> implements Factory<T> {

    private List<Builder<T>> builders;
    private List<JSONObject> jList;

    public BuilderBasedFactory(List<Builder<T>> builders){
        this.builders = builders;
        jList = getBI(this.builders);
    }

    private List<JSONObject> getBI(List<Builder<T>> b) {
        List<JSONObject> l = new ArrayList<>();
        for (Builder<T> bu : b) {
            l.add(bu.getBuilderInfo());
        }
        return l;
    }

    @Override
    public T createInstance(JSONObject info) {
        T o;

        for (Builder<T> bu : builders) {
            o = bu.createInstance(info);
            if(o != null)
                return o;
        }

        throw new IllegalArgumentException("Failed to Build");
    }

    @Override
    public List<JSONObject> getInfo() {
        return jList;
    }
}
