package galapagos;

import java.util.*;

/**
 * Every behavior that sometimes uses information about
 * how it is treated earlier extends this class.
 */
public abstract class MemoryBehavior implements Behavior {
    private final Map<Finch,Action> finches;
    
    /**
     * A new MemoryBehavior.
     */
    public MemoryBehavior() {
        finches = new WeakHashMap<Finch,Action>();
    }
    
    /**
     * Remember the finch in the argument and what it did.
     */
    protected void add(Finch finch, Action action) {
        finches.put(finch, action);
    }
    
    /**
     * Choose which action to do to the finch in the argument.
     * If it is remembered what action the finch chose earlier at som time,
     * this behavior should choose the same action now.
     * 
     * @ensure finches.containsKey(finch) implies decide == finches.get(finch)
     * @ensure !finches.containsKey(finch) implies decide == defaultAction()
     */
    public Action decide(Finch finch) {
        if (finches.containsKey(finch))
            return finches.get(finch);
        else 
            return defaultAction();
    }
    
    /**
     * The action chosen when nothing about a given finch is remembered.
     */
    protected abstract Action defaultAction ();
    
    /**
     * A new instanse of this behavior.
     */
    public abstract Behavior clone();
}
