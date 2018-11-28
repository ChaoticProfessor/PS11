package asteroids.participants;

import static asteroids.game.Constants.*;
import asteroids.game.Controller;
import asteroids.game.Participant;
import java.awt.Shape;
import java.awt.geom.*;

public abstract class Debris extends Participant
{
    
    /** The outline of the asteroid */
    private Shape outline;
    
    
    public Debris(int x, int y)
    {
       int randDirection;
       int randAmount;
       
       randAmount = RANDOM.nextInt();
       randDirection = RANDOM.nextInt();

       
    }
    
    public void generate()
    {
        Path2D.Double poly = new Path2D.Double();
        
      
        poly.moveTo(0, -30);
        poly.lineTo(28, -15);
        poly.lineTo(20, 20);
        poly.lineTo(4, 8);
        poly.lineTo(-1, 30);
        poly.lineTo(-12, 15);
        poly.lineTo(-5, 2);
        poly.lineTo(-25, 7);
        poly.lineTo(-10, -25);
        poly.closePath();
        
        
        
        outline = poly;
    }
    
    
    @Override
    protected Shape getOutline ()
    {
        return outline;
    }
    

}
