package galapagos.behaviors;

import galapagos.biotope.*;
import java.util.List;
import java.util.ArrayList;

/**
 * A Behavior with multiple personalities.
 */
public class Schizophrenic implements Behavior {
    private final String name;
    private final String description;
    private final List<Behavior> personalities;
    private final boolean sharedMemory;
    private int personalityChoice;
    
    public Schizophrenic (String name, List<Behavior> personalities,
                          boolean sharedMemory) {
        this.name = name;
        this.personalities = personalities;
        this.sharedMemory = sharedMemory;
        
        String description = 
            "<HTML>A schizophrenic finch with the following personalities:";
        for (Behavior personality : personalities)
            description += "<br>" + personality;
        description += "</HTML>";
        
        this.description = description;
    }

    /**
     * Returns the Action from a random Behavior among this
     * Schizophrenic's behaviors.
     */
    public Action decide(Finch finch) {
        personalityChoice = (int) Math.random() * personalities.size();
        return personalities.get(personalityChoice).decide(finch);
    }

    public String description() {
        return description;
    }

    public void response(Finch finch, Action action) {
        if (sharedMemory) {
            for (Behavior personality : personalities)
                personality.response(finch, action);
        } else
            personalities.get(personalityChoice).response(finch, action);
    }
    
    public String toString() {
        return name;
    }
    
    public boolean equals(Object obj) {
        return (obj instanceof Schizophrenic) && obj.toString().equals(name);
    }
    
    public int hashCode() {
        return name.hashCode();
    }
    
    public Behavior clone() {
        List<Behavior> clone = new ArrayList<Behavior>(personalities.size());
        for (Behavior personality : personalities)
            clone.add(personality.clone());
        return new Schizophrenic(name, clone, sharedMemory);
    }
}
