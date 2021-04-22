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

        for (Body o : bs) {
            for (Body b : bs) {
                if (!b.equals(o.getId()))
                    o.addForce(forceApplied(b, o));
            }//foreach
        }//foreach

    }

    private Vector2D forceApplied(Body b2, Body b1){
        double f = 0.0;
        double dis = operation(b2,b1);

        if(dis > 0){
            f = fResult(b2, b1, dis);
        }
        Vector2D vDir = vectorDirection(b2,b1).direction();
        return vDir.scale(f);
    }

    private double operation(Body b2, Body b1){
        Vector2D posB2 = b2.getPosition();
        Vector2D posB1 = b1.getPosition();
        double distance = posB2.distanceTo(posB1);
        return Math.pow(distance,2);
    }

    private double fResult(Body b2, Body b1, double dis){
        double mB2 = b2.getMass();
        double mB1 = b1.getMass();
        return g * ( (mB2 * mB1) / dis );
    }

    private Vector2D vectorDirection(Body b2, Body b1){
        Vector2D pB2 = b2.getPosition();
        Vector2D pB1 = b1.getPosition();
        return pB2.minus(pB1);
    }

    @Override
    public String toString(){
        return "Newton’s Universal Gravitation with G="+g;
    }

}
