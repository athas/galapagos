package galapagos;

import java.util.*;

public class Biotope extends Observable {
    private int width, height;
    private double breedingProbability;
    private int maxHitpoints, initialHitpoints, hitpointsPerRound;
    private int minMaxAge, maxMaxAge;
    private int finchesPerBehavior;
    private int round;
    private World<GalapagosFinch> world;
    private TreeMap<String,Statistics> statisticsTree;
    
    /*private List<Behavior> behaviors;
    private List<Integer> population;
    private List<Integer> born;
    private List<Integer> deadByAge;
    private List<Integer> deadByTicks;
    
    public Biotope () {
        
    }
    
    public Biotope (int width, int height /*eller Dimension*/, double breedingProbability, int maxHitpoints, 
            int initialHitpoints, int hitpointsPerRound, int minMaxAge, int maxMaxAge,
            int finchesPerBehavior, List<Behavior> behaviors) {
        
    }
    
    /**
     * Do initialization of objects common to all constructors.
     */
    private void initialize () {
        engagedFinches = new ArrayList(width * height);
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
                    world.setAt(neighbours.get(0).xPosition(), neighbours.get(0).yPosition(), 
                            new GalapagosFinch(initialHitpoints, maxAge, finch.behavior().clone()));
                }
            }
        }
    }
    
    private void makeMeetings () {
        
    }
    
    private void meeting (GalapagosFinch finch1, GalapagosFinch finch2) {
        
    }
    
    private void grimReaper () {
        /*for (p : world) if (p.element != null) {
            GalapagosFinch f = (GalapagosFinch)p.element;
            f.addHitpoints(-hitpointsPerRound);
            if (f.status != FinchStatus.ALIVE) {
                String behaviorString = f.behavior.toString();
                Statistics s = statistics.get(behaviorString);
          */      
            
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
