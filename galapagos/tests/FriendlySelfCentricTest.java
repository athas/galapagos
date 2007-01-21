package galapagos.tests;

import galapagos.biotope.*;
import galapagos.behaviors.FriendlySelfCentric;

public class FriendlySelfCentricTest extends BehaviorTest {

    /**
     * This method tests that the FriendlySelfCentric Behavior
     * follows the three meeting indentification pattern, without regard to
     * whether the opponent follows the pattern or not.
     * It is also testet that after the indentification pattern, an
     * opponent is only helped as long as it behaves as a FriendlySelfCentric.
     */
	public void testDecide() {
        // The initial three meetings should be CLEANING, IGNORING, IGNORING
        // even if the other finch does something different.
		assertEquals(Action.CLEANING, behavior.decide(opponent));
        behavior.response(opponent, Action.CLEANING);
        assertEquals(Action.IGNORING, behavior.decide(opponent));
        behavior.response(opponent, Action.IGNORING);
        assertEquals(Action.IGNORING, behavior.decide(opponent));
        behavior.response(opponent, Action.IGNORING);
        
        assertEquals(Action.CLEANING, behavior.decide(opponent2));
        behavior.response(opponent2, Action.CLEANING);
        assertEquals(Action.IGNORING, behavior.decide(opponent2));
        behavior.response(opponent2, Action.IGNORING);
        assertEquals(Action.IGNORING, behavior.decide(opponent2));
        behavior.response(opponent2, Action.CLEANING);
		
		// After the initial meeting the opponent should be ignored if 
        // it deviated from the pattern.
        assertEquals(Action.IGNORING, behavior.decide(opponent2));
        behavior.response(opponent2, Action.CLEANING);
        assertEquals(Action.IGNORING, behavior.decide(opponent2));
        behavior.response(opponent2, Action.CLEANING);
        assertEquals(Action.IGNORING, behavior.decide(opponent2));
        behavior.response(opponent2, Action.IGNORING);
        assertEquals(Action.IGNORING, behavior.decide(opponent2));
        
        // After the initial meetings the opponent should be cleaned
        // as long as it also cleans in return (if it stops cleaning,
        // it can't be a FriendlySelfCentric.
        assertEquals(Action.CLEANING, behavior.decide(opponent));
		behavior.response(opponent, Action.CLEANING);
		assertEquals(Action.CLEANING, behavior.decide(opponent));
		behavior.response(opponent, Action.CLEANING);
		assertEquals(Action.CLEANING, behavior.decide(opponent));
		behavior.response(opponent, Action.IGNORING);
		assertEquals(Action.IGNORING, behavior.decide(opponent));
		behavior.response(opponent, Action.CLEANING);
		assertEquals(Action.IGNORING, behavior.decide(opponent));
		behavior.response(opponent, Action.CLEANING);
		assertEquals(Action.IGNORING, behavior.decide(opponent));
	}

	public String behaviorName() {
		return "Friendly Self-Centric";
	}

	protected Behavior getBehavior() {
		return new FriendlySelfCentric();
	}
}
