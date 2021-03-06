package galapagos.behaviors;

import galapagos.biotope.*;

import java.util.*;

/**
 * Every behavior that sometimes uses information about
 * finches it met earlier, extends this class.
 * @param <DATA> The type of data the behavior needs to store.
 */
public abstract class MemoryBehavior<DATA> implements Behavior {
    private final Map<Finch, DATA> finches;
    
    /**
     * Constructor for MemoryBehavior.
     * @ensure {@code this.recall(finch) == null} for any {@code Finch}.
     */
    public MemoryBehavior() {
        //The garbage collector removes items from WeakHashMap's
        //if there is no other references to them in the program.
        finches = new WeakHashMap<Finch, DATA>();
    }
    
    /**
     * Remember a finch and with some data about it.
     * @param finch The finch to remember.
     * @param data The data to store with the finch.
     */
    protected void remember(Finch finch, DATA data) {
        finches.put(finch, data);
    }
    
    /**
     * The data associated with a given finch is returned - if there is any.
     * null is returned if the finch wasn't found.
     *  
     * @param finch The finch to look up in the memory.
     * @return The data associated with the given finch or null.
     */
    protected DATA recall(Finch finch) {
        return finches.get(finch);
    }
    
    /**
     * @inheritDoc
     */
    public abstract Behavior clone();
}
