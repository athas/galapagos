package galapagos;

import java.util.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class GalapagosFrame extends JFrame implements Observer {

    private AreaPanel area;
    private Biotope biotope;
    public static final Behavior[] behaviors = {new Cheater(), new FlipFlopper(), new Grudger(), new ProbingTitForTat(),
                                          new RandomFinch(), new Samaritan(), new SuspiciousTitForTat(), new TitForTat()};
    public TreeMap<String, Color> colorMap;
    public int pixelSize;
    private StatisticsPanel statistics;
    private BiotopeController controller;
    
    private JButton newBiotope;
    private JButton nextRound;
    private JSpinner numberOfRounds;
    private JButton severalRounds;
    private JButton stopRounds;
    
    private static final Dimension minimumButtonDimension = new Dimension(0,30);
    
    public GalapagosFrame()
    {
        setTitle("Galapagos Finch Simulator");
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
        
        Container controlButtons = Box.createHorizontalBox();
        this.add(controlButtons, BorderLayout.NORTH);
        controlButtons.add(Box.createGlue());
        newBiotope = new JButton("New Biotope");
        newBiotope.setActionCommand("newBiotope");
        newBiotope.addActionListener(controller);
        newBiotope.setMinimumSize(minimumButtonDimension);
        controlButtons.add(newBiotope);
        nextRound = new JButton("Next Round");
        nextRound.setActionCommand("nextRound");
        nextRound.addActionListener(controller);
        nextRound.setMinimumSize(minimumButtonDimension);
        controlButtons.add(nextRound);
        numberOfRounds = new JSpinner(new SpinnerNumberModel(50,0,Integer.MAX_VALUE,1));
        numberOfRounds.setMaximumSize(new Dimension(100,30));
        numberOfRounds.setMinimumSize(minimumButtonDimension);
        controlButtons.add(numberOfRounds);
        severalRounds = new JButton("Compute Several Rounds");
        severalRounds.setActionCommand("severalRounds");
        severalRounds.addActionListener(controller);
        severalRounds.setMinimumSize(minimumButtonDimension);
        controlButtons.add(severalRounds);
        stopRounds = new JButton("Stop Simulation");
        stopRounds.setActionCommand("stopRounds");
        stopRounds.addActionListener(controller);
        stopRounds.setMinimumSize(minimumButtonDimension);
        controlButtons.add(stopRounds);
        controlButtons.add(Box.createGlue());
        
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
    
    public void update(Observable observableBiotope, Object arg)
    {
        Biotope biotope = (Biotope) observableBiotope;
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
    
    public void disableButtons() {
        newBiotope.setEnabled(false);
        nextRound.setEnabled(false);
        numberOfRounds.setEnabled(false);
        severalRounds.setEnabled(false);
        stopRounds.doClick();
        stopRounds.setEnabled(false);
    }
    
    public void enableButtons() {
        newBiotope.setEnabled(true);
        nextRound.setEnabled(true);
        numberOfRounds.setEnabled(true);
        severalRounds.setEnabled(true);
        stopRounds.setEnabled(true);
    }
    
    public void setBiotope() {
        
    }
}
