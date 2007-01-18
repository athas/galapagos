package galapagos;

import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

public class GalapagosFrame extends JFrame implements Observer {

    private AreaPanel area;
    public TreeMap<String, Color> colorMap;
    public int pixelSize;
    private NicerStatisticsPanel statistics;
    private BiotopeLogger logger;
    private BiotopeController controller;
    public final BiotopeCreator biotopeCreator;
    private Biotope biotope;
    
    private JButton newBiotope;
    private JButton nextRound;
    private JSpinner numberOfRounds;
    private JButton severalRounds;
    private JButton unlimitedRounds;
    private JButton stopRounds;
    private JCheckBox toggleLogging;
    private JCheckBox toggleDisplayRefresh;
    private JSpinner timerInterval;

    private ButtonGroup behaviorButtons;
    private Container behaviorButtonsBox;
    private JLabel behaviorButtonsLabel;
    private Behavior selectedBehavior;

    private boolean isLogging;
    private boolean isRefreshing;
    
    private static final Dimension minimumButtonDimension = new Dimension(0,30);
    private static final Dimension standardSpinnerSize = new Dimension(100,22);
    
    private List<Behavior> behaviors;
    
    public GalapagosFrame(Map<Behavior, Color> behaviors)
    {
        makeBehaviorListAndColorMap(behaviors);
        
        pixelSize = 5;
        isRefreshing = true;
        
        area = new AreaPanel();
        MouseInputAdapter listener = new MouseInputAdapter () {
                public void maybeAddFinchAt(int x, int y, Behavior b) { 
                    if (x < biotope.width && y < biotope.height)
                        biotope.putFinch(x, y, b.clone());
                }
                public void mousePressed(MouseEvent e) {
                    if (e.getX() >= 0 && e.getY() >= 0 && selectedBehavior != null) {
                        maybeAddFinchAt(e.getX() / pixelSize, e.getY() / pixelSize, 
                                   selectedBehavior);
                    }
                }
                public void mouseDragged(MouseEvent e) {
                    if (e.getX() >= 0 && e.getY() >= 0 && selectedBehavior != null) {
                        maybeAddFinchAt(e.getX() / pixelSize, e.getY() / pixelSize, 
                                   selectedBehavior);
                    }
                }
            };
        area.addMouseListener(listener);
        area.addMouseMotionListener(listener);
        statistics = new NicerStatisticsPanel(colorMap);
        logger = new BiotopeLogger();
        controller = new BiotopeController(this, biotope);

        this.setLayout(new BorderLayout());

        initializeControls();
        biotopeCreator = new BiotopeCreator(this);
        biotopeCreator.createBiotope();

        this.doLayout();
        
        this.addWindowListener(new Terminator());
        this.setTitle("Galapagos Finch Simulator");
        this.setVisible(true);
    }
    
