package simulator.factories;

import org.json.JSONObject;


public abstract class Builder<T> {

    protected String type;

    protected JSONObject o;

    public Builder(String type){
        this.type = typeCheck(type);
    }

    private String typeCheck(String t){
        if(t == null) throw new IllegalArgumentException("Invalid type");
        return t;
    }

    public T createInstance(JSONObject info){
        T o = null;

        if(info.has("type") && info.get("type").equals(type)){

            if(info.has("data"))
                o = instanceOf(info.getJSONObject("data"));
            else return null;

        }

        return o;
    }

    protected abstract T instanceOf(JSONObject data);


    protected JSONObject getBuilderInfo(String typeDesc, JSONObject data, String desc){
        o = new JSONObject();
        o.put("type", typeDesc);
        o.put("data", data);
        o.put("desc", desc);
        return o;
    }

    public abstract JSONObject getBuilderInfo();

}
