package galapagos;

import java.util.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class GalapagosFrame extends JFrame implements Observer {

    private AreaPanel area;
    private Biotope biotope;
    public static Behavior[] behaviors = {new Cheater(), new FlipFlopper(), new Grudger(), new ProbingTitForTat(),
                                          new RandomFinch(), new Samaritan(), new SuspiciousTitForTat(), new TitForTat()};
    public TreeMap<String, Color> colorMap;
    public int pixelSize;
    private StatisticsPanel statistics;
    private BiotopeController controller;
    public final BiotopeCreator biotopeCreator;
    
    private JButton newBiotope;
    private JButton nextRound;
    private JSpinner numberOfRounds;
    private JButton severalRounds;
    private JButton stopRounds;
    
    private static final Dimension minimumButtonDimension = new Dimension(0,30);
    private static final Dimension standardSpinnerSize = new Dimension(100,22);
    
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
        numberOfRounds = new JSpinner(new SpinnerNumberModel(50,0,Integer.MAX_VALUE,10));
        numberOfRounds.setPreferredSize(standardSpinnerSize);
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
        
        biotopeCreator = new BiotopeCreator(this);
        
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
    
    public class BiotopeCreator extends JFrame {
        private final JSpinner width, height;
        private final JSpinner breedingProbability;
        private final JSpinner maxHitpoints, initialHitpoints, hitpointsPerRound;
        private final JSpinner minMaxAge, maxMaxAge;
        private final JSpinner finchesPerBehavior;
        private final JCheckBox[] behaviors;
        private final JButton okButton, cancelButton;
        
        private BiotopeCreator(GalapagosFrame frame) {
            this.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {enableButtons();}
            });
            
            setTitle("Biotope Creator");
            this.setLayout(new BorderLayout());
            JPanel options = new JPanel(new GridBagLayout());
            this.add(options, BorderLayout.CENTER);
            
            // The world size options.
            JPanel optionGroup = new JPanel(new GridBagLayout());
            optionGroup.setBorder(BorderFactory.createTitledBorder("World size"));
            width = new JSpinner(new SpinnerNumberModel(100,0,Integer.MAX_VALUE,10));
            width.setPreferredSize(standardSpinnerSize);
            height = new JSpinner(new SpinnerNumberModel(100,0,Integer.MAX_VALUE,10));
            height.setPreferredSize(standardSpinnerSize);
            optionGroup.add(new JLabel("Width",SwingConstants.CENTER), getConstraints(0,0));
            optionGroup.add(width, getConstraints(0,1));
            optionGroup.add(new JLabel("Height",SwingConstants.CENTER), getConstraints(1,0));
            optionGroup.add(height, getConstraints(1,1));
            options.add(optionGroup, getConstraints(0,0));
            
            // Hitpoint options.
            optionGroup = new JPanel(new GridBagLayout());
            optionGroup.setBorder(BorderFactory.createTitledBorder("Finch hitpoints"));
            initialHitpoints = new JSpinner(new SpinnerNumberModel(5,0,Integer.MAX_VALUE,1));
            initialHitpoints.setPreferredSize(standardSpinnerSize);
            maxHitpoints = new JSpinner(new SpinnerNumberModel(10,0,Integer.MAX_VALUE,1));
            maxHitpoints.setPreferredSize(standardSpinnerSize);
            hitpointsPerRound = new JSpinner(new SpinnerNumberModel(3,0,Integer.MAX_VALUE,1));
            hitpointsPerRound.setPreferredSize(standardSpinnerSize);
            optionGroup.add(new JLabel("Initial hitpoints",SwingConstants.CENTER), getConstraints(0,0));
            optionGroup.add(initialHitpoints, getConstraints(0,1));
            optionGroup.add(new JLabel("Max. hitpoints",SwingConstants.CENTER), getConstraints(1,0));
            optionGroup.add(maxHitpoints, getConstraints(1,1));
            optionGroup.add(new JLabel("Hitpoints lost per round",SwingConstants.CENTER), getConstraints(2,0));
            optionGroup.add(hitpointsPerRound, getConstraints(2,1));
            options.add(optionGroup, getConstraints(0,1));
            
            // Age options.
            optionGroup = new JPanel(new GridBagLayout());
            optionGroup.setBorder(BorderFactory.createTitledBorder("Finch age"));
            minMaxAge = new JSpinner(new SpinnerNumberModel(10,0,Integer.MAX_VALUE,1));
            minMaxAge.setPreferredSize(standardSpinnerSize);
            maxMaxAge = new JSpinner(new SpinnerNumberModel(20,0,Integer.MAX_VALUE,1));
            maxMaxAge.setPreferredSize(standardSpinnerSize);
            optionGroup.add(new JLabel("Least possible max. age",SwingConstants.CENTER), getConstraints(0,0));
            optionGroup.add(minMaxAge, getConstraints(0,1));
            optionGroup.add(new JLabel("Greatest possible max. age",SwingConstants.CENTER), getConstraints(1,0));
            optionGroup.add(maxMaxAge, getConstraints(1,1));
            options.add(optionGroup, getConstraints(1,1));
                        
            // Breeding propability and Finches per Behavior.
            optionGroup = new JPanel(new GridBagLayout());
            optionGroup.setBorder(BorderFactory.createTitledBorder("Other parametres"));
            breedingProbability = new JSpinner(new SpinnerNumberModel(0.5,0.0,1.0,0.01));
            breedingProbability.setPreferredSize(new Dimension(50,22));
            finchesPerBehavior = new JSpinner(new SpinnerNumberModel(30,0,Integer.MAX_VALUE,1));
            finchesPerBehavior.setPreferredSize(standardSpinnerSize);
            optionGroup.add(new JLabel("Breeding propability",SwingConstants.CENTER), getConstraints(0,0));
            optionGroup.add(breedingProbability, getConstraints(0,1));
            optionGroup.add(new JLabel("Finches per behavior",SwingConstants.CENTER), getConstraints(1,0));
            optionGroup.add(finchesPerBehavior, getConstraints(1,1));
            options.add(optionGroup, getConstraints(1,0));
            
            // Behavior selection.
            optionGroup = new JPanel(new FlowLayout());
            optionGroup.setBorder(BorderFactory.createTitledBorder("Behaviors"));
            behaviors = new JCheckBox[GalapagosFrame.behaviors.length];
            for (int i = 0; i < behaviors.length; i++) {
                behaviors[i] = new JCheckBox(GalapagosFrame.behaviors[i].toString());
                optionGroup.add(behaviors[i]);
            }
            options.add(optionGroup, new GridBagConstraints(0,2,2,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(5,5,5,5),0,0));
            
            // OK and CANCEL.
            optionGroup = new JPanel(new FlowLayout());
            okButton = new JButton("Create biotope");
            okButton.setActionCommand("okButton");
            okButton.addActionListener(controller);
            cancelButton = new JButton("Abort creation");
            cancelButton.setActionCommand("cancelButton");
            cancelButton.addActionListener(controller);
            optionGroup.add(okButton);
            optionGroup.add(cancelButton);
            this.add(optionGroup, BorderLayout.SOUTH);
            
            this.setSize(getPreferredSize().width + 20, getPreferredSize().height + 40);
        }
        
        /**
         * A set of GridBagConstraints for use with the GridBagLayout.
         * @param x the horisontal position of the component.
         * @param y the vertical position of the component.
         */
        private GridBagConstraints getConstraints (int x, int y) {
            return new GridBagConstraints(x,y,1,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(5,5,5,5),0,0);
        }

        public void createBiotope() {
            disableButtons();
            this.setVisible(true);
        }

        private void abort() {
            enableButtons();
            setVisible(false);
        }
    }
}
