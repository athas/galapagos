package galapagos.tests;

import galapagos.*;
import junit.framework.*;
import java.util.*;

/**
 * Primarily tests that Biotope is able to run simulation-rounds as specified
 * (with breeding, meetings and removal of dead finches) but also tests
 * that it updates the statistics properly.
 *
 */
public class BiotopeTest extends TestCase {
	ArrayList<Behavior> behaviors;
	public void setUp() {
		behaviors = new ArrayList<Behavior>();
		behaviors.add(new Samaritan());
		behaviors.add(new Grudger());
		behaviors.add(new FlipFlopper());
	}
	
	/**
	 * Tests that the right amount of finches per Behavior is placed in the world.
	 * Is tested with a 100x100 Biotop and 40 finches per Behavior. 
	 */
    public void testConstructor () {       
        //for counting the number of finches per behavior
        Map<Behavior, Integer> counter = new HashMap<Behavior, Integer>();
        //set the count to 0 for all Behaviors.
        for (Behavior be : behaviors)
        	counter.put(be, 0);
        
        Biotope biotope = new
        	Biotope(100, 100, 1.00, 12, 7, 3, 10, 13, 40, behaviors);
        
        assertEquals(0, biotope.round());
        
        // Count the number of finches belonging to each of the Behavior-type.
        for (World<GalapagosFinch>.Place place : biotope.world)
        {
        	GalapagosFinch finch = place.getElement();
        	if (finch != null) //only places with finches should be tested
        	{
        		// Test that the finch is one of the wanted types
        		assertTrue(behaviors.contains(finch.behavior()));
        		
        		// Test that the finch is alive with age 1.
        		assertEquals(1, finch.age());
        		assertEquals(FinchStatus.ALIVE, finch.status());

        		counter.put(finch.behavior(), counter.get(finch.behavior()) + 1);
        	}
        }
        
        // We test that here are 40 of each Behavior-type.
        for (Behavior be : behaviors)
        {
        	assertEquals(40, (int)counter.get(be));
        	
        	Statistics stats = biotope.statistics(be);
        	assertEquals(0, stats.getBorn());
        	assertEquals(0, stats.getBornThisRound());
        	assertEquals(0, stats.getDeadByAge());
        	assertEquals(0, stats.getDeadByAgeThisRound());
        	assertEquals(40, stats.getPopulation());
        }
    }
    
    /**
     * Creates a Biotope with finchesPerBehavior == 0
     * Tests that the constructor creates an empty World and
     * the Statistics is zero for all Behaviors.
     */
    public void testConstructorNoFinches () {
        // Make a Biotope at size 4x4 with 0 finches per behavior
        
        Biotope biotope = new
        	Biotope(4, 4, 0.33, 12, 7, 3, 10, 13, 0, behaviors);
        
        // There should be no finches in the world
        for (World<GalapagosFinch>.Place place : biotope.world)
        	assertNull(place.getElement());
        
        //Statistics for all behaviors should be 0.
        for (Behavior be : behaviors)
        {
        	Statistics stats = biotope.statistics(be);
        	assertEquals(0, stats.getBorn());
        	assertEquals(0, stats.getBornThisRound());
        	assertEquals(0, stats.getDeadByTicks());
        	assertEquals(0, stats.getDeadByTicksThisRound());
        	assertEquals(0, stats.getDeadByAge());
        	assertEquals(0, stats.getDeadByAgeThisRound());
        	assertEquals(0, stats.getPopulation());
        }
    }
    
    
    public void testPutFinch () {
        // Make a Biotope at size 4x4 with 0 Samaritans,
    	// Grudgers and FlipFloppers. In this biotope the finches don't
    	// loose any hotpoints, and they become 100 rounds old.
        
        Biotope biotope = new
        	Biotope(4, 4, 0.33, 12, 7, 0, 100, 100, 0, behaviors);
        World<GalapagosFinch> world = biotope.world;
    
        // We make a new Samaritan at place (2,2).
        biotope.putFinch(2,2, new Samaritan());
        
        // Check that the statistics has been correctly updated.
        Statistics stats = biotope.statistics((new Samaritan()));
        assertEquals(0, stats.getBorn());
        assertEquals(0, stats.getBornThisRound());
        assertEquals(0, stats.getDeadByTicks());
        assertEquals(0, stats.getDeadByTicksThisRound());
        assertEquals(0, stats.getDeadByAge());
        assertEquals(0, stats.getDeadByAgeThisRound());
        assertEquals(1, stats.getPopulation());
    	
    	World<GalapagosFinch>.Place place = world.getAt(2, 2);
    	assertNotNull(place.getElement());
    	
    	GalapagosFinch finch = place.getElement();
    	
    	assertEquals(new Samaritan(), finch.behavior());
    	assertEquals(1, finch.age());
		assertEquals(FinchStatus.ALIVE, finch.status());
		
		// Make a new samaritan and check that the statistics is still OK.
		biotope.putFinch(0, 2, new Samaritan());
        
        // Check that the statistics has been correctly updated.
        assertEquals(0, stats.getBorn());
        assertEquals(0, stats.getBornThisRound());
        assertEquals(0, stats.getDeadByTicks());
        assertEquals(0, stats.getDeadByTicksThisRound());
        assertEquals(0, stats.getDeadByAge());
        assertEquals(0, stats.getDeadByAgeThisRound());
        assertEquals(2, stats.getPopulation());
	}
    
