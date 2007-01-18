package galapagos.tests;

import galapagos.*;

public class SuspiciousTitForTatTest extends BehaviorTest {
	public void testDecide()
    {
		assertEquals(behavior.decide(opponent), Action.IGNORING);
		behavior.response(opponent, Action.CLEANING);
		// Should do what opponent did last time.
		assertEquals(behavior.decide(opponent), Action.CLEANING);
		behavior.response(opponent, Action.IGNORING);
        assertEquals(behavior.decide(opponent), Action.IGNORING);
        behavior.response(opponent, Action.IGNORING);
        assertEquals(behavior.decide(opponent), Action.IGNORING);
        behavior.response(opponent, Action.CLEANING);
        assertEquals(behavior.decide(opponent), Action.CLEANING);
    }
    
    public Behavior getBehavior() {
        return new SuspiciousTitForTat();
    }
    

    public void testToString () {
    	assertEquals(behavior.toString(),"Suspicious Tit for Tat");
    }
}
