package galapagos.tests;

import galapagos.*;


public class LenientGrudgerTest extends BehaviorTest {
    public void testDecide()
    {
        assertEquals(behavior.decide(opponent), Action.CLEANING);
        
        for(int i = 0; i < 12; i++)
        {
            behavior.response(opponent, Action.CLEANING);
            assertEquals(behavior.decide(opponent), Action.CLEANING);
        }
        
        // Should ignore the first ignorance.
        behavior.response(opponent, Action.IGNORING);
        
        for(int i = 0; i < 12; i++)
        {
            behavior.response(opponent, Action.CLEANING);
            assertEquals(behavior.decide(opponent), Action.CLEANING);
        }
        
        // But again! What insolence! It is no friend of the Lenient
        // Grudger any more.
        behavior.response(opponent, Action.IGNORING);
        
        for(int i = 0; i < 12; i++)
        {
            behavior.response(opponent, Action.CLEANING);
            assertEquals(behavior.decide(opponent), Action.IGNORING);
        }
    }
    
    public Behavior getBehavior() {
        return new LenientGrudger();
    }
    

    public void testToString () {
    	assertEquals(behavior.toString(),"Lenient Grudger");
    }
}
