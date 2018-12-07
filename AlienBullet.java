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

public class AlienBullet extends Participant implements ShipDestroyer, AsteroidDestroyer
{
    /** The outline of the Bullet */
    private Shape outline;

    private boolean isShot;
    

    /** Game controller */
    private Controller controller;

    public AlienBullet (Controller controller, Alien alien, Ship ship)
    {
        double x=0;
        double y=0;
        double direction=0;
        new ParticipantCountdownTimer(this, BULLET_DURATION);
        try
        {
            x = alien.getX();
            y = alien.getY();

            if (alien.getScalar() == 2)
            {
                direction = (RANDOM.nextInt(6) * Math.PI / 3);
            }
            else // if(alien.getScalar()==1 && ship!=null)
            {
                if(ship.getX()<alien.getX())
                {
                  direction = Math.PI+(Math.atan((ship.getY() - alien.getY()) / (ship.getX() - alien.getX())));
                }
                else
                {
                  direction = Math.atan((ship.getY() - alien.getY()) / (ship.getX() - alien.getX()));
                }
            }
        }
        catch (NullPointerException e)
        {

        }

        this.controller = controller;
        setPosition(x, y);
        setRotation(direction - Math.PI / 2);
        setVelocity(BULLET_SPEED, direction);

        Path2D.Double poly = new Path2D.Double();
        poly.moveTo(0, 0);
        poly.lineTo(4, 0);
        poly.lineTo(4, 4);
        poly.lineTo(2, 6);
        poly.lineTo(0, 4);
        poly.closePath();
        outline = poly;
        

    }

    public void countdownComplete (Object payload)
    {
        // makes the bullet disappear after the given time Bullet_Duration

        Participant.expire(this);

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
        if (p instanceof AsteroidDestroyer || p instanceof ShipDestroyer)
        {
            // Expire the ship from the game
            Participant.expire(this);

        }
        // TODO Auto-generated method stub

    }

}