    private void initializeControls()
    {
        //create top controls
        newBiotope = newButton ("New Biotope", "newBiotope");
        nextRound = newButton ("Next Round", "nextRound");
        severalRounds = newButton ("Compute Several Rounds", "severalRounds");
        unlimitedRounds = newButton ("Go!", "unlimitedRounds");
        stopRounds = newButton ("Stop Simulation", "stopRounds");
        
        numberOfRounds = new JSpinner(new RevisedSpinnerNumberModel(50,0,Integer.MAX_VALUE,10));
        numberOfRounds.setPreferredSize(standardSpinnerSize);
        numberOfRounds.setMaximumSize(new Dimension(100,30));
        numberOfRounds.setMinimumSize(minimumButtonDimension);
        
        toggleLogging = new JCheckBox("Perform logging", isLogging);
        toggleLogging.addActionListener(new ActionListener () {
                public void actionPerformed(ActionEvent e) {
                    if (isLogging)
                        biotope.deleteObserver(logger);
                    else
                        biotope.addObserver(logger);
                    isLogging = !isLogging;
                }
            });

        toggleDisplayRefresh = new JCheckBox("Update display", isRefreshing);
        toggleDisplayRefresh.addActionListener(new ActionListener () {
                public void actionPerformed(ActionEvent e) {
                    if (isRefreshing)
                        biotope.deleteObserver(GalapagosFrame.this);
                    else
                        biotope.addObserver(GalapagosFrame.this);
                    isRefreshing = !isRefreshing;
                }
            });
        
        timerInterval = new JSpinner(new RevisedSpinnerNumberModel(200,0,Integer.MAX_VALUE,100));
        timerInterval.setPreferredSize(standardSpinnerSize);
        timerInterval.setMaximumSize(new Dimension(100,30));
        timerInterval.setMinimumSize(minimumButtonDimension);
        timerInterval.addChangeListener(controller);
        
        Container topContainer = Box.createHorizontalBox();
        topContainer.add(Box.createGlue());
        topContainer.add(newBiotope);
        topContainer.add(nextRound);
        topContainer.add(numberOfRounds);
        topContainer.add(severalRounds);
        topContainer.add(unlimitedRounds);
        topContainer.add(stopRounds);
        topContainer.add(Box.createGlue());
        
        //this container's only purpose is to centralize area
        Container centerContainer = new Container();
        centerContainer.setLayout(new GridBagLayout());
        centerContainer.add(area);

        JPanel leftContainer = new JPanel(new GridBagLayout());
        leftContainer.add(toggleLogging, getComponentConstraints(0,0));
        leftContainer.add(toggleDisplayRefresh, getComponentConstraints(0,1));
        leftContainer.add(new JLabel("Milliseconds between rounds"), getComponentConstraints(0,2));
        leftContainer.add(timerInterval,getComponentConstraints(0,3));
        leftContainer.setMaximumSize(leftContainer.getPreferredSize());
        Container outerLeftContainer = Box.createVerticalBox();
        outerLeftContainer.add(Box.createGlue());
        outerLeftContainer.add(leftContainer);
        outerLeftContainer.add(Box.createGlue());

        behaviorButtonsBox = Box.createVerticalBox();
        behaviorButtonsLabel = new JLabel("Pencil for freehand finch drawing");
        
        this.add(topContainer, BorderLayout.NORTH);
        this.add(centerContainer,BorderLayout.CENTER);
        this.add(statistics, BorderLayout.SOUTH);
        this.add(outerLeftContainer, BorderLayout.WEST);
        this.add(behaviorButtonsBox, BorderLayout.EAST);
    }
    
    /**
     * Create a new JButton with the specified text and actionCommand.
     * 
     * @param text The button's text
     * @param command The button's actionCommand
     * @return The new JButton
     */
    public JButton newButton(String text, String command)
    {
        JButton button = new JButton(text);
        button.setActionCommand(command);
        button.addActionListener(this.controller);
        button.setMinimumSize(minimumButtonDimension);
        
        return button;
    }
    
    /**
     * A set of GridBagConstraints for use with the GridBagLayout. Recommended for single components.
     * @param x the horisontal position of the component.
     * @param y the vertical position of the component.
     */            
    private GridBagConstraints getComponentConstraints (int x, int y) {
        return new GridBagConstraints(x, y, 1, 1, 1.0, 1.0, 
                                        GridBagConstraints.CENTER,
                                        GridBagConstraints.NONE,
                                        new Insets(5,5,5,5),
                                        0, 0);
    }
    
    /**
     * A set of GridBagConstraints for use with the GridBagLayout. Recommended for containers.
     * @param x the horisontal position of the component.
     * @param y the vertical position of the component.
     * @param xSize the horisontal size of the component. How many columns the component
     * covers in the layout.
     * @param ySize the vertical size of the component. How many rows the component
     * covers in the layout.
     */            
    private GridBagConstraints getContainerConstraints (int x, int y, int xSize, int ySize) {
        return new GridBagConstraints(x, y, xSize, ySize, 1.0, 1.0, 
                                        GridBagConstraints.CENTER,
                                        GridBagConstraints.BOTH,
                                        new Insets(0,0,0,0),
                                        0, 0);
    }
    
    public void update(Observable observableBiotope, Object arg)
    {
        Biotope biotope = (Biotope) observableBiotope;
        for(World<GalapagosFinch>.Place place : biotope.world)
        {
            GalapagosFinch element = place.getElement();
            if(element != null)
                area.pixel(place.xPosition(), place.yPosition(), colorByBehavior(element.behavior()));
            else
                area.pixel(place.xPosition(), place.yPosition(), Color.BLACK);
        }
        area.update();
    }
    
    /**
     * Get the color associated with the behavior
     * @param behavior The behavior to look-up in the ColorMap
     * @return The color associated with behavior.
     */
    public Color colorByBehavior(Behavior behavior)
    {
        Color c = colorMap.get(behavior.toString());
        
        if(c == null)
            throw new Error("Color not defined for this Behavior");
        
        return c;
    }
    
