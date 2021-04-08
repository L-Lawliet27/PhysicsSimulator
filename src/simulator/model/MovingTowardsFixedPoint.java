package simulator.model;

import simulator.misc.Vector2D;

import java.util.List;

public class MovingTowardsFixedPoint implements ForceLaws{

    protected Vector2D c;
    protected double g;

    public MovingTowardsFixedPoint(Vector2D c, double g){
        this.c=c;
        this.g=g;
    }

    @Override
    public void apply(List<Body> bs) {
        for (Body b : bs) {
            b.addForce(forceApplied(b));
        }
    }

    private Vector2D forceApplied(Body b){
        return vectorDirection(c,b).direction().scale(b.getMass()/g);
    }

    private Vector2D vectorDirection(Vector2D c, Body b){
        return c.minus(b.getPosition());
    }



}
