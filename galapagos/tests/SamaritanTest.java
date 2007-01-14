package galapagos.tests;

import galapagos.*;


public class SamaritanTest extends BehaviorTest {
    public void testDecide()
    {
        assertEquals(behavior.decide(opponent), Action.CLEANING);
        
        //should also return CLEANING after response()-calls
        behavior.response(opponent, Action.CLEANING);
        assertEquals(behavior.decide(opponent), Action.CLEANING);
        
        behavior.response(opponent, Action.IGNORING);
        assertEquals(behavior.decide(opponent), Action.CLEANING);
    }
    
    public Behavior getBehavior() {
        return new Samaritan();
    }
}
