package simulator.factories;

import org.json.JSONObject;

import java.util.List;

public class BuilderBasedFactory<T> implements Factory<T> {


    @Override
    public T createInstance(JSONObject info) {
        return null;
    }

    @Override
    public List<JSONObject> getInfo() {
        return null;
    }
}
