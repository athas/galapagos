package galapagos.tests;

import galapagos.biotope.*;
import galapagos.behaviors.SelfCentric;

/**
 * Test of the SelfCentric behavior.
 */
public class SelfCentricTest extends BehaviorTest {

	/**
	 * Tests that the SelfCentric behavior uses the start pattern:
	 * first ignore then clean.
	 * And tests that opponents that doesn't use this pattern is ignored.
	 * Also tests that the SelfCentric behavior makes a distinction
	 * between two different finches.
	 */
	public void testDecide() {
		assertEquals(Action.IGNORING, behavior.decide(opponent));
		
		//if the opponent also starts with ignoring then it could be SelfCentric too.
		behavior.response(opponent, Action.IGNORING);
		assertEquals(Action.CLEANING, behavior.decide(opponent));
		//if the second action is CLEANING then it think its SelfCentric 
		behavior.response(opponent, Action.CLEANING);
		assertEquals(Action.CLEANING, behavior.decide(opponent));
		behavior.response(opponent, Action.CLEANING);
		assertEquals(Action.CLEANING, behavior.decide(opponent));
		behavior.response(opponent, Action.IGNORING);
		assertEquals(Action.IGNORING, behavior.decide(opponent));
		behavior.response(opponent, Action.CLEANING);
		assertEquals(Action.IGNORING, behavior.decide(opponent));
		
		assertEquals(Action.IGNORING, behavior.decide(opponent2));
		behavior.response(opponent2, Action.IGNORING);
		assertEquals(Action.CLEANING, behavior.decide(opponent2));
		behavior.response(opponent2, Action.IGNORING);
		assertEquals(Action.IGNORING, behavior.decide(opponent2));
		behavior.response(opponent, Action.CLEANING);
		assertEquals(Action.IGNORING, behavior.decide(opponent));
	}

	public String behaviorName() {
		return "Self-Centric";
	}

	protected Behavior getBehavior() {
		return new SelfCentric();
	}
}
