package galapagos;

/**
 * A behavior implementing two-bit prediction.
 */
public class Predictor extends MemoryBehavior<Predictor.Prediction> {
    private static final String DESCRIPTION = 
        "<HTML>Tries to predict the other finch's next<br>" +
        "move using two-bit prediction.</HTML>";
    
    /**
     * @inheritDoc
     */
    public String description() {
        return DESCRIPTION;
    }
    
    /**
     * The prediction for the behavior of a finch.
     */
    protected enum Prediction {STRONG_IGNORE, WEAK_IGNORE, STRONG_CLEAN, WEAK_CLEAN}
    
    /**
     * If a finch just met ignores this lenient grudger finch twice it
     * should be remembered.
     */
    public void response(Finch finch, Action action) {
        Prediction prediction = recall(finch);
        if (prediction == null)
            remember(finch, action == Action.IGNORING ? 
                     Prediction.STRONG_IGNORE :
                     Prediction.STRONG_CLEAN);
        else {
            if (prediction == Prediction.STRONG_IGNORE &&
                action == Action.CLEANING)
                remember(finch, Prediction.WEAK_IGNORE);
            else if (prediction == Prediction.WEAK_IGNORE && 
                     action == Action.CLEANING)
                remember(finch, Prediction.STRONG_CLEAN);
             else if (prediction == Prediction.WEAK_IGNORE &&
                      action == Action.IGNORING)
                 remember(finch, Prediction.STRONG_IGNORE);
            else if (prediction == Prediction.STRONG_CLEAN && 
                     action == Action.IGNORING)
                remember(finch, Prediction.WEAK_CLEAN);
            else if (prediction == Prediction.WEAK_CLEAN && 
                     action == Action.IGNORING)
                remember(finch, Prediction.STRONG_IGNORE);
             else if (prediction == Prediction.WEAK_CLEAN && 
                      action == Action.CLEANING)
                 remember(finch, Prediction.STRONG_CLEAN);
        }
    }
    
    /**
     * What a predictor should do to a finch it doesn't know.
     */
    protected Action defaultAction() {
        return Action.CLEANING;
    }

    public Action decide(Finch finch) {
        Prediction prediction = recall(finch);

        if (prediction != null) {
            if (prediction == Prediction.STRONG_IGNORE || 
                prediction == Prediction.WEAK_IGNORE ||
                prediction == Prediction.STRONG_CLEAN)
                return Action.IGNORING;
            if (prediction == Prediction.WEAK_CLEAN)
                return Action.CLEANING;
        }
        
        return defaultAction();
    }
    
    /**
     * @inheritDoc
     */
    public Behavior clone() {
        return new Predictor();
    }

    /**
     * @inheritDoc
     */
    public String toString() {
        return "Predictor";
    }
    
    /**
     * @inheritDoc
     */
    public final boolean equals(Object obj) {
    	return (obj instanceof Predictor);
    }
    
    /**
     * @inheritDoc
     */
    public int hashCode() {
    	return toString().hashCode();
    }
}