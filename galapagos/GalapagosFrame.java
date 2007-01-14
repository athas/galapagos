package galapagos;

import java.util.*;

import javax.swing.*;
import java.awt.*;

public class GalapagosFrame extends JFrame implements Observer {

    private AreaPanel area;
    private Biotope biotope;
    public TreeMap<String, Color> colorMap;
    public int pixelSize;
    
    public GalapagosFrame()
    {
        pixelSize = 5;
        colorMap = createColorMap();
        this.setLayout(new BorderLayout());
        
        
        ArrayList<Behavior> behaviors = new ArrayList<Behavior>();
        behaviors.add(new Samaritan());
        behaviors.add(new TitForTat());
        behaviors.add(new Grudger());
        behaviors.add(new Cheater());
        behaviors.add(new FlipFlopper());
        behaviors.add(new ProbingTitForTat());
        behaviors.add(new RandomFinch());
        behaviors.add(new SuspiciousTitForTat());
        
        biotope = new Biotope(100,100,0.2,20,10,4,10,15,100,behaviors);
        //biotope.addObserver(new BiotopeLogger());
        biotope.addObserver(this);
        area = new AreaPanel();
        this.add(new JButton("asd"), BorderLayout.NORTH);
        this.add(area,BorderLayout.CENTER);
        
        area.reset(biotope.world.width(), biotope.world.height(), pixelSize);
        
        this.addWindowListener(new Terminator());
        this.setSize(biotope.width*pixelSize, biotope.height * pixelSize);
        
        this.setVisible(true);
        
        for(int i = 0; i < 1000; i++)
            biotope.runRound();
    }
    
    public void update(Observable biotope, Object arg)
    {
        Biotope bio = (Biotope) biotope;
        setTitle(((Integer) bio.round()).toString());
        for(World<GalapagosFinch>.Place place : bio.world)
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
    
    private TreeMap<String, Color> createColorMap()
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
