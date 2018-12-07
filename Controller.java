package asteroids.game;

import static asteroids.game.Constants.*;
import java.awt.event.*;
import java.util.Iterator;
import javax.swing.*;
import asteroids.participants.Alien;
import asteroids.participants.AlienBullet;
import asteroids.participants.Asteroid;
import asteroids.participants.Debris;
import asteroids.participants.Ship;
import asteroids.participants.Bullet;

/**
 * Controls a game of Asteroids.
 */
public class Controller implements KeyListener, ActionListener
{
    /** The state of all the Participants */
    private ParticipantState pstate;

    /** The ship (if one is active) or null (otherwise) */
    private Ship ship;
    
    /** The alien ship if active or null otherwise*/
    private Alien alien;
    
    private Alien alien1;

    private Bullet bullet;
    
    private AlienBullet alienBullet;

    /** When this timer goes off, it is time to refresh the animation */
    private Timer refreshTimer;

    /**
     * The time at which a transition to a new stage of the game should be made. A transition is scheduled a few seconds
     * in the future to give the user time to see what has happened before doing something like going to a new level or
     * resetting the current level.
     */
    private long transitionTime;

    /** Number of lives left */
    private int lives;
    
    /**Level number */
    private int level;
    
    /**Score Value*/
    private int score;
    
    /**Sounds*/
    private Sounds sounds;
    
    /** number of bullets in the game */
    public int numBullets;

    /** The game display */
    private Display display;

    /**
     * Constructs a controller to coordinate the game and screen
     */
    public Controller ()
    {
        // Initialize the ParticipantState
        pstate = new ParticipantState();

        // Set up the refresh timer.
        refreshTimer = new Timer(FRAME_INTERVAL, this);

        // Clear the transitionTime
        transitionTime = Long.MAX_VALUE;

        // Record the display object
        display = new Display(this);
        
        //Establish the sounds
        sounds = new Sounds();

        // Bring up the splash screen and start the refresh timer
        splashScreen();
        display.setVisible(true);
        refreshTimer.start();
    }

    /**
     * Returns the ship, or null if there isn't one
     */
    public Ship getShip ()
    {
        return ship;
    }
    
        /**
     * Returns the number of lives
     */
    
    public int getLives ()
    {
        return lives;
    }
    
    /**
     * Returns what level the player is on
     */
    
    public int getLevel()
    {
        return level;
    }
    
    /**
     * Returns the current Score
     */
    
    public int getScore()
    {
        return score;
    }

    /**
     * Configures the game screen to display the splash screen
     */
    private void splashScreen ()
    {
        // Clear the screen, reset the level, and display the legend
        clear();
        display.setLegend("Asteroids");

        // Place four asteroids near the corners of the screen.
        placeAsteroids();
    }

    /**
     * The game is over. Displays a message to that effect.
     */
    private void finalScreen ()
    {
        display.setLegend(GAME_OVER);
        display.removeKeyListener(this);
    }

    /**
     * Place a new ship in the center of the screen. Remove any existing ship first.
     */
    private void placeShip ()
    {
        // Place a new ship
        Participant.expire(ship);
        ship = new Ship(SIZE / 2, SIZE / 2, -Math.PI / 2, this);
        addParticipant(ship);
        display.setLegend("");
//        if(ship!=null) {
           scheduleTransition((int)Math.round(5000*(RANDOM.nextDouble()+1)));
//            }

    }

     /** Places an asteroid near one corner of the screen. Gives it a random velocity and rotation.
     */
    private void placeAsteroids ()
    {

        addParticipant(new Asteroid(RANDOM.nextInt(4), 2, EDGE_OFFSET, EDGE_OFFSET, 3, this));
        addParticipant(new Asteroid(RANDOM.nextInt(4), 2, EDGE_OFFSET, -EDGE_OFFSET, 3, this));
        addParticipant(new Asteroid(RANDOM.nextInt(4), 2, -EDGE_OFFSET, EDGE_OFFSET, 3, this));
        addParticipant(new Asteroid(RANDOM.nextInt(4), 2, -EDGE_OFFSET, -EDGE_OFFSET, 3, this));
        for(int i = 0; i< level-1;i++)
        {
            addParticipant(new Asteroid(RANDOM.nextInt(4), 2, EDGE_OFFSET, EDGE_OFFSET, 3, this)); 
        }

    }
    
