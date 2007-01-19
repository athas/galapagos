package galapagos.tests;

import galapagos.*;

/**
 * Test that the Predictor uses the two-bit algorithm properly.
 */
public class PredictorTest extends BehaviorTest {
    public void testDecide()
    {
        // By default, clean (a Predictor is a nice finch!)
        assertEquals(behavior.decide(opponent), Action.CLEANING);

        // Then let it make a cleaning prediction.
        behavior.response(opponent, Action.CLEANING);

        // Now a strong clean prediction, which should make the
        // Predictor ignore.
        assertEquals(behavior.decide(opponent), Action.IGNORING);

        // Now the cleaning prediction will be weak.
        behavior.response(opponent, Action.IGNORING);

        // So clean.
        assertEquals(behavior.decide(opponent), Action.CLEANING);

        // Go to strong ignore prediction.
        behavior.response(opponent, Action.IGNORING);

        // So ignore.
        assertEquals(behavior.decide(opponent), Action.IGNORING);

        // Go to weak ignore prediction.
        behavior.response(opponent, Action.IGNORING);

        // So ignore.
        assertEquals(behavior.decide(opponent), Action.IGNORING);
    }

    public Behavior getBehavior() {
        return new Predictor();
    }

    public void testToString () {
    	assertEquals(behavior.toString(),"Predictor");
    }
}
