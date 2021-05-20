package simulator.model;

import org.json.JSONObject;
import simulator.misc.Vector2D;

public class Body {

    protected String id;
    protected Vector2D velocity;
    protected Vector2D force;
    protected Vector2D position;
    protected double mass;

    private Vector2D accel;

    public Body(String id, Vector2D v, Vector2D pos, double m){
        this.id=id;
        velocity=v;
        position=pos;
        mass=m;
        force = new Vector2D();
    }

    public String getId() {
        return id;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public Vector2D getForce() {
        return force;
    }

    public Vector2D getPosition() {
        return position;
    }

    public double getMass() {
        return mass;
    }

    void addForce(Vector2D f){
        force = force.plus(f);
    }

    void resetForce(){
        force = new Vector2D();
    }

    void move(double t){
        accel = secondNewton();
        position = changePos(t);
        velocity = changeVel(t);
    }

    private Vector2D secondNewton(){
        if(mass!=0.0){
            return force.scale(1.0/mass);
        }
        return new Vector2D();
    }

    private Vector2D changePos(double t){
        Vector2D left = velocity.scale(t);
        Vector2D right = accel.scale(Math.pow(t,2)/2.0);
        return position.plus(left.plus(right));
    }

    private Vector2D changeVel(double t){
        return velocity.plus(accel.scale(t));
    }


    public JSONObject getState(){
        JSONObject b = new JSONObject();
        b.put("id", id);
        b.put("m", mass);
        b.put("p", position.asJSONArray());
        b.put("v", velocity.asJSONArray());
        b.put("f", force.asJSONArray());
        return b;
    }

    public String toString(){
        return getState().toString();
    }


    public boolean equals(Object b){
        return this.id.equals(b.toString());
    }

}
