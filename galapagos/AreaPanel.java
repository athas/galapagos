package galapagos;

import java.awt.*;
import java.awt.image.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Map;

import javax.swing.*;

/**
 * A Swing component that displays a 2D array of pixels.
 * 
 * The pixels can be updated one at a time.
 * 
 * The pixels can be displayed with a magnification factor that can be changed.
 * 
 * To avoid confusion all sizes and coordinates are given in the source coordinate system.
 */

public class AreaPanel extends JPanel implements Observer
{
    private int zoomFactor;
    private int sourceX;
    private int sourceY;

    private Image image;
    private int[] pixels;
    private MemoryImageSource source;
    
	private Map<Behavior, Color> colorMap;
	private Color background;
	
    public AreaPanel(Map<Behavior, Color> colorMap)
    {
    	this.colorMap = colorMap;
    	background = Color.BLACK;
    }
    
    /**
     * Command: color a pixel
     * 
     * @param x x position
     * @param y y position
     * @require 0 <= x < sourceX
     * @require 0 <= y < sourceY
     */
    public void pixel( int x, int y, Color c )
    {
        int pixelBase = y * zoomFactor * zoomFactor * sourceX + x * zoomFactor;
        int pixel = 0;
        
        for( int i = 0; i < zoomFactor; ++i )
        {
            for( int j = 0; j < zoomFactor; ++j )
            {
                pixel = pixelBase + j * sourceX * zoomFactor + i;
                pixels[ pixel ] = c.getRGB();
            }
        }
    }

    /**
     * Make sure that changes are drawn.
     */
    public void refresh()
    {
        source.newPixels( 0, 0, sourceX * zoomFactor, sourceY * zoomFactor );
    }
    
    /**
     * Draws the Biotope to the screen when it changes.
     * @require observableBiotope instanceof Biotope
     * @ensure That all finches are drawn to the screen.
     */
    public void update(Observable observableBiotope, Object arg)
    {
    	Biotope biotope = (Biotope) observableBiotope;
    	
    	drawBiotope(biotope);
    }
    
    /**
     * Draws the specified biotope to the screen
     * @param biotope The Biotope to draw.
     * @require biotope.world.width == this.getWidth() * zoomFactor
     *  		&& biotope.world.height == this.getHeight() * zoomFactor
     */
    public void drawBiotope(Biotope biotope) {
    	assert biotope.world.width() == this.getWidth() * zoomFactor;
    	assert biotope.world.height() == this.getHeight() * zoomFactor;
        for(World<GalapagosFinch>.Place place : biotope.world)
        {
            GalapagosFinch element = place.getElement();
            if(element != null)
                pixel(place.xPosition(), place.yPosition(), colorByBehavior(element.behavior()));
            else
                pixel(place.xPosition(), place.yPosition(), background);
        }
        refresh();
    }
    
    /**
     * Get the color associated with the behavior
     * @param behavior The behavior to look-up in the ColorMap
     * @return The color associated with behavior.
     * @require colorMap.containsKey(behavior)
     * @ensure colorByBehavior(behavior).equals(colorMap.get(behavior.toString())
     */
    public Color colorByBehavior(Behavior behavior)
    {
        Color c = colorMap.get(behavior);
        assert c != null : "Color not defined for this Behavior";
        return c;
    }
    
    /**
     * Change size and / or zoomFactor
     * @param sourceX the new width of the source
     * @param sourceY the new height of the source
     * @param zoomFactor the multiplication of size for source pixels
     * @require zoomFactor > 0
     */
    public void reset( int sourceX, int sourceY, int zoomFactor )
    {
    	assert zoomFactor > 0 : "Can't have negative or 0 zoomFactor";
        this.zoomFactor = zoomFactor;
        this.sourceX = sourceX;
        this.sourceY = sourceY;
        
        Dimension dimension = new Dimension( sourceX * zoomFactor, sourceY * zoomFactor );

        setMinimumSize( dimension );
        setPreferredSize( dimension );
        setMaximumSize( dimension );
        
        pixels = new int[ dimension.width * dimension.height ];
        
        //fill with black
        int black = background.getRGB();
        for(int i = 0; i < pixels.length; ++i)
        	pixels[i] = black;
        
        source = new MemoryImageSource( dimension.width, 
                                        dimension.height, 
                                        pixels,
                                        0,
                                        dimension.width );
        
        source.setAnimated( true );
        source.setFullBufferUpdates( true );
        image = createImage( source );        

        refresh();
        
        repaint();
    }

    /**
     * Override the Panel paint component. This is not used directly
     */

    public void paint(Graphics g) {
        g.drawImage( image, 0, 0, this );
    }
}