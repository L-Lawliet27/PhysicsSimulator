package simulator.control;

import org.json.JSONObject;

public class StatesNotEqualException extends Exception{

    public StatesNotEqualException(JSONObject obj1, JSONObject obj2, int execStep) throws StatesNotEqualException {
        String o1 = obj1.toString() + "\n";
        String o2 = obj2.toString() + "\n";
        String m = "States Not Equal - Execution Step: " + execStep + "\n" + o1 + o2;
       throw new StatesNotEqualException(m);
    }

    private StatesNotEqualException(String message){
        super(message);
    }

}
