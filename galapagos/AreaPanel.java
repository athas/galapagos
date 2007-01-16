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
    
    public void update()
    {
        source.newPixels( 0, 0, sourceX * zoomFactor, sourceY * zoomFactor );
    }
    
    /**
     * Change size and / or zoomFactor
     * @param sourceX the new width of the source
     * @param sourceY the new height of the source
     * @param zoomFactor the multiplication of size for source pixels
     */
    public void reset( int sourceX, int sourceY, int zoomFactor )
    {
        this.zoomFactor = zoomFactor;
        this.sourceX = sourceX;
        this.sourceY = sourceY;
        
        Dimension dimension = new Dimension( sourceX * zoomFactor, sourceY * zoomFactor );

        setMinimumSize( dimension );
        setPreferredSize( dimension );
        setMaximumSize( dimension );

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

    /**
     * Override the Panel paint component. This is not used directly
     */

    public void paint(Graphics g) {
        g.drawImage( image, 0, 0, this );
    }
    
    private int zoomFactor;
    private int sourceX;
    private int sourceY;

    private Image image;
    private int[] pixels;
    private MemoryImageSource source;











}