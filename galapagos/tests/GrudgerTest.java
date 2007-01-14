package galapagos.tests;

import galapagos.*;


public class GrudgerTest extends BehaviorTest {
    public void testDecide()
    {
        assertEquals(behavior.decide(opponent), Action.CLEANING);
        
        for(int i = 0; i < 12; i++)
        {
            behavior.response(opponent, Action.CLEANING);
            assertEquals(behavior.decide(opponent), Action.CLEANING);
        }
        
        //should start ignoring when the "opponent" ignores it
        for(int i = 0; i < 12; i++)
        {
            behavior.response(opponent, Action.IGNORING);
            assertEquals(behavior.decide(opponent), Action.IGNORING);
        }
        
        //and it shouldnt stop
        for(int i = 0; i < 12; i++)
        {
            behavior.response(opponent, Action.CLEANING);
            assertEquals(behavior.decide(opponent), Action.IGNORING);
        }
    }
    
    public Behavior getBehavior() {
        return new Grudger();
    }
}
