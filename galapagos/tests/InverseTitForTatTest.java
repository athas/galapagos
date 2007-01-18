package galapagos.tests;

import galapagos.*;

public class InverseTitForTatTest extends BehaviorTest {
	public void testDecide()
    {
        assertEquals(behavior.decide(opponent), Action.CLEANING);
        behavior.response(opponent, Action.CLEANING);
        // Should do the opposite ofwhat opponent did last time.
        assertEquals(behavior.decide(opponent), Action.IGNORING);
        behavior.response(opponent, Action.IGNORING);
        assertEquals(behavior.decide(opponent), Action.CLEANING);
        behavior.response(opponent, Action.IGNORING);
        assertEquals(behavior.decide(opponent), Action.CLEANING);
        behavior.response(opponent, Action.CLEANING);
        assertEquals(behavior.decide(opponent), Action.IGNORING);
    }
    
    public Behavior getBehavior() {
        return new InverseTitForTat();
    }
    

    public void testToString () {
    	assertEquals(behavior.toString(),"Inverse Tit for Tat");
    }
}
