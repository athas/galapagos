package galapagos.tests;

import galapagos.*;

/**
 * Test of the Lenient Grudger behavior.
 */
public class LenientGrudgerTest extends BehaviorTest {
    public void testDecide() {
        assertEquals(behavior.decide(opponent), Action.CLEANING);
        assertEquals(behavior.decide(opponent2), Action.CLEANING);
        
        //should clean finches that cleans it
        for(int i = 0; i < 12; i++) {
            behavior.response(opponent, Action.CLEANING);
            assertEquals(behavior.decide(opponent), Action.CLEANING);
            
            behavior.response(opponent2, Action.CLEANING);
            assertEquals(behavior.decide(opponent2), Action.CLEANING);
        }
        
        // Should ignore the first ignorance.
        behavior.response(opponent, Action.IGNORING);
        
        for(int i = 0; i < 12; i++) {
            behavior.response(opponent, Action.CLEANING);
            assertEquals(behavior.decide(opponent), Action.CLEANING);
            
            behavior.response(opponent2, Action.CLEANING);
            assertEquals(behavior.decide(opponent2), Action.CLEANING);
        }
        
        // But again! What insolence! It is no friend of the Lenient
        // Grudger any more.
        behavior.response(opponent, Action.IGNORING);
        
        for(int i = 0; i < 12; i++) {
            behavior.response(opponent, Action.CLEANING);
            assertEquals(behavior.decide(opponent), Action.IGNORING);
            
            //should continue cleaning nice opponents
            behavior.response(opponent2, Action.CLEANING);
            assertEquals(behavior.decide(opponent2), Action.CLEANING);
        }
    }
    
    public Behavior getBehavior() {
        return new LenientGrudger();
    }
    
    public String behaviorName () {
    	return "Lenient Grudger";
    }
}
