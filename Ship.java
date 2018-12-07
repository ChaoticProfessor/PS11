package asteroids.participants;

import static asteroids.game.Constants.*;
import java.awt.Shape;
import java.awt.geom.*;
import asteroids.destroyers.*;
import asteroids.game.Controller;
import asteroids.game.Participant;
import asteroids.game.ParticipantCountdownTimer;
import asteroids.game.Sounds;

/**
 * Represents ships
 */
public class Ship extends Participant implements AsteroidDestroyer
{
    /** The outline of the ship */
    private Shape outline;

    /** Game controller */
    private Controller controller;
    
    private boolean isTurningRight;
    
    private boolean isTurningLeft;
    
    private boolean isAccellerating;
    
    /**Allows for the fire to alternate*/
    private boolean on;
    
    private int beatTime;
    
    private Sounds sound;
    
    
    
    
    
    

    /**
     * Constructs a ship at the specified coordinates that is pointed in the given direction.
     */
    public Ship (int x, int y, double direction, Controller controller)
    {
        this.controller = controller;
        setPosition(x, y);
        setRotation(direction);
        
        beatTime = 2000;
        sound = new Sounds();

        Path2D.Double poly = new Path2D.Double();
        poly.moveTo(21, 0);
        poly.lineTo(-21, 12);
        poly.lineTo(-14, 10);
        poly.lineTo(-14, -10);
        poly.lineTo(-21, -12);
        poly.lineTo(21, 0);
        outline = poly; 
        
        
        on = true;

        // Schedule the fire every .05 seconds
        new ParticipantCountdownTimer(this, 50);

        // Schedule an acceleration in two seconds
        //new ParticipantCountdownTimer(this, "move", 2000);
    }

    /**
     * Returns the x-coordinate of the point on the screen where the ship's nose is located.
     */
    public double getXNose ()
    {
        Point2D.Double point = new Point2D.Double(20, 0);
        transformPoint(point);
        return point.getX();
    }

    /**
     * Returns the x-coordinate of the point on the screen where the ship's nose is located.
     */
    public double getYNose ()
    {
        Point2D.Double point = new Point2D.Double(20, 0);
        transformPoint(point);
        return point.getY();
    }
    
    public boolean getIsTurningRight()
    {
        return isTurningRight;
    }
    
    public void setIsTurningRight(boolean b)
    {
        isTurningRight = b;
    }
    
    public boolean getIsTurningLeft()
    {
        return isTurningLeft;
    }
    
    public void setIsTurningLeft(boolean b)
    {
        isTurningLeft = b;
    }
    
    public boolean getIsAccellerating()
    {
        return isAccellerating;
    }
    
    public void setIsAccellerating(boolean b)
    {
        isAccellerating = b;
    }

    @Override
    protected Shape getOutline ()
    {
        return outline;
    }

    /**
     * Customizes the base move method by imposing friction
     */
    @Override
    public void move ()
    {
        applyFriction(SHIP_FRICTION);
        super.move();
    }

    /**
     * Turns right by Pi/16 radians
     */
    public void turnRight ()
    {
        
        rotate(Math.PI / 16);
        
    }

    
    /**
     * Turns left by Pi/16 radians
     */
    public void turnLeft ()
    {
        rotate(-Math.PI / 16);
    }

    /**
     * Accelerates by SHIP_ACCELERATION
     */
    public void accelerate ()
    {
        accelerate(SHIP_ACCELERATION);
    }
    

    /**
     * When a Ship collides with a ShipDestroyer, it expires
     */
    @Override
    public void collidedWith (Participant p)
    {
        if (p instanceof ShipDestroyer)
        {
            // Expire the ship from the game
            Participant.expire(this);

                        
            //Generate Debris
            
            
            
            // Tell the controller the ship was destroyed
            controller.shipDestroyed();
        }
    }
    
  
   /** Places the fire accordingly
     * 
     */
    public void countdownComplete(Object payload)
    {
        if(payload.equals("fire") && on && getIsAccellerating())
        {
            Path2D.Double poly = new Path2D.Double();
            poly.moveTo(21, 0);
            poly.lineTo(-21, 12);
            poly.lineTo(-14, 10);
            poly.lineTo(-14, -10);
            poly.lineTo(-21, -12);
            poly.lineTo(21, 0);
            poly.moveTo(-14,10);
            poly.lineTo(-28, 0);
            poly.lineTo(-14, -10);
            poly.closePath();
            outline = poly; 
            on = false;
            new ParticipantCountdownTimer(this, "fire", 50);
        }
        else if(payload.equals("fire") && getIsAccellerating() && !on)
        {
            Path2D.Double poly = new Path2D.Double();
            poly.moveTo(21, 0);
            poly.lineTo(-21, 12);
            poly.lineTo(-14, 10);
            poly.lineTo(-14, -10);
            poly.lineTo(-21, -12);
            poly.lineTo(21, 0);
            outline = poly; 
            on = true;
            new ParticipantCountdownTimer(this, "fire", 50);
        }
        
        else if(payload.equals("beat1"))
        {
          sound.backgroundNoise("beat1");
          if(beatTime > 50)
          {
              beatTime = beatTime - 50;
          }
          
          new ParticipantCountdownTimer(this, "beat2", beatTime);
            
            
        }
        else if(payload.equals("beat2"))
        {
          sound.backgroundNoise("beat2");
          if(beatTime > 50)
          {
              beatTime = beatTime - 50;
          }
          new ParticipantCountdownTimer(this, "beat1", beatTime);
            
            
        }
        
    }
    
  
}
