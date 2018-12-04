package asteroids.participants;

import static asteroids.game.Constants.*;
import java.awt.Shape;
import java.awt.geom.*;
import asteroids.destroyers.AsteroidDestroyer;
import asteroids.destroyers.ShipDestroyer;
import asteroids.game.Controller;
import asteroids.game.Display;
import asteroids.game.Participant;

public class Alien extends Participant implements ShipDestroyer
{
    
    /**The alien shape*/
    private Shape outline;

    /** The game controller */
    private Controller controller;
    
    
    
    public Alien(double x,double y, int scalar)
    {
        
        
        setPosition(x,y);
        
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
            
            //Generate Debris
            controller.placeDebris(this.getX(), this.getY());

            // Inform the controller
            

        }
        
    }
    
    
    
    
    
    
    
}
