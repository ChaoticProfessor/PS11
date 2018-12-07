package asteroids.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.*;

/**
 * Sounds Class
 */
@SuppressWarnings("serial")
public class Sounds extends JFrame 
{
    

    /** A Clip that, when played, sounds like a weapon being fired */
    private Clip fire;
    
    /** A clip that will play when an alien is destroyed*/
    private static Clip alienDestroyed;
    
    /**A clip that is played for each size of asteroid being destroyed */
    private Clip largeDestroyed;
    private Clip mediumDestroyed;
    private Clip smallDestroyed;
    
    /**A clip that is played when thrust is  applied*/
    private Clip thrust;
    
    

    /** A Clip that, when played repeatedly, sounds like a small saucer flying */
    private Clip smallSaucer;
   /** A clip that is played with a big alien*/
    private Clip bigSaucer;

    /**
     * Initializes every variable.
     */
    public Sounds()
    {
        // We create the clips in advance so that there will be no delay
        // when we need to play them back. Note that the actual wav
        // files are stored in the "sounds" project.
        fire = createClip("/sounds/fire.wav");
        smallSaucer = createClip("/sounds/saucerSmall.wav");
        alienDestroyed = createClip("/sounds/bangAlienShip.wav");
        largeDestroyed = createClip("/sounds/bangLarge.wav");
        mediumDestroyed = createClip("/sounds/bangMedium.wav");
        smallDestroyed = createClip("/sounds/bangSmall.wav");
        thrust = createClip("/sounds/thrust.wav");
        bigSaucer = createClip("/sounds/saucerBig.wav");
       beat1 = createClip("/sounds/beat1.wav");
        beat2 = createClip("/sounds/beat2.wav");
        
    }

    /**
     * Creates an audio clip from a sound file.
     */
    public Clip createClip (String soundFile)
    {
        // Opening the sound file this way will work no matter how the
        // project is exported. The only restriction is that the
        // sound files must be stored in a package.
        try (BufferedInputStream sound = new BufferedInputStream(getClass().getResourceAsStream(soundFile)))
        {
            // Create and return a Clip that will play a sound file. There are
            // various reasons that the creation attempt could fail. If it
            // fails, return null.
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
            return clip;
        }
        catch (LineUnavailableException e)
        {
            return null;
        }
        catch (IOException e)
        {
            return null;
        }
        catch (UnsupportedAudioFileException e)
        {
            return null;
        }
    }
    /**Background Noises*/
    public void backgroundNoise (String sound)
    {

        if (sound.equals("beat1") && beat1 != null)
        {
            if (beat1.isRunning())
            {
                beat1.stop();
            }
            beat1.setFramePosition(0);
            beat1.start();  
        }
        else if (sound.equals("beat2") && beat2 != null)
        {
            if (beat2.isRunning())
            {
                beat2.stop();
            }
            beat2.setFramePosition(0);
            beat2.start();        
        }
    }

    /**
     * Plays sounds depending on which button was clicked.
     */
    public void soundCall (String sound)
    {
     
        // We "rewind" the fireClip and play it.
        if (sound.equals("fire") && fire != null)
        {
            if (fire.isRunning())
            {
                fire.stop();
            }
            fire.setFramePosition(0);
            fire.start();
        }
        else if (sound.equals("smallSaucer") && smallSaucer != null)
        {
            if (smallSaucer.isRunning())
            {
                smallSaucer.stop();
            }
            else
            {
            smallSaucer.setFramePosition(0);
            smallSaucer.loop(Clip.LOOP_CONTINUOUSLY);        
            }
       }
        else if (sound.equals("bigSaucer") && bigSaucer != null)
        {
            if (bigSaucer.isRunning())
            {
                bigSaucer.stop();
            }
            else
            {
            bigSaucer.setFramePosition(0);
            bigSaucer.loop(Clip.LOOP_CONTINUOUSLY); 
            }
        }
        else if (sound.equals("thrust") && thrust != null)
        {
            if (thrust.isRunning())
            {
                thrust.stop();
            }
            else
            {
            thrust.setFramePosition(0);
            thrust.loop(Clip.LOOP_CONTINUOUSLY); 
            }
        }
        else if (sound.equals("alienDestroyed") && alienDestroyed != null)
        {
            if (alienDestroyed.isRunning())
            {
                alienDestroyed.stop();
            }
            alienDestroyed.setFramePosition(0);
            alienDestroyed.start();        
        }
        else if (sound.equals("largeDestroyed") && largeDestroyed != null)
        {
            if (largeDestroyed.isRunning())
            {
                largeDestroyed.stop();
            }
            largeDestroyed.setFramePosition(0);
            largeDestroyed.start();        
        }
        else if (sound.equals("mediumDestroyed") && mediumDestroyed != null)
        {
            if (mediumDestroyed.isRunning())
            {
                mediumDestroyed.stop();
            }
            mediumDestroyed.setFramePosition(0);
            mediumDestroyed.start();        
        }
        else if (sound.equals("smallDestroyed") && smallDestroyed != null)
        {
            if (smallDestroyed.isRunning())
            {
                smallDestroyed.stop();
            }
            smallDestroyed.setFramePosition(0);
            smallDestroyed.start();        
        }
    
        
       
        
        
       
    }
}