    /**
     * Maps each color in the behaviors-map to the associated behaviors name (Behavior.toString());
     * and creates a list of the Behaviors, adding a specific behavior only if there isn't already a
     * Behavior in the list with the same name (Behavior.toString()).
     */
    private void makeBehaviorListAndColorMap(Map<Behavior, Color> behaviors)
    {
        this.behaviors = new ArrayList<Behavior>();
        this.colorMap = new TreeMap<String, Color>();
        Behavior currentBehavior;
        //go through all the behaviors in the behaviors-map and add its string representation to new map 
        for(Map.Entry<Behavior, Color> entry : behaviors.entrySet()) {
            currentBehavior = entry.getKey();
            if (!this.colorMap.containsKey(currentBehavior.toString())) {
                this.behaviors.add(currentBehavior);
                this.colorMap.put(currentBehavior.toString(), entry.getValue());
            }
        }
    }
    
    /**
     * Get the number of rounds specified by the numberOfRounds-textfield
     * @return
     */
    public int getNumberOfRounds () {
        return ((SpinnerNumberModel) numberOfRounds.getModel()).getNumber().intValue();
    }
    
    /**
     * Get the timer interval specified by the timerInterval-textfield
     * @return
     */
    public int getTimerInterval () {
        return ((SpinnerNumberModel) timerInterval.getModel()).getNumber().intValue();
    }
    
    /**
     * Disable the control buttons of this frame. The biotope-simulation is stopped.
     */
    public void disableButtons() {
        newBiotope.setEnabled(false);
        nextRound.setEnabled(false);
        numberOfRounds.setEnabled(false);
        severalRounds.setEnabled(false);
        unlimitedRounds.setEnabled(false);
        stopRounds.doClick();
        stopRounds.setEnabled(false);
    }
    
    /**
     * Enable the control buttons of this frame. The biotope-simulation is stopped.
     */
    public void enableButtons() {
        newBiotope.setEnabled(true);
        nextRound.setEnabled(true);
        numberOfRounds.setEnabled(true);
        severalRounds.setEnabled(true);
        unlimitedRounds.setEnabled(true);
        stopRounds.setEnabled(true);
    }
    
    /**
     * Get the combined size of all components preferred sizes plus 50 extra pixels in width and height.
     */
    public Dimension combinedSize() {
        BorderLayout layout = (BorderLayout) this.getContentPane().getLayout();
        Dimension centerDim = layout.getLayoutComponent(BorderLayout.CENTER).getPreferredSize();
        Dimension topDim = layout.getLayoutComponent(BorderLayout.NORTH).getPreferredSize();
        Dimension leftDim = layout.getLayoutComponent(BorderLayout.WEST).getPreferredSize();
        Dimension rightDim = layout.getLayoutComponent(BorderLayout.EAST).getPreferredSize();
        Dimension bottomDim = layout.getLayoutComponent(BorderLayout.SOUTH).getPreferredSize();
        
        int width = 50 + Math.max(leftDim.width + centerDim.width + rightDim.width, Math.max(topDim.width, bottomDim.width));
        int height = 50 + Math.max(topDim.height + centerDim.height + bottomDim.height, Math.max(leftDim.height, rightDim.height));
        return new Dimension(width, height);
    }
    
    /**
     * A Dialog
     *
     */
    public class BiotopeCreator extends JFrame {
        private final JSpinner widthSpinner, heightSpinner;
        private final JSpinner breedingProbabilitySpinner;
        private final JSpinner maxHitpointsSpinner, initialHitpointsSpinner, hitpointsPerRoundSpinner;
        private final JSpinner minMaxAgeSpinner, maxMaxAgeSpinner;
        private final JSpinner finchesPerBehaviorSpinner;
        private final JCheckBox[] behaviorCheckboxes;
        private final JButton okButton, cancelButton;
        private final JButton setVariantOneButton, setVariantTwoButton, setVariantThreeButton;
        
        private BiotopeCreator(GalapagosFrame frame) {
            //Enables GalapagosFrame buttons when the dialog is closed
            this.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {enableButtons();}
            });
            
            // The world size options.
            JPanel sizeOptionGroup = new JPanel(new GridBagLayout());
            sizeOptionGroup.setBorder(BorderFactory.createTitledBorder("World size"));
            widthSpinner = newIntegerSpinner(100, 10, 1);
            heightSpinner = newIntegerSpinner(100, 10, 1);
            sizeOptionGroup.add(new JLabel("Width",SwingConstants.CENTER), getComponentConstraints(0,0));
            sizeOptionGroup.add(widthSpinner, getComponentConstraints(0,1));
            sizeOptionGroup.add(new JLabel("Height",SwingConstants.CENTER), getComponentConstraints(0,2));
            sizeOptionGroup.add(heightSpinner, getComponentConstraints(0,3));
            
