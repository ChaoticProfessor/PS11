package asteroids.participants;

import static asteroids.game.Constants.*;
import asteroids.game.Participant;
import java.awt.Shape;
import java.awt.geom.*;

public class Debris extends Participant
{

    /** The outline of the asteroid */
    private Shape outline;

    public Debris (double x, double y)
    {

        
        setPosition(x, y);
        setVelocity(RANDOM.nextInt(5), RANDOM.nextDouble() * 2 * Math.PI);
        setRotation(2 * Math.PI * RANDOM.nextDouble());
        generateDebrisOutline(x, y);
        

        
    }

    public void generateDebrisOutline (double x, double y)
    {
        Path2D.Double poly = new Path2D.Double();

        poly.moveTo(x, y);
        poly.lineTo(x + RANDOM.nextInt(15), y + RANDOM.nextInt(10));
        poly.closePath();

        outline = poly;
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
