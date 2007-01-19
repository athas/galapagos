package galapagos.tests;

import galapagos.*;
import junit.framework.*;
import java.util.*;

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
    	assertEquals(1, finch.age());
		assertEquals("Samaritan", finch.behavior().toString());
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
    
    public void testBreed () {
        // Make a Biotope at size 4x4 with 0 Samaritans,
    	// Grudgers and FlipFloppers. In this biotope the finches don't
    	// loose any hotpoints, and they become 100 rounds old.
        
        Biotope b = new
        	Biotope(4, 4, 0.33, 12, 7, 0, 100, 100, 0, behaviors);
        World<GalapagosFinch> world = b.world;
    
        // We make a new Samaritan at place (2,2).
        b.putFinch(2,2, new Samaritan());
        
    	World<GalapagosFinch>.Place p = world.getAt(2, 2);
    	
    	GalapagosFinch fi = p.getElement();
    	
    	Statistics s = b.statistics(fi.behavior());
		
    	// Repeat making new rounds until the population has increased.
		// The finch fi should make a new finch at some point before it
		// dies.
		while (s.getPopulation() != 2) b.runRound();
		
		assertTrue(s.getBorn() == 1);
    	assertTrue(s.getBornThisRound() == 1);
    	assertTrue(s.getDeadByTicks() == 0);
    	assertTrue(s.getDeadByTicksThisRound() == 0);
    	assertTrue(s.getDeadByAge() == 0);
    	assertTrue(s.getDeadByAgeThisRound() == 0);
    	
    	// Get the place at which the finch fi has made a new finch.
    	List<World<GalapagosFinch>.Place> placeList = p.filledNeighbours();
    	assertTrue(placeList.size() == 1);
    	World<GalapagosFinch>.Place newPlace = placeList.get(0);
    	GalapagosFinch fi2 = newPlace.getElement();
    	assertTrue(fi2.behavior().toString() == "Samaritan");
    	assertTrue(fi2.age() == 1);
    	assertTrue(fi2.status() == FinchStatus.ALIVE);
    }
    
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
        b.putFinch(2,2, new Samaritan());
        b.putFinch(2,3, new Samaritan());
        
    	GalapagosFinch fi1 = world.getAt(2, 2).getElement();
    	GalapagosFinch fi2 = world.getAt(2, 3).getElement();
    	
    	b.runRound();
    	
    	// fi1 and fi2 should be alive because they got 3
    	// hitpoints each from the meeting.
    	assertTrue(fi1.status() == FinchStatus.ALIVE);
    	assertTrue(fi2.status() == FinchStatus.ALIVE);
    	
    	// fi1 and fi2 should still be in the world:
    	assertTrue(world.getAt(2, 2).getElement() == fi1);
    	assertTrue(world.getAt(2, 3).getElement() == fi2);
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
    	assertTrue(fi.age() == 2);
    	
    	// fi should be alive
    	assertTrue(fi.status() == FinchStatus.ALIVE);
    	
    	// fi should be in the world:
    	assertTrue(world.getAt(2, 2).getElement() == fi);
    	
    	Statistics s = b.statistics(new Samaritan());
    	
    	assertTrue(s.getBorn() == 0);
    	assertTrue(s.getBornThisRound() == 0);
    	assertTrue(s.getDeadByTicks() == 0);
    	assertTrue(s.getDeadByTicksThisRound() == 0);
    	assertTrue(s.getDeadByAge() == 0);
    	assertTrue(s.getDeadByAgeThisRound() == 0);
    
    	b.runRound();
    	
    	// fi should be older now.
    	assertTrue(fi.age() == 3);
    	
    	// fi should be dead by age
    	assertTrue(fi.status() == FinchStatus.DEAD_AGE);
    	
    	// fi should not be in the world:
    	assertTrue(world.getAt(2, 2).getElement() == null);
    	
    	assertTrue(s.getBorn() == 0);
    	assertTrue(s.getBornThisRound() == 0);
    	assertTrue(s.getDeadByTicks() == 0);
    	assertTrue(s.getDeadByTicksThisRound() == 0);
    	assertTrue(s.getDeadByAge() == 1);
    	assertTrue(s.getDeadByAgeThisRound() == 1);
    }
        
    public void testGrimReaperDeadByTicks () {
        // Make a Biotope at size 4x4 with 0 Samaritans,
    	// Grudgers and FlipFloppers. In this biotope the finches loose
    	// 1 hitpoint per round and have 2 initial hitpoint.
    	// Therefore, they are dead after two rundRounds

        Biotope b = new
        	Biotope(4, 4, 0.00, 10, 2, 1, 100, 100, 0, behaviors);
        World<GalapagosFinch> world = b.world;
    
        // We make a new Samaritan at place (2,2).
        b.putFinch(2,2, new Samaritan());
        
    	GalapagosFinch fi = world.getAt(2, 2).getElement();
    	
    	b.runRound();
    	
    	// fi should be older now.
    	assertTrue(fi.age() == 2);
    	
    	// fi should be alive
    	assertEquals(FinchStatus.ALIVE, fi.status());
    	
    	// fi should be in the world
    	assertTrue(world.getAt(2, 2).getElement() == fi);
    	
    	Statistics s = b.statistics(new Samaritan());
    	
    	assertTrue(s.getBorn() == 0);
    	assertTrue(s.getBornThisRound() == 0);
    	assertTrue(s.getDeadByTicks() == 0);
    	assertTrue(s.getDeadByTicksThisRound() == 0);
    	assertTrue(s.getDeadByAge() == 0);
    	assertTrue(s.getDeadByAgeThisRound() == 0);

    	b.runRound();
    	
    	// fi1 should be older now.
    	assertTrue(fi.age() == 3);
    	
    	// fi1 should be dead by ticks
    	assertEquals(FinchStatus.DEAD_TICKS, fi.status());
    	
    	// fi1 should not be in the world:
    	assertTrue(world.getAt(2, 2).getElement() == null);
    	
    	assertTrue(s.getBorn() == 0);
    	assertTrue(s.getBornThisRound() == 0);
    	assertTrue(s.getDeadByTicks() == 1);
    	assertTrue(s.getDeadByTicksThisRound() == 1);
    	assertTrue(s.getDeadByAge() == 0);
    	assertTrue(s.getDeadByAgeThisRound() == 0);
    }
    
    public void testRound () {
        // Make a Biotope at size 4x4 with 0 Samaritans,
    	// Grudgers and FlipFloppers.

        Biotope b = new
        	Biotope(4, 4, 0.00, 10, 2, 1, 100, 100, 0, behaviors);
        
        assertTrue(b.round() == 0);
        b.runRound();
        b.runRound();
        assertTrue(b.round() == 2);
    }
    
    public void testBehaviors () {
        // Make a Biotope at size 4x4 with 0 Samaritans,
    	// Grudgers and FlipFloppers.
        
        Biotope b = new
        	Biotope(4, 4, 0.00, 10, 2, 1, 100, 100, 0, behaviors);
        
        assertTrue(b.behaviors() == behaviors);
    }
}
