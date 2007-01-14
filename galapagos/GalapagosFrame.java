package galapagos;

import java.util.*;

import javax.swing.JFrame;
import java.awt.Color;

public class GalapagosFrame extends JFrame {

    AreaPanel area;
    Biotope biotope;
    final TreeMap<String, Color> colorMap;
    public int pixelSize;
    
    public GalapagosFrame()
    {
        pixelSize = 4;
        colorMap = createColorMap();
        
        
        ArrayList<Behavior> behaviors = new ArrayList<Behavior>();
        behaviors.add(new Samaritan());
        behaviors.add(new TitForTat());
        behaviors.add(new Grudger());
        
        biotope = new Biotope(behaviors);
        area = new AreaPanel();
        area.reset( biotope.width, biotope.height, 4);
        
        this.addWindowListener(new Terminator());
        this.setSize(biotope.width*pixelSize, biotope.height * pixelSize);
        this.add(area);
        this.setVisible(true);
        
        run(10000);
    }
    
    public void run(int count)
    {
        for (int i = 0; i < count; i++)
        {
            runOnce();
            this.setTitle((i+1) + "");
        }
    }
    
    public void runOnce()
    {
        biotope.runRound();
        
        for(World<GalapagosFinch>.Place place : biotope.world)
        {
            GalapagosFinch element = place.element();
            if(element != null)
                area.pixel(place.xPosition(), place.yPosition(), colorByBehavior(element.behavior()));
            else
                area.pixel(place.xPosition(), place.yPosition(), Color.BLACK);
        }
        area.update();
    }
    
    public Color colorByBehavior(Behavior bhv)
    {
        Color c = colorMap.get(bhv.toString());
        
        if(c == null)
            throw new Error("Color not defined for this Behavior");
        
        return c;
    }
    
    public TreeMap<String, Color> createColorMap()
    {
        TreeMap<String, Color> colorMap = new TreeMap<String, Color>();
        
        colorMap.put("Cheater", Color.BLUE);
        colorMap.put("Samaritan", Color.RED);
        colorMap.put("Grudger", Color.YELLOW);
        colorMap.put("TitForTat", Color.GREEN);
        colorMap.put("SuspiciousTitForTat", Color.ORANGE);
        colorMap.put("ProbingTitForTat", Color.CYAN);
        colorMap.put("FlipFlopper", Color.WHITE);
        colorMap.put("RandomFinch", Color.MAGENTA);
        
        return colorMap;
    }
}
