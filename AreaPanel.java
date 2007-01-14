package galapagos;

import java.awt.*;
import java.awt.image.*;
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

public class AreaPanel extends JPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = -2738419921405379064L;
    public AreaPanel()
    {        
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
        int pixelBase = y * zoomFactor * zoomFactor * worldWidth + x * zoomFactor;
        int pixel = 0;
        
        for( int i = 0; i < zoomFactor; ++i )
        {
            for( int j = 0; j < zoomFactor; ++j )
            {
                pixel = pixelBase + j * worldWidth * zoomFactor + i;
                pixels[ pixel ] = c.getRGB();
            }
        }
    }

    /**
     * Make sure that changes are drawn.
     */
    
    public void update()
    {
        source.newPixels( 0, 0, worldWidth * zoomFactor, worldWidth * zoomFactor );
    }
    
    /**
     * Change size
     * @param worldWidth the new width of the source
     * @param worldHeight the new height of the source
     */
    public void reset( int worldWidth, int worldHeight )
    {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        
        reset();
    }
    
    public void reset()
    {
        int pixelSizeWidth = (int)Math.floor(getWidth() / worldWidth);
        int pixelSizeHeight = (int)Math.floor(getHeight() / worldHeight);
        
        zoomFactor = (pixelSizeWidth > pixelSizeHeight) ? pixelSizeHeight : pixelSizeWidth;
        
        Dimension dimension = new Dimension( worldWidth * zoomFactor, worldHeight * zoomFactor );

        //setMinimumSize( dimension );
        //setPreferredSize( dimension );
        //setMaximumSize( dimension );

        pixels = new int[ dimension.width * dimension.height ];
        source = new MemoryImageSource( dimension.width, 
                                        dimension.height, 
                                        pixels,
                                        0,
                                        dimension.width );
        
        source.setAnimated( true );
        source.setFullBufferUpdates( true );
        image = createImage( source );        

        update();
        
        repaint();
    }
    
    public void setBounds(int x, int y, int width, int height)
    {
        //if the Panel size is smaller than the world
        int newWidth = (width < worldWidth) ? worldWidth : width;
        int newHeight = (height < worldHeight) ? worldHeight : height;
        
        
        super.setBounds(x, y, newWidth, newHeight);
        
        reset();
    }

    /**
     * Override the Panel paint component. This is not used directly
     */
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.drawImage( image, this.getWidth()/2 - worldWidth*zoomFactor/2 , 0, this );
    }
    
    private int zoomFactor;
    private int worldWidth;
    private int worldHeight;

    private Image image;
    private int[] pixels;
    private MemoryImageSource source;
}