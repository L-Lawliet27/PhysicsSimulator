package simulator.model;

import simulator.misc.Vector2D;

import java.util.List;

public class NewtonUniversalGravitation implements ForceLaws{

    protected double g;

    public NewtonUniversalGravitation(double g){
        this.g = g;
    }

    @Override
    public void apply(List<Body> bs) {
        for (int i = 0; i < bs.size(); i++) {
            for (Body b : bs) {
                if (!b.equals(bs.get(i))) {
                    bs.get(i).addForce(forceApplied(bs.get(i),b));
                }//if
            }//foreach
        }//for
    }

    private Vector2D forceApplied(Body b2, Body b1){
        double f = 0.0;
        double dir = directionOf(b2,b1);

        if(dir > 0){
            f = fResult(b2, b1, dir);
        }

        return vectorDirection(b2,b1).scale(f);
    }

    private double directionOf(Body b2, Body b1){
        return Math.pow(b2.position.distanceTo(b1.getPosition()),2);
    }

    private double fResult(Body b2, Body b1, double dir){
        return g * ((b2.mass * b1.mass)/dir);
    }

    private Vector2D vectorDirection(Body b2, Body b1){
        return b2.position.direction().minus(b1.position.direction());
    }
}
