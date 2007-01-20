package galapagos;

/**
 * This is a more concrete MemoryBehavior which only stores a single action with each remembered finch.
 * The action stored is the action we want to do to the finch next time we meet it. 
 * It has a default action which is used when nothing is remembered about the finch.
 */
public abstract class ActionMemoryBehavior extends MemoryBehavior<Action> {

    /**
     * The constructor for ActionMemoryBehavior.
     */
    public ActionMemoryBehavior() {
        super();
    }

    /**
     * Choose which action to do to the given finch.
     * If an Action is remembered for the given finch, that action i returned; 
     * if not, defaultAction() is returned.
     * 
     * @param finch The finch to decide action against.
     * @ensure {@code (recall(finch) == null) ? defaultAction() : recall(finch)}
     */
    public Action decide(Finch finch) {
        Action savedAction = recall(finch);
        
        if (savedAction == null)
            return defaultAction();
        else 
            return savedAction;
    }
    
    /**
     * The action chosen when nothing about a given finch is remembered.
     */
    protected abstract Action defaultAction ();
}