    /**
     * Tests that finches breeds to a neighbour-place in the world and that the child
     * is a 1-year old version of their parents.
     * Tests in a 4x4 world with a breeding propability of 33% and no Behavior's per finch.
     */
    public void testBreed () {       
        Biotope biotope = new
        	Biotope(4, 4, 0.33, 12, 7, 0, 100, 100, 0, behaviors);
    
        // We make a new Samaritan at place (2,2).
        biotope.putFinch(2, 2, new Samaritan());
        
    	World<GalapagosFinch>.Place p = biotope.world.getAt(2, 2);
    	
    	GalapagosFinch fi = p.getElement();
    	
    	Statistics s = biotope.statistics(fi.behavior());
		
    	// Repeat making new rounds until the population has increased.
		// The finch fi should make a new finch at some point before it
		// dies.
		while (s.getPopulation() != 2) biotope.runRound();
		
		assertEquals(1, s.getBorn());
		assertEquals(1, s.getBornThisRound());
		assertEquals(0, s.getDeadByTicks());
		assertEquals(0, s.getDeadByTicksThisRound());
		assertEquals(0, s.getDeadByAge());
		assertEquals(0, s.getDeadByAgeThisRound());
    	
    	// Get the place at which the finch fi has made a new finch.
    	List<World<GalapagosFinch>.Place> neighbours = p.filledNeighbours();
    	
    	//should only have the just born finch as neighbour
    	assertEquals(1, neighbours.size());
    	World<GalapagosFinch>.Place newPlace = neighbours.get(0);
    	GalapagosFinch fi2 = newPlace.getElement();
    	assertEquals(new Samaritan(), fi2.behavior());
    	assertEquals(1, fi2.age());
    	assertEquals(FinchStatus.ALIVE, fi2.status());
    }
    
    /**
     * 
     *
     */
    public void testMeeting () {
        // Make a Biotope at size 4x4 with 0 Samaritans,
    	// Grudgers and FlipFloppers. In this biotope the finches loose
    	// 1 hitpoint per round and have 1 initial hitpoint.
    	// Therefore, they die in the first round if they don't
    	// make any meetings.
        
        Biotope b = new
        	Biotope(4, 4, 0.00, 10, 1, 1, 100, 100, 0, behaviors);
        World<GalapagosFinch> world = b.world;
    
        // We make a new Samaritan at place (2,2) and one at place (2,3).
        b.putFinch(2, 2, new Samaritan());
        b.putFinch(2, 3, new Samaritan());
        
    	GalapagosFinch fi1 = world.getAt(2, 2).getElement();
    	GalapagosFinch fi2 = world.getAt(2, 3).getElement();
    	
    	b.runRound();
    	
    	// fi1 and fi2 should be alive because they got 3
    	// hitpoints each from the meeting.
    	assertEquals(FinchStatus.ALIVE, fi1.status());
    	assertEquals(FinchStatus.ALIVE, fi2.status());
    	
    	// fi1 and fi2 should still be in the world:
    	assertEquals(fi1, world.getAt(2, 2).getElement());
    	assertEquals(fi2, world.getAt(2, 3).getElement());
    }
    
