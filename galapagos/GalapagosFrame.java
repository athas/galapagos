package galapagos;

import java.util.*;

import javax.swing.*;
import java.awt.*;

public class GalapagosFrame extends JFrame implements Observer {

    private AreaPanel area;
    private Biotope biotope;
    public TreeMap<String, Color> colorMap;
    public int pixelSize;
    private JSpinner numberOfRounds;
    private StatisticsPanel statistics;
    private BiotopeController controller;
    
    public GalapagosFrame()
    {
        pixelSize = 5;
        colorMap = createColorMap();
        this.setLayout(new BorderLayout());
        
        area = new AreaPanel();
        
        ArrayList<Behavior> behaviors = new ArrayList<Behavior>();
        behaviors.add(new Samaritan());
        behaviors.add(new TitForTat());
        behaviors.add(new Grudger());
        behaviors.add(new Cheater());
        behaviors.add(new FlipFlopper());
        behaviors.add(new ProbingTitForTat());
        behaviors.add(new RandomFinch());
        behaviors.add(new SuspiciousTitForTat());
        
        statistics = new StatisticsPanel(this);
        this.add(statistics, BorderLayout.SOUTH);
        
        biotope = new Biotope(100,100,0.05,20,10,2,50,100,100,behaviors);
        biotope.addObserver(new BiotopeLogger());
        biotope.addObserver(this);
        biotope.addObserver(statistics);
        
        controller = new BiotopeController(this, biotope);
        
        Container controlButtons = new Container();
        this.add(controlButtons, BorderLayout.NORTH);
        controlButtons.setLayout(new FlowLayout());
        JButton button = new JButton("New Biotope");
        button.setActionCommand("newBiotope");
        button.addActionListener(controller);
        controlButtons.add(button);
        button = new JButton("Next Round");
        button.setActionCommand("nextRound");
        button.addActionListener(controller);
        controlButtons.add(button);
        numberOfRounds = new JSpinner(new SpinnerNumberModel(50,0,Integer.MAX_VALUE,1));
        numberOfRounds.setPreferredSize(new Dimension(100,22));
        controlButtons.add(numberOfRounds);
        button = new JButton("Compute Several Rounds");
        button.setActionCommand("severalRounds");
        button.addActionListener(controller);
        controlButtons.add(button);
        button = new JButton("Stop Simulation");
        button.setActionCommand("stopRounds");
        button.addActionListener(controller);
        controlButtons.add(button);
        
        Container container = new Container();
        container.setLayout(new GridBagLayout());
        container.add(area);
        this.add(container,BorderLayout.CENTER);
        this.doLayout();
        
        area.reset(biotope.world.width(), biotope.world.height(), pixelSize);
        
        this.addWindowListener(new Terminator());
        this.setSize(biotope.width*pixelSize + 100, biotope.height * pixelSize + 100);
        
        this.setVisible(true);
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
        colorMap.put("Tit for Tat", Color.GREEN);
        colorMap.put("Suspicious Tit for Tat", Color.ORANGE);
        colorMap.put("Probing Tit for Tat", Color.CYAN);
        colorMap.put("Flip-Flopper", Color.WHITE);
        colorMap.put("Random", Color.MAGENTA);
        
        return colorMap;
    }
    
    public Integer getNumberOfRounds () {
        return (Integer) ((SpinnerNumberModel) numberOfRounds.getModel()).getNumber();
    }
}