            // Hitpoint options.
            JPanel hitpointsOptionGroup = new JPanel(new GridBagLayout());
            hitpointsOptionGroup.setBorder(BorderFactory.createTitledBorder("Finch hitpoints"));
            initialHitpointsSpinner = newIntegerSpinner(5, 1, 1);
            maxHitpointsSpinner = newIntegerSpinner(10, 1, 1);
            hitpointsPerRoundSpinner = newIntegerSpinner(3, 1, 0);
            hitpointsOptionGroup.add(new JLabel("Initial hitpoints",SwingConstants.CENTER), getComponentConstraints(0,0));
            hitpointsOptionGroup.add(initialHitpointsSpinner, getComponentConstraints(0,1));
            hitpointsOptionGroup.add(new JLabel("Max. hitpoints",SwingConstants.CENTER), getComponentConstraints(0,3));
            hitpointsOptionGroup.add(maxHitpointsSpinner, getComponentConstraints(0,4));
            hitpointsOptionGroup.add(new JLabel("Hitpoints lost per round",SwingConstants.CENTER), getComponentConstraints(0,5));
            hitpointsOptionGroup.add(hitpointsPerRoundSpinner, getComponentConstraints(0,6));
            
            // Age options.
            JPanel ageOptionGroup = new JPanel(new GridBagLayout());
            ageOptionGroup.setBorder(BorderFactory.createTitledBorder("Finch age"));
            minMaxAgeSpinner = newIntegerSpinner(10, 1, 2);
            maxMaxAgeSpinner = newIntegerSpinner(20, 1, 2);
            ageOptionGroup.add(new JLabel("Least maximum age",SwingConstants.CENTER), getComponentConstraints(0,0));
            ageOptionGroup.add(minMaxAgeSpinner, getComponentConstraints(0,1));
            ageOptionGroup.add(new JLabel("Greatest maximum age",SwingConstants.CENTER), getComponentConstraints(0,2));
            ageOptionGroup.add(maxMaxAgeSpinner, getComponentConstraints(0,3));
                   
            // Breeding probability and Finches per Behavior.
            JPanel otherOptionGroup = new JPanel(new GridBagLayout());
            otherOptionGroup.setBorder(BorderFactory.createTitledBorder("Other parametres"));
            breedingProbabilitySpinner = new JSpinner(new RevisedSpinnerNumberModel(0.33,0.0,1.0,0.01));
            breedingProbabilitySpinner.setPreferredSize(new Dimension(50,22));
            finchesPerBehaviorSpinner = newIntegerSpinner(30, 1, 0);
            otherOptionGroup.add(new JLabel("Breeding probability",SwingConstants.CENTER), getComponentConstraints(0,0));
            otherOptionGroup.add(breedingProbabilitySpinner, getComponentConstraints(0,1));
            otherOptionGroup.add(new JLabel("Finches per behavior",SwingConstants.CENTER), getComponentConstraints(0,2));
            otherOptionGroup.add(finchesPerBehaviorSpinner, getComponentConstraints(0,3));
            
            // Behavior selection.
            JPanel behaviorsOptionGroup = new JPanel(new GridBagLayout());
            behaviorsOptionGroup.setBorder(BorderFactory.createTitledBorder("Behaviors"));
            behaviorCheckboxes = new JCheckBox[GalapagosFrame.this.behaviors.size()];
            for (int i = 0; i < behaviorCheckboxes.length; i++) {
                behaviorCheckboxes[i] = new JCheckBox(GalapagosFrame.this.behaviors.get(i).toString(),true);
                behaviorsOptionGroup.add(behaviorCheckboxes[i],getComponentConstraints(i / 10, i % 10));
            }
            
