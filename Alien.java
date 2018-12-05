package asteroids.participants;

import static asteroids.game.Constants.*;
import java.awt.Shape;
import java.awt.geom.*;
import asteroids.destroyers.AsteroidDestroyer;
import asteroids.destroyers.ShipDestroyer;
import asteroids.game.Controller;
import asteroids.game.Display;
import asteroids.game.Participant;
import asteroids.game.ParticipantCountdownTimer;

public class Alien extends Participant implements ShipDestroyer
{

    /** The alien shape */
    private Shape outline;

    /** The game controller */
    private Controller controller;
    
    private int horizontal;

    public Alien (double x, double y, int scalar)
    {
        horizontal = -1;
        setPosition(x, y);

        Path2D.Double poly = new Path2D.Double();
        poly.moveTo(0, 0);
        poly.lineTo(20 * scalar, 0 * scalar);
        poly.lineTo(15 * scalar, 5 * scalar);
        poly.lineTo(5 * scalar, 5 * scalar);
        poly.lineTo(0 * scalar, 0 * scalar);
        poly.lineTo(5 * scalar, -5 * scalar);
        poly.lineTo(15 * scalar, -5 * scalar);
        poly.lineTo(20 * scalar, 0 * scalar);

        outline = poly;
        
        new ParticipantCountdownTimer(this,"diagonal", 500);
        new ParticipantCountdownTimer(this,"switch", 10000);
    }

    public void countdownComplete(Object payload)
    {
        
        if(payload == "diagonal")
        {
        int a = RANDOM.nextInt(2);

        if (a == 0)
        {
            setVelocity(3 * horizontal, Math.PI * 2 / 3);
            new ParticipantCountdownTimer(this,"diagonal", 500);

        }
        else
        {
            setVelocity(3 * horizontal, Math.PI * 4 / 3);
            new ParticipantCountdownTimer(this,"diagonal", 500);

        }
        }
        if(payload == "switch")
        {
            horizontal = horizontal * -1;
            new ParticipantCountdownTimer(this,"switch", 4000);
        }

    }

    /**
     * Generate the outline of the Ship
     */
    @Override
    protected Shape getOutline ()
    {

        return outline;
    }

    /**
     * Expires ship and spawns debris in contact with an asteroidDestroyer participant
     */

    @Override
    public void collidedWith (Participant p)
    {
        if (p instanceof AsteroidDestroyer)
        {
            // Expire the asteroid
            Participant.expire(this);

            // Generate Debris
            controller.placeDebris(this.getX(), this.getY());

            // Inform the controller

        }

    }

}
