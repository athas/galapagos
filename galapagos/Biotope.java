package galapagos;

import java.util.*;

public class Biotope extends Observable {
    private int width, height;
    private double breedingProbability;
    private int maxHitpoints, initialHitpoints;
    private int minMaxAge, maxMaxAge;
    private int finchesPerBehavior;
    private int round;
    private World<Finch> world;
    private List<Behavior> behaviors;
    private List<Integer> population;
    private List<Integer> born;
    private List<Integer> deadByAge;
    private List<Integer> deadByTicks;
    
    public Biotope () {
        
    }
    
    public Biotope (int width, int height /*eller Dimension*/, double breedingProbability, int maxHitpoints, 
            int initialHitpoints, int minMaxAge, int maxMaxAge, int finchesPerBehavior, List<Behavior> behaviors) {
        
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
        
    }
    
    private void makeMeetings () {
        
    }
    
    private void meeting (GalapagosFinch finch1, GalapagosFinch finch2) {
        
    }
    
    private void grimReaper () {
        
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
