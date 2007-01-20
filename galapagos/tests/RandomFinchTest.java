package galapagos.tests;

import galapagos.behaviors.*;
import galapagos.biotope.*;

/**
 * Test of the Random behavior, the decide method is
 * left untested because of its random nature.
 */
public class RandomFinchTest extends BehaviorTest {
	/**
	 * We cannot test the unpredictable
	 */
    public void testDecide() {

    }
    
    public Behavior getBehavior() {
        return new RandomFinch();
    }
    

    public String behaviorName () {
    	return "Random";
    }
}
