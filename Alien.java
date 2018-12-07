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

public class Alien extends Participant implements ShipDestroyer , AsteroidDestroyer
{

    /** The alien shape */
    private Shape outline;

    /** The game controller */
    private Controller controller;
    
    private int horizontal;
    
    private int Scalar;
    
    

    public Alien (double x, double y, int scalar,Controller controller)
    {
        this.controller= controller;
        Scalar=scalar;
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
        new ParticipantCountdownTimer(this,"shoot",(int)(Math.random()*3000)+3000);
    }
    
    public double getSpeed()
    {
        if(getScalar()==1)
        {
            return 5* horizontal;
        }
        else
        {
            return 3*horizontal;
        }
    }
    
    public int getScalar()
    {
        return Scalar;
    }

    public void countdownComplete(Object payload)
    {
        if (payload=="shoot")
        {
            controller.alienShoot();
            new ParticipantCountdownTimer(this,"shoot",(int)(Math.random()*3000)+3000);
        }
        
        if(payload == "diagonal")
        {
        int a = RANDOM.nextInt(2);

        if (a == 0)
        {
            setVelocity(getSpeed(), Math.PI * 2 / 3);
            new ParticipantCountdownTimer(this,"diagonal", 500);

        }
        else
        {
            setVelocity(getSpeed(), Math.PI * 4 / 3);
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
        if (p instanceof Bullet||p instanceof Ship ||p instanceof Asteroid)
        {
            // Expire the asteroid
            Participant.expire(this);

            // Generate Debris
            

            // Inform the controller
            controller.alienDestroyed();

        }

    }

}
