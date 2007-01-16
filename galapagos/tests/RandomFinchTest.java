package galapagos.tests;

import galapagos.*;


public class RandomFinchTest extends BehaviorTest {
    public void testDecide()
    {
        //??
    }
    
    public Behavior getBehavior() {
        return new RandomFinch();
    }
    

    public void testToString () {
    	assertEquals(behavior.toString(),"Random");
    }
}
