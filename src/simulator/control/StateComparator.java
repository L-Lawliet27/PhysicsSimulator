package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

public interface StateComparator {
	boolean equal(JSONObject s1, JSONObject s2);

	static boolean tEqual(JSONObject s1, JSONObject s2){ return s1.getDouble("time") == s2.getDouble("time"); }

	static boolean idEqual(int i, JSONArray ja1, JSONArray ja2){ return ja1.getJSONObject(i).get("id").equals(ja2.getJSONObject(i).get("id")); }

	static double cord(JSONArray j, int i, String s, int c){ return j.getJSONObject(i).getJSONArray(s).getDouble(c); }
}
