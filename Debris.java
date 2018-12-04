package asteroids.participants;

import static asteroids.game.Constants.*;
import asteroids.game.Participant;
import asteroids.game.ParticipantCountdownTimer;
import java.awt.Shape;
import java.awt.geom.*;

public class Debris extends Participant
{

    /** The outline of the asteroid */
    private Shape outline;

    public Debris (double x, double y)
    {
       
        
        generateDebrisOutline();
        setPosition(x, y);
        setVelocity(RANDOM.nextInt(2), RANDOM.nextDouble() * 2 * Math.PI);
        new ParticipantCountdownTimer(this, 2500);
    
    }

    public void generateDebrisOutline ()
    {
        Path2D.Double poly = new Path2D.Double();

        poly.moveTo(0, 0);
        poly.lineTo(RANDOM.nextInt(10),RANDOM.nextInt(10));
        poly.closePath();

        outline = poly;
    }
    /**
     * Expires the debris after 500 miliseconds
     */
    
    public void countdownComplete (Object payload)
    {

            Participant.expire(this);;
        
    }

    @Override
    protected Shape getOutline ()
    {
        return outline;
    }

    @Override
    public void collidedWith (Participant p)
    {

    }

}
