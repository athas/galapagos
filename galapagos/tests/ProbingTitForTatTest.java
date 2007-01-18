package galapagos.tests;

import galapagos.*;

public class ProbingTitForTatTest extends BehaviorTest {
	public void testDecide()
    {
        assertEquals(behavior.decide(opponent), Action.CLEANING);
        behavior.response(opponent, Action.CLEANING);
        // Should do what opponent did last time.
        assertEquals(behavior.decide(opponent), Action.CLEANING);
        behavior.response(opponent, Action.IGNORING);
        assertEquals(behavior.decide(opponent), Action.IGNORING);
        behavior.response(opponent, Action.IGNORING);
        assertEquals(behavior.decide(opponent), Action.IGNORING);
        behavior.response(opponent, Action.CLEANING);
        assertEquals(behavior.decide(opponent), Action.CLEANING);
        
        // We cannot test 
    }
    
    public Behavior getBehavior() {
        return new ProbingTitForTat();
    }
    

    public void testToString () {
    	assertEquals(behavior.toString(),"Probing Tit for Tat");
    }
}
