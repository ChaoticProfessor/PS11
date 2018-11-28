package asteroids.participants;

import static asteroids.game.Constants.*;
import java.awt.Shape;
import java.awt.geom.*;
import asteroids.destroyers.AsteroidDestroyer;
import asteroids.destroyers.ShipDestroyer;
import asteroids.game.Controller;
import asteroids.game.Participant;


/**
 * represents Bullets
 */
public class Bullet extends Participant implements AsteroidDestroyer
{

    /** The outline of the Bullet */
    private Shape outline;

    /** Game controller */
    private Controller controller;

    /**
     * Constructs a Bullet at the specified coordinates that is pointed in the given direction.
     */
    public Bullet (int x, int y, double direction, Controller controller)
    {
        this.controller = controller;
        setPosition(x, y);
        setRotation(direction);

        Path2D.Double poly = new Path2D.Double();
        poly.moveTo(0, 0);
        poly.lineTo(4,0);
        poly.lineTo(4,4);
        poly.lineTo(2,6);
        poly.lineTo(0,4);
        poly.closePath();
        outline = poly;
        
   

    }
    @Override
    protected Shape getOutline ()
    {
        // TODO Auto-generated method stub
        return outline;
    }

    @Override
    public void collidedWith (Participant p)
    {
        // TODO Auto-generated method stub
        
    }

}
