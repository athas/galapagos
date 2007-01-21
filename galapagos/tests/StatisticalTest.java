package galapagos.tests;

import galapagos.behaviors.*;
import galapagos.biotope.*;

/**
 * Test that the Statistical behavior works properly.
 *
 * The decision-test only makes sure that no exceptions are
 * thrown. This is because it uses statistical and probabilistic evaluations
 * that make it hard to determine how the behavior reacts in specific
 * situations. 
 * Also, we do not believe that it makes sense to test the decision-making 
 * aspect of the behavior, as the determination algorithm is not specified, 
 * but an implementation detail.
 */
public class StatisticalTest extends BehaviorTest {
	/**
	 * The decision-test only makes sure that no exceptions are
	 * thrown. This is because it uses statistical and probabilistic evaluations
	 * that make it hard to determine how the behavior reacts in specific
	 * situations. 
	 * Also, we do not believe that it makes sense to test the decision-making 
	 * aspect of the behavior, as the determination algorithm is not specified, 
	 * but an implementation detail.
	 */
    public void testDecide()
    {
    	try {
        behavior.decide(opponent);
        behavior.response(opponent, Action.IGNORING);
        behavior.decide(opponent);
        behavior.response(opponent, Action.IGNORING);
        behavior.decide(opponent);
        behavior.response(opponent, Action.CLEANING);
        behavior.decide(opponent);
        behavior.response(opponent, Action.CLEANING);
        behavior.decide(opponent);
        behavior.response(opponent, Action.IGNORING);
        behavior.decide(opponent);
        behavior.response(opponent, Action.CLEANING);
        behavior.decide(opponent);
        behavior.response(opponent, Action.IGNORING);
        behavior.decide(opponent);
        behavior.response(opponent, Action.IGNORING);
        behavior.decide(opponent);
        behavior.response(opponent, Action.IGNORING);
        behavior.decide(opponent);
        behavior.response(opponent, Action.IGNORING);
        behavior.decide(opponent);
        behavior.response(opponent, Action.CLEANING);
        behavior.decide(opponent);
        behavior.response(opponent, Action.CLEANING);
        behavior.decide(opponent);
    	} catch(Exception e) {
    		fail(e.getMessage());
    	}
    	
    }

    public Behavior getBehavior() {
        return new Statistical();
    }

    public String behaviorName () {
    	return "Statistical";
    }
}
