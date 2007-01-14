package galapagos;

import java.util.*;

import javax.swing.JFrame;
import java.awt.Color;

public class GalapagosFrame extends JFrame {

    AreaPanel area;
    Biotope biotope;
    final Color[] colors;
    
    public GalapagosFrame()
    {
        this.setVisible(true);
        ArrayList<Behavior> behaviors = new ArrayList<Behavior>();
        behaviors.add(new Cheater());
        behaviors.add(new Samaritan());
        behaviors.add(new Grudger());
        behaviors.add(new FlipFlopper());
        behaviors.add(new TitForTat());
        behaviors.add(new SuspiciousTitForTat());
        
        biotope = new Biotope(behaviors);
        this.setSize(500, 500);
        area = new AreaPanel();
        this.add(area);
        area.reset( biotope.width, biotope.height, 5);
        
        
        
        TreeMap<String, Color> behaviorColors = new TreeMap<String, Color>();
        
        colors = colorList();
        int color = 0;
        for(Behavior bhv : behaviors)
        {
            if(color > colors.length)
                color = 0;
            
            behaviorColors.put(bhv.toString(), colors[color]);
            
            color++;
        }
        
        Biotope biotope = new Biotope(behaviors);
        
        for (int i = 0; i < 1000; i++)
        {
            biotope.runRound();
            
            for(World<GalapagosFinch>.Place place : biotope.world)
            {
                if(place.element() != null)
                    area.pixel(place.xPosition(), place.yPosition(), behaviorColors.get(place.element().behavior().toString()));
                else
                    area.pixel(place.xPosition(), place.yPosition(), Color.BLACK);
            }
            area.update();
            this.setTitle((i+1) + "");
        }
        
        
        
    }
    
    public Color[] colorList()
    {
        Color[] colors = new Color[10];
        colors[0] = Color.BLUE;
        colors[1] = Color.YELLOW;
        colors[2] = Color.RED;
        colors[3] = Color.GREEN;
        colors[4] = Color.PINK;
        colors[5] = Color.ORANGE;
        colors[6] = Color.WHITE;
        colors[7] = Color.CYAN;
        colors[8] = Color.MAGENTA;
        colors[9] = Color.GRAY;
        
        return colors;
    }
}
