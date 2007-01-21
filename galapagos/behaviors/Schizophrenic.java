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
    private int personalityChoice;
    
    /**
     * Create a new Schizophrenic Behavior.
     * @param name the name of the specific personality combination. Each combination
     * of Behaviors must be given different names (since {@code equals} depends
     * on the names to compare two Schizophrenic.
     * @param personalities the Behaviors that this Schizophrenic, chooses randomly
     * between each round. The personalities don't share any
     * memory of meetings.
     * @require {@code personalities.size() > 0}
     * @ensure {@code this.toString().equals(name)}
     */
    public Schizophrenic (String name, List<Behavior> personalities) {
        this.name = name;
        this.personalities = personalities;
        
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

    /**
     * Informs either the personality that decided the action this round, 
     * or all the personalities (depending on {@code sharedMemory} in the
     * contructor), of the opponent's action this round.
     */
    public void response(Finch finch, Action action) {
        personalities.get(personalityChoice).response(finch, action);
    }
    
    /**
     * @inheritDoc
     */
    public String toString() {
        return name;
    }
    
    /**
     * Returns true if {@code obj} is a Schizophrenic with the same name.
     */
    public boolean equals(Object obj) {
        return (obj instanceof Schizophrenic) && obj.toString().equals(name);
    }
    
    /**
     * @inheritDoc
     */
    public int hashCode() {
        return name.hashCode();
    }
    
    /**
     * A new Schizophrenic with the same name, the same personalities 
     * (made by cloning each of the Behaviors), shared personality memory if
     * and only if this Schizophrenic has shared personality memory.
     */
    public Behavior clone() {
        List<Behavior> clone = new ArrayList<Behavior>(personalities.size());
        for (Behavior personality : personalities)
            clone.add(personality.clone());
        return new Schizophrenic(name, clone);
    }
}