    public void testGrimReaperDeadByAge () {
        // Make a Biotope at size 4x4 with 0 Samaritans,
    	// Grudgers and FlipFloppers. In this biotope the finches loose
    	// 0 hitpoint per round, and become 2 rounds old.
    	
        Biotope b = new
        	Biotope(4, 4, 0.00, 10, 1, 0, 3, 3, 0, behaviors);
        World<GalapagosFinch> world = b.world;
    
        // We make a new Samaritan at place (2,2).
        b.putFinch(2,2, new Samaritan());
        
    	GalapagosFinch fi = world.getAt(2, 2).getElement();
    	
    	b.runRound();
    	
    	// fi should be older now.
    	assertEquals(2, fi.age());
    	
    	// fi should be alive
    	assertEquals(FinchStatus.ALIVE, fi.status());
    	
    	// fi should be in the world:
    	assertEquals(fi, world.getAt(2, 2).getElement());
    	
    	Statistics stats = b.statistics(new Samaritan());
    	
    	assertEquals(0, stats.getBorn());
    	assertEquals(0, stats.getBornThisRound());
    	assertEquals(0, stats.getDeadByTicks());
    	assertEquals(0, stats.getDeadByTicksThisRound());
    	assertEquals(0, stats.getDeadByAge());
    	assertEquals(0, stats.getDeadByAgeThisRound());
    
    	b.runRound();
    	
    	// fi should be older now.
    	assertEquals(3, fi.age());
    	
    	// fi should be dead by age
    	assertEquals(FinchStatus.DEAD_AGE, fi.status());
    	
    	// fi should not be in the world:
    	assertNull(world.getAt(2, 2).getElement());
    	
    	assertEquals(0, stats.getBorn());
    	assertEquals(0, stats.getBornThisRound());
    	assertEquals(0, stats.getDeadByTicks());
    	assertEquals(0, stats.getDeadByTicksThisRound());
    	assertEquals(1, stats.getDeadByAge());
    	assertEquals(1, stats.getDeadByAgeThisRound());
    }
    
    /**
     * Test that finches age is increased, the finches status is changed on death and dead finches is removed.
     */
    public void testGrimReaperDeadByTicks () {
        // Make a Biotope at size 4x4 with 0 Samaritans,
    	// Grudgers and FlipFloppers. In this biotope the finches loose
    	// 1 hitpoint per round and have 2 initial hitpoint. And breeding propability == 0.
    	// Therefore, they are dead after two rounds

        Biotope biotope = new Biotope(4, 4, 0.00, 10, 2, 1, 100, 100, 0, behaviors);
    
        // We make a new Samaritan at place (2,2).
        biotope.putFinch(2,2, new Samaritan());
        
    	GalapagosFinch fi = biotope.world.getAt(2, 2).getElement();
    	
    	biotope.runRound();
    	
    	// fi should be older now.
    	assertEquals(2, fi.age());
    	
    	// fi should be alive
    	assertEquals(FinchStatus.ALIVE, fi.status());
    	
    	// fi should be in the world
    	assertEquals(fi, biotope.world.getAt(2, 2).getElement());
    	
    	Statistics stats = biotope.statistics(new Samaritan());
    	
    	assertEquals(0, stats.getBorn());
    	assertEquals(0, stats.getBornThisRound());
    	assertEquals(0, stats.getDeadByTicks());
    	assertEquals(0, stats.getDeadByTicksThisRound());
    	assertEquals(0, stats.getDeadByAge());
    	assertEquals(0, stats.getDeadByAgeThisRound());

    	biotope.runRound();
    	
    	// fi1 should be older now.
    	assertEquals(3, fi.age());
    	
    	// fi1 should be dead by ticks
    	assertEquals(FinchStatus.DEAD_TICKS, fi.status());
    	
    	// fi1 should not be in the world:
    	assertNull(biotope.world.getAt(2, 2).getElement());
    	
    	assertEquals(0, stats.getBorn());
    	assertEquals(0, stats.getBornThisRound());
    	assertEquals(1, stats.getDeadByTicks());
    	assertEquals(1, stats.getDeadByTicksThisRound());
    	assertEquals(0, stats.getDeadByAge());
    	assertEquals(0, stats.getDeadByAgeThisRound());
    }
    
    /**
     * Test that the number of rounds is increased each time we call runRound.
     */
    public void testRound () {
        Biotope biotope = new Biotope(10, 10, 0.00, 10, 2, 1, 100, 100, 5, behaviors);
        
        assertEquals(0, biotope.round());
        biotope.runRound();
        biotope.runRound();
        biotope.runRound();
        assertEquals(3, biotope.round());
    }
    
    /**
     * Test that the behaviors of the Biotope equals those we give as argument
     */
    public void testBehaviors () {        
        Biotope biotope = new Biotope(10, 10, 0.00, 10, 2, 1, 100, 100, 5, behaviors);
        
        //tests both ways
        assertTrue(biotope.behaviors().containsAll(behaviors));
        assertTrue(behaviors.containsAll(biotope.behaviors()));
    }
}
