package galapagos.tests;

import galapagos.*;
import junit.framework.TestCase;

public abstract class BehaviorTest extends TestCase {

    Behavior behavior;
    Finch opponent;
    
    protected void setUp() {
        behavior = getBehavior();
        opponent = new GalapagosFinch(10, 10, 10, new Samaritan());
    }
    
    protected abstract Behavior getBehavior();
    public abstract void testDecide();

    public final void testClone()
    {
        Behavior clone = behavior.clone();
        
        assertEquals(behavior.getClass().toString(), clone.getClass().toString());
    }
    
    /*public void testToString()
    {
        assertEquals(behavior.getClass().getSimpleName(), behavior.toString());
    }*/
}