     /**
     * Place an alien ship
     */

    public void placeAlien ()
    {
    
        if (level == 2)
        {

            Participant.expire(alien);
            alien = new Alien(375, 0, 2,this);
            addParticipant(alien);
            sounds.soundCall("bigAlien");
            
        }
        else if(level>=3)
        {
            Participant.expire(alien);
            alien = new Alien(375, 0, 1,this);
            addParticipant(alien);
            sounds.soundCall("smallAlien");
        }
        
        
        
    }

    /**
     * Place A random amount of Debris wherever something blows up
     */
    private void placeDebris (double x, double y)
    {

        for (int i = 0; i <= RANDOM.nextInt(5); i++)
        {
            addParticipant(new Debris(x, y));
        }

    }

    /**
     * Clears the screen so that nothing is displayed
     */
    private void clear ()
    {
        pstate.clear();
        display.setLegend("");
        ship = null;
    }

    /**
     * Sets things up and begins a new game.
     */
    private void initialScreen ()
    {
        // Clear the screen
        clear();

        // Place asteroids
        placeAsteroids();

        // Place the ship
        placeShip();

        // Reset statistics
        lives = 3;
        level = 1;
        score = 0;
        numBullets=0;

        // Start listening to events (but don't listen twice)
        display.removeKeyListener(this);
        display.addKeyListener(this);

        // Give focus to the game screen
        display.requestFocusInWindow();
    }

    /**
     * Adds a new Participant
     */
    public void addParticipant (Participant p)
    {
        pstate.addParticipant(p);
    }

    /**
     * The ship has been destroyed
     */
    public void shipDestroyed ()
    {
         
        
        // Gets the location of the ship and places Debris accordingly
        placeDebris(ship.getX(), ship.getY());

        // Null out the ship
        ship=null;
        

        // Display a legend
        display.setLegend("Get rekt, son");

        // Decrement lives
        lives--;

        // Since the ship was destroyed, schedule a transition 
        scheduleTransition(END_DELAY);
        
        
       
        
    }

    /**
     * An asteroid has been destroyed
     */
   public void asteroidDestroyed (Asteroid a)
       {
        int size = a.getSize();

        if (size == 2)
        {
            sounds.soundCall("largeDestroyed");
            // Creates two smaller Asteroids of semi-random size
            addParticipant(new Asteroid(RANDOM.nextInt(3), RANDOM.nextInt(2), a.getX(), a.getY(), 3, this));
            addParticipant(new Asteroid(RANDOM.nextInt(3), RANDOM.nextInt(2), a.getX(), a.getY(), 3, this));
            score = score + 20;
        }
        else if (size == 1)
        {
            sounds.soundCall("mediumDestroyed");
            // creates two small Asteroids
            addParticipant(new Asteroid(RANDOM.nextInt(3), 0, a.getX(), a.getY(), 3, this));
            addParticipant(new Asteroid(RANDOM.nextInt(3), 0, a.getX(), a.getY(), 3, this));
            score = score + 50;
        }
        else
        {
            sounds.soundCall("smallDestroyed");
            score = score + 100;
        }

        // Creates Debris at the site of the asteroids destruction
        placeDebris(a.getX(), a.getY());

        // If all the asteroids are gone, schedule a transition
        if (pstate.countAsteroids() == 0)
        {
            level++;
            scheduleTransition(END_DELAY);
        }
    }
      /**
     * When an alien is destroyed it applies the proper score boost and sets a timer to spawn a new one in
     * 5-10 Seconds
     */

