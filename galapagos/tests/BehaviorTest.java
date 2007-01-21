package galapagos.tests;

import galapagos.behaviors.*;
import galapagos.biotope.*;
import junit.framework.TestCase;

/**
 * A small test environment for behaviors.
 */
public abstract class BehaviorTest extends TestCase {

    Behavior behavior;
    Finch opponent, opponent2;
    
    /**
     * Create a behavior and some opponents for the concrete tests to use.
     */
    protected void setUp() {
        behavior = getBehavior();
        //create a GalapagosFinch the testDecide can use as opponent.
        opponent = new GalapagosFinch(10, 10, 10, new Samaritan());
        //create an alternative opponent for MemoryBehaviors.
        opponent2 = new GalapagosFinch(10, 10, 10, new Cheater());
    }
    
    /**
     * Returns an instance of the behavior under test.
     */
    protected abstract Behavior getBehavior();
    
    /**
     * Tests the decide method of a behavior.
     * Behaviors with memory should also make sure,
     * that the response method is tested.
     */
    public abstract void testDecide();

    /**
     * The name of the finch under test, is used in the testToString.
     */
    public abstract String behaviorName();
    
    /**
     * Ensures that the test writer and the implementation
     * agrees about the finch's name.
     */
    public void testToString () {
    	assertEquals(behavior.toString(), behaviorName());
    }
    
    /**
     * Test that the clone-method creates a new behavior of the same type,
     * and that it doesn't just copies the reference.
     */
    public final void testClone() {
        Behavior clone = behavior.clone();
        
        // should be of the same type
        assertEquals(behavior, clone);
        
        // but not the same instance
        assertTrue(behavior != clone);
        
        // creating 2 clones should create 2 new behaviors.
        Behavior clone2 = behavior.clone();
        assertTrue(clone != clone2);
    }
    
    /**
     * Test that equals() indicates when to behaviors is of
     * the same behavior type.
     */
    public final void testEquals() {
    	//JUnit uses the equals()-method to test for equality.
    	assertEquals(behavior, behavior.clone());
    	assertNotSame(behavior, new Object());
    }
    
    /**
     * Test that hashCode() gives the same result for instances that are equal
     */
    public final void testHashCode() {
    	assertEquals(behavior.hashCode(), behavior.clone().hashCode());
    }
}
