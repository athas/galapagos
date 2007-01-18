package galapagos;

/**
 * This is a more concrete MemoryBehavior which only stores a single action with each remembered finch.
 * The action stored is that action we want to do to the finch next time we meet it. 
 * It has a default action which is used when nothing is remembered about the finch.
 */
public abstract class ActionMemoryBehavior extends MemoryBehavior<Action> {

    public ActionMemoryBehavior() {
        super();
    }

    /**
     * Choose which action to do the given finch.
     * If it is remembered what action the finch chose earlier at som time,
     * this behavior should choose the same action now.
     * 
     * @ensure <code>(recall(finch) == null) ? defaultAction() : recall(finch)</code>
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