    public void alienDestroyed ()
    {
        
        if (alien.getScalar()==2)
        {
            score = score + 200;
             sounds.soundCall("bigAlien");
        }
        else
        {
            score = score + 1000;
             sounds.soundCall("smallAlien");
        }
        placeDebris(alien.getX(), alien.getY());
        
        
        
        alien=null;
        if(ship!=null) {
        scheduleTransition((int)Math.round(5000*(RANDOM.nextDouble()+1)));
        }
        
    }
    
    
    public void alienShoot()
    {
        
        alienBullet = new AlienBullet(this,alien,ship);
        addParticipant(alienBullet);
        
    }
   

    /**
     * Schedules a transition m msecs in the future
     */
    private void scheduleTransition (int m)
    {
        transitionTime = System.currentTimeMillis() + m;
    }

    /**
     * This method will be invoked because of button presses and timer events.
     */
    @Override
    public void actionPerformed (ActionEvent e)
    {

        // The start button has been pressed. Stop whatever we're doing
        // and bring up the initial screen
        if (e.getSource() instanceof JButton)
        {
            initialScreen();
        }

        // Time to refresh the screen and deal with keyboard input
        else if (e.getSource() == refreshTimer)
        {
            if (ship != null)
            {
                if (ship.getIsTurningRight())
                {
                    ship.turnRight();
                }

                if (ship.getIsTurningLeft())
                {
                    ship.turnLeft();
                }

                if (ship.getIsAccellerating())
                {
                    ship.accelerate();
                }
                
                
            }
            
            // It may be time to make a game transition
            performTransition();

            // Move the participants to their new locations
            pstate.moveParticipants();

            // Refresh screen
            display.refresh();
        }
    }

    /**
     * Returns an iterator over the active participants
     */
    public Iterator<Participant> getParticipants ()
    {
        return pstate.getParticipants();
    }
    
    public int getBullets()
    {
        return numBullets;
    }
    
    public void setBullets(int num)
    {
        numBullets=num;
    }

   
    /**
     * If the transition time has been reached, transition to a new state
     */
    private void performTransition ()
    {
        // Do something only if the time has been reached
        if (transitionTime <= System.currentTimeMillis())
        {
            
            
                
            
            // Clear the transition time
            transitionTime = Long.MAX_VALUE;
            

            // If there are no lives left, the game is over. Show the final
            // screen.
            if (lives <= 0)
            {
                finalScreen();
            }
            //If there are no asteroids left, resets the level
            else if (pstate.countAsteroids() == 0)
            {
               clear();
               numBullets = 0;
               placeAsteroids();
               placeShip();
               if (sounds.bigSaucer.isRunning())
               {
                   sounds.bigSaucer.stop();
               }
               else if(sounds.smallSaucer.isRunning())
               {
                   sounds.smallSaucer.stop();
               }
               
            }
            //If there are lives and asteroids left, respawns the ship
            else if(ship==null)
            {
                
              placeShip();
            }
            else if(alien==null)
            {
                placeAlien();
            }
           
        }
    }

    /**
     * If a key of interest is pressed, record that it is down.
     */
    @Override
    public void keyPressed (KeyEvent e)
    {
        if ((e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) && ship != null)
        {
            ship.setIsTurningRight(true);
        }
        if ((e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) && ship != null)
        {
            ship.setIsTurningLeft(true);
        }
        if ((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) && ship != null)
        {
            ship.setIsAccellerating(true);
        }
        if ((e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S
                || e.getKeyCode() == KeyEvent.VK_SPACE) && ship != null)
        {
            sounds.soundCall("fire");
            numBullets++;
            bullet = new Bullet(this,ship);
            addParticipant(bullet);
            if(numBullets>8)
            {
                Participant.expire(bullet);
                numBullets--;
            }
        }
    }

    /**
     * These events are ignored.
     */
    @Override
    public void keyTyped (KeyEvent e)
    {
    }

    /**
     * These events are ignored.
     */
    @Override
    public void keyReleased (KeyEvent e)
    {
        if ((e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) && ship != null)
        {
            ship.setIsTurningRight(false);
        }
        if ((e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) && ship != null)
        {
            ship.setIsTurningLeft(false);
        }
        if ((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) && ship != null)
        {
            ship.setIsAccellerating(false);
        }
        
        
    }
}
