package galapagos;

import java.util.*;
import java.lang.*;

public class Biotope extends Observable {
    private int width, height;
    private double breedingProbability;
    private int maxHitpoints, initialHitpoints, hitpointsPerRound;
    private int minMaxAge, maxMaxAge;
    private int numberOfBehaviors;
    private int finchesPerBehavior;
    private int round;
    private World<GalapagosFinch> world;
    private TreeMap<String,Statistics> statisticsTree;
    
    /*private List<Behavior> behaviors;
    private List<Integer> population;
    private List<Integer> born;
    private List<Integer> deadByAge;
    private List<Integer> deadByTicks;*/

    private Random random;

    private final static int HelpedGotHelpValue = 3;
    private final static int HelpedDidntGetHelpValue = 0;
    private final static int DidntHelpGotHelpValue = 5;
    private final static int DidntHelpDidntGetHelpValue = 1;
    private ArrayList<Boolean> engagedFinches;
    
    public Biotope () {
        width = 300;
        height = 200;
        initialHitpoints = 7;
        minMaxAge = 10;
        maxMaxAge = 13;
        finchesPerBehavior = 40;
        initialize();
    }
    
    public Biotope (int width, int height /*eller Dimension*/, double breedingProbability, int maxHitpoints, 
            int initialHitpoints, int hitpointsPerRound, int minMaxAge, int maxMaxAge,
            int finchesPerBehavior, List<Behavior> behaviors) {
        throw new Error("Unimplemented");
        //initialize();
    }
    
    /**
     * Do initialization of objects common to all constructors.
     */
    private void initialize () {
        numberOfBehaviors = 5;
        engagedFinches = new ArrayList(width * height);
        world = new World<GalapagosFinch>(width, height);

        for (int bcounter = 0; bcounter < numberOfBehaviors; bcounter++)
            for (int fcounter = 0; fcounter < finchesPerBehavior; fcounter++)
                addRandomFinch(new Samaritan());
    }
    
    private void addRandomFinch (Behavior behavior) {
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);

        if (world.getAt(x, y).element() == null) {
            world.setAt(x, y, new GalapagosFinch(initialHitpoints,
                                                 minMaxAge + (int)
                                                 (Math.random() *
                                                  (maxMaxAge - minMaxAge)),
                                                 behavior));
        } else addRandomFinch(behavior);
    }


    /* OR:
    public void setWorldSize (int width, int height) {
        
    }
    
    public void setBreedingProbability (double prob) {
        
    }
    
    public void setMaxHitpoints (int maxHitpoints) {
        
    }
    
    public void setInitialHitpoints (int initialHitpoints) {
        
    }
    
    public void setMinMaxAge (int minMaxAge) {
        
    }
    
    public void setMaxMaxAge (int maxMaxAge) {
        
    }
    
    public void setFinchesPerBehavior (int finchesPerBehavior) {
        
    }
    
    public void setBehaviors (List<Behavior> behaviors) {
        
    }
    */
    
    public void runRound () {
        
    }
    
    private void breed () {
        for (Iterator<World<GalapagosFinch>.Place> i = world.randomIterator(); i.hasNext();) {
            World<GalapagosFinch>.Place place = i.next();
            GalapagosFinch finch = place.element();
            if (finch != null && finch.age() > 0) {
                List<World<GalapagosFinch>.Place> neighbours = place.emptyNeighbours();
                if (!neighbours.isEmpty()) {
                    int maxAge = random.nextInt(maxMaxAge - minMaxAge) + minMaxAge;
                    // world.setAt(neighbours.get(0).xPosition(), neighbours.get(0).yPosition(), 
//                                 new GalapagosFinch(initialHitpoints, maxAge, finch.behavior().clone()));
                }
            }
        }
    }
    
    private void makeMeetings () {
        
    }
    
    private void meeting (GalapagosFinch finch1, GalapagosFinch finch2) {
        
    }
    
    /**
     * Decrease the hitpoints of all finches by hitpointsPerRound, and find dead finches
     * and remove them from world, and store the changes in statisticsTree.
     */
    private void grimReaper () {
        for (World<GalapagosFinch>.Place p : world) if (p.element() != null) {
            GalapagosFinch f = p.element();
            f.addHitpoints(-hitpointsPerRound);
            FinchStatus newStatus = f.status();
            if (newStatus != FinchStatus.ALIVE) {
                Statistics s = statisticsTree.get(f.behavior().toString());
                if (newStatus == FinchStatus.DEAD_AGE)
                    s.incDeadByAge();
                    else s.incDeadByTicks();
                s.decPopulation();
                world.setAt(p.xPosition(),p.yPosition(),null);
            }
        }
    }
    
    public List<Integer> population () {
        return null;
    }
    
    public List<Integer> born () {
        return null;
    }
    
    public List<Integer> deadByAge () {
        return null;
    }
    
    public List<Integer> deadByTicks () {
        return null;
    }
    
    public int round () {
        return 0;
    }
    
    public Behavior behavior (int row, int column) {
        return null;
    }
    // OR
    public World<GalapagosFinch> world () {
        return null;
    }
}