            // OK and CANCEL.
            JPanel buttonPanel = new JPanel(new FlowLayout());
            okButton = new JButton("Genesis!");
            okButton.setActionCommand("okButton");
            okButton.addActionListener(controller);
            cancelButton = new JButton("Abort creation");
            cancelButton.setActionCommand("cancelButton");
            cancelButton.addActionListener(controller);
            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton);

            JPanel standardSettingsPanel = new JPanel(new FlowLayout());
            setVariantOneButton = new JButton("Set variant #1");
            setVariantOneButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setConfiguration(1.0/6.0, 3, 20, 7, 10, 13, 40);
                        selectRandomBehaviors(5);
                    }
                });
            setVariantTwoButton = new JButton("Set variant #2");
            setVariantTwoButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setConfiguration(1.0/6.0, 4, 20, 10, 20, 23, 40);
                        selectRandomBehaviors(5);
                    }
                });
            setVariantThreeButton = new JButton("Set variant #3");
            setVariantThreeButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setConfiguration(1.0/12.0, 3, 20, 13, 11, 14, 40);
                        selectRandomBehaviors(5);
                    }
                });
            standardSettingsPanel.add(setVariantOneButton);
            standardSettingsPanel.add(setVariantTwoButton);
            standardSettingsPanel.add(setVariantThreeButton);

            Container bottomButtonsContainer = Box.createVerticalBox();
            bottomButtonsContainer.add(standardSettingsPanel);
            bottomButtonsContainer.add(buttonPanel);
            
            JPanel options = new JPanel(new GridBagLayout());
            options.add(sizeOptionGroup, getContainerConstraints(0,0,1,1));
            options.add(hitpointsOptionGroup, getContainerConstraints(0,1,1,1));
            options.add(ageOptionGroup, getContainerConstraints(1,0,1,1));
            options.add(otherOptionGroup, getContainerConstraints(1,1,1,1));
            options.add(behaviorsOptionGroup, getContainerConstraints(2,0,1,2));
            
            this.setLayout(new BorderLayout());
            this.add(options, BorderLayout.CENTER);
            this.add(bottomButtonsContainer, BorderLayout.SOUTH);
            this.setTitle("Biotope Creator");
            
            this.setSize(getPreferredSize().width + 20, getPreferredSize().height + 40);
        }

        private void selectRandomBehaviors(int count) {
            assert (count <= behaviorCheckboxes.length)
                : "More behaviors required than is available";
            LinkedList<Integer> list = new LinkedList<Integer>();
            for (Integer i = 0; i < behaviorCheckboxes.length; i++)
                list.add(i);
            Collections.shuffle(list);
            for (JCheckBox box : behaviorCheckboxes)
                box.setSelected(false);
            for (int i = 0; i < 5; i++)
                behaviorCheckboxes[list.get(i)].setSelected(true);
        }

        private void setConfiguration(double breedingProbability, int roundPrice, 
                                      int maxHitpoints, int startHitpoints,
                                      int minMaxAge, int maxMaxAge,
                                      int initialFinchesPerBehavior) {
            breedingProbabilitySpinner.setValue(breedingProbability);
            maxHitpointsSpinner.setValue(maxHitpoints);
            initialHitpointsSpinner.setValue(startHitpoints);
            hitpointsPerRoundSpinner.setValue(roundPrice);
            minMaxAgeSpinner.setValue(minMaxAge);
            maxMaxAgeSpinner.setValue(maxMaxAge);
            finchesPerBehaviorSpinner.setValue(initialFinchesPerBehavior);
        }
        
        /**
         * Create a new spinner with a startValue, stepSize and minValue.
         * The max value is set to Integer.MAX_VALUE.
         * @param startValue The spinner's start-value.
         * @param stepSize The step size of the spinner.
         * @return The created spinner.
         */
        private JSpinner newIntegerSpinner(int startValue, int stepSize, int minValue)
        {
            JSpinner spinner = new JSpinner(new RevisedSpinnerNumberModel(startValue, minValue, Integer.MAX_VALUE, stepSize));
            spinner.setPreferredSize(standardSpinnerSize);
            
            return spinner;
        }

        public void createBiotope() {
            //Get the user input from the spinners.
            int width = (Integer) this.widthSpinner.getValue();
            int height = (Integer) this.heightSpinner.getValue();
            double breedingProbability = (Double) this.breedingProbabilitySpinner.getValue();
            int maxHitpoints = (Integer) this.maxHitpointsSpinner.getValue();
            int initialHitpoints = (Integer) this.initialHitpointsSpinner.getValue();
            int hitpointsPerRound = (Integer) this.hitpointsPerRoundSpinner.getValue();
            int minMaxAge = (Integer) this.minMaxAgeSpinner.getValue();
            int maxMaxAge = (Integer) this.maxMaxAgeSpinner.getValue();
            int finchesPerBehavior = (Integer) this.finchesPerBehaviorSpinner.getValue();
            
            //Get a list of the users chosen finches.
            List<Behavior> finchBehaviors = new LinkedList<Behavior>();
            for(int i = 0; i < behaviors.size(); ++i)
                if(behaviorCheckboxes[i].isSelected())
                    finchBehaviors.add(behaviors.get(i).clone());
            
            //Check that the values are legal
            if (checkStartFinches(width, height, finchesPerBehavior, finchBehaviors.size()) 
                    & checkAge(minMaxAge, maxMaxAge) & checkHitpoints(maxHitpoints, initialHitpoints)) {

                biotope = new Biotope(width,height,breedingProbability,
                                      maxHitpoints,initialHitpoints,hitpointsPerRound,minMaxAge,
                                      maxMaxAge,finchesPerBehavior,finchBehaviors);
                
                biotope.addObserver(statistics);
                if (isLogging)
                    biotope.addObserver(logger);
                if (isRefreshing)
                    biotope.addObserver(GalapagosFrame.this);

                //Create RadioButton's on the GalapagosFrame
                //for the spawning-tool
                behaviorButtons = new ButtonGroup();
                behaviorButtonsBox.removeAll();
                behaviorButtonsBox.add(Box.createGlue());
                behaviorButtonsBox.add(behaviorButtonsLabel);

                for (final Behavior b : finchBehaviors) {
                    JRadioButton button = new JRadioButton(b.toString());
                    button.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                selectedBehavior = b;
                            }
                        });
                    behaviorButtons.add(button);
                    behaviorButtonsBox.add(button);
                }

                behaviorButtonsBox.add(Box.createGlue());

                selectedBehavior = null;
                controller.setBiotope(biotope);
                area.reset(biotope.world.width(), biotope.world.height(), pixelSize);
                biotope.doNotifyObservers();
                
                GalapagosFrame.this.setSize(combinedSize());
                GalapagosFrame.this.validate();
                close();
            }
        }
        
        /**
         * Show the BiotopeCreator screen.
         */
        public void openPanel() {
            disableButtons();
            this.setVisible(true);
        }
        
        /**
         * Close the BiotopeCreator screen.
         */
        public void close() {
            enableButtons();
            setVisible(false);
        }
        
        /**
         * Check that user-chosen world is big enough for the number of finches that should be spawned.
         * Shows a messagebox if the values are illegal.
         * @param width The width of the world.
         * @param height The height of the world.
         * @param finchesPerBehavior How many finches that should be spawned per behavior.
         * @param numberOfBehaviors How many different behaviors the user has chosen.
         * @return true if the values are legal.
         */
        public boolean checkStartFinches(int width, int height, int finchesPerBehavior, int numberOfBehaviors) {
            if (width * height >= finchesPerBehavior * numberOfBehaviors) {
                return true;
            } else {
                JOptionPane.showMessageDialog(this,
                        "There is not enough room in the world for the initial amount of finches.",
                        "Impossible to create biotope", JOptionPane.WARNING_MESSAGE);
                return false;            
                
            }
        }
        
        /**
         * Check that the user-chosen age values are legal and show a messagebox if not.
         * (The minimum age must be smaller than the maximum age)
         * Shows a messagebox if the values are illegal
         * @param minMaxAge The user-chosen minimum age.
         * @param maxMaxAge The user-chosen maximum age.
         * @return true if the values are legal.
         */
        public boolean checkAge(int minMaxAge, int maxMaxAge) {
            if (minMaxAge <= maxMaxAge) {
                return true;
            } else {
                JOptionPane.showMessageDialog(this,
                        "The greatest maximum age must be at least as large as the least maximum age.",
                        "Impossible to create biotope", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }
        
        /**
         * Check that the user-chosen hitpoint values are legal.
         * (The finches can't start with more hitpoints than the maximum) 
         * Shows a messagebox if they are illegal.
         * @ensure (initialHitpoints <= maxHitpoints) ? true : false
         * @param maxHitpoints The user-chosen maximum hitpoints-value.
         * @param initialHitpoints The user-chosen initial hitpoints-value.
         * @return true if the values are legal.
         */
        public boolean checkHitpoints(int maxHitpoints, int initialHitpoints) {
            if (initialHitpoints <= maxHitpoints) {
                return true;
            } else {
                JOptionPane.showMessageDialog(this,
                        "The initial amount of hitpoints may at most be the maximum amount of hitpoints.",
                        "Impossible to create biotope", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }
    }
}
