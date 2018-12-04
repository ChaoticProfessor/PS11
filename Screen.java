package asteroids.game;

import static asteroids.game.Constants.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.Iterator;
import javax.swing.*;

/**
 * The area of the display in which the game takes place.
 */
@SuppressWarnings("serial")
public class Screen extends JPanel
{
    /** Legend that is displayed across the screen */
    private String legend;

    /** Game controller */
    private Controller controller;
    
    private Shape lifeOutline;

    /**
     * Creates an empty screen
     */
    public Screen (Controller controller)
    {
        this.controller = controller;
        legend = "";
        setPreferredSize(new Dimension(SIZE, SIZE));
        setMinimumSize(new Dimension(SIZE, SIZE));
        setBackground(Color.black);
        setForeground(Color.white);
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 120));
        setFocusable(true);
    }

    /**
     * Set the legend
     */
    public void setLegend (String legend)
    {
        this.legend = legend;
    }

    /**
     * Paint the participants onto this panel
     */
    @Override
    public void paintComponent (Graphics graphics)
    {
        // Use better resolution
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

    // Do the default painting
        super.paintComponent(g);
        
        //Paints the Lives
        Path2D.Double poly = new Path2D.Double();
        

        
        for(int i = 1; i <= controller.getLives(); i++)
        {
        poly.moveTo(21 + (50 * i), 50);
        poly.lineTo(-21 + (50 * i), 12 + 50);
        poly.lineTo(-14 + (50 * i), 10 + 50);
        poly.lineTo(-14 + (50 * i), -10 + 50);
        poly.lineTo(-21 + (50 * i), -12 + 50);
        poly.closePath();
 
        lifeOutline = poly;
       
        g.draw(lifeOutline);
        }
        
        
        

        // Draw each participant in its proper place
        Iterator<Participant> iter = controller.getParticipants();
        while (iter.hasNext())
        {
            iter.next().draw(g);
        }


        // Draw the legend across the middle of the panel
        int size = g.getFontMetrics().stringWidth(legend);
        g.drawString(legend, (SIZE - size) / 2, SIZE / 2);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 24));
        g.drawString(String.valueOf(controller.getLevel()), 600, 50);
        g.drawString(String.valueOf(controller.getScore()), 600, 100);
    }
}
