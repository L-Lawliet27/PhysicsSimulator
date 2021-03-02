package simulator.model;

import simulator.misc.Vector2D;

public class MassLosingBody extends Body {

    protected double lossFactor;
    protected double lossFrequency;
    private double counter = 0.0;

    public MassLosingBody(String id, Vector2D v, Vector2D pos, double m, double fac, double fre) {
        super(id, v, pos, m);
        lossFactor = fac;
        lossFrequency = fre;
    }


    void move(double t){
        super.move(t);

        if (counter >= lossFrequency){
            reduceMass();
            counter = 0.0;

        } else counter += t;

    }


    private void reduceMass(){ mass = mass * (1-lossFactor); }

}
