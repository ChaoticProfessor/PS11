package asteroids.participants;

import static asteroids.game.Constants.*;
import java.awt.Shape;
import java.awt.geom.*;
import asteroids.destroyers.AsteroidDestroyer;
import asteroids.destroyers.ShipDestroyer;
import asteroids.game.Controller;
import asteroids.game.Participant;
import asteroids.game.ParticipantCountdownTimer;


/**
 * represents Bullets
 */
public class Bullet extends Participant implements AsteroidDestroyer
{

    /** The outline of the Bullet */
    private Shape outline;
    
    private boolean isShot;

    /** Game controller */
    private Controller controller;

    /**
     * Constructs a Bullet at the specified coordinates that is pointed in the given direction.
     */
    public Bullet (Controller controller, Ship ship)
    {
        int x=(int)ship.getX();
        int y=(int)ship.getY();
        double direction=ship.getRotation() - Math.PI/2;
        
        this.controller = controller;
        setPosition(x, y);
        setRotation(direction);
        setSpeed(BULLET_SPEED);

        Path2D.Double poly = new Path2D.Double();
        poly.moveTo(0, 0);
        poly.lineTo(4,0);
        poly.lineTo(4,4);
        poly.lineTo(2,6);
        poly.lineTo(0,4);
        poly.closePath();
        outline = poly;

    }
    public void die()
    {
        Participant.expire(this);
    }
    
    
    public void countdownComplete (Object payload)
    {
        // makes the bullet disappear after the given time Bullet_Duration
        if (payload.equals("die"))
        {
            new ParticipantCountdownTimer(this, "die",BULLET_DURATION );
        }
    }
    
    @Override
    public void move ()
    {
        super.move();
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
