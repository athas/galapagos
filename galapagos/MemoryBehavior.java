package galapagos;

import java.util.*;

public abstract class MemoryBehavior implements Behavior {
    private final Map<Finch,Action> finches;
  
    public MemoryBehavior() {
        finches = new WeakHashMap<Finch,Action>();
    }
    
    protected void add(Finch finch, Action action) {
        finches.put(finch, action);
    }
    
    public Action decide(Finch finch) {
        if (finches.containsKey(finch))
            return finches.get(finch);
        else 
            return defaultAction();
    }
    
    protected abstract Action defaultAction ();
    
    public abstract Behavior clone();
}
