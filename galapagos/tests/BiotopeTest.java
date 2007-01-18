package galapagos.tests;

import galapagos.*;
import junit.framework.*;
import java.util.*;

public class BiotopeTest extends TestCase {

    public void testStart1 () {
        // Make a Biotope at size 100x100 with 40 Samaritans,
    	// Grudgers and FlipFloppers.
    	ArrayList<Behavior> list = new ArrayList<Behavior>();
        list.add(new Samaritan());
        list.add(new Grudger());
        list.add(new FlipFlopper());
        // Make a list of the string representations of the behaviors.
        ArrayList<String> stringList = new ArrayList<String>();
        for (Behavior be : list) stringList.add(be.toString());
        
        Map<String,Integer> m = new WeakHashMap<String,Integer>();
        for (Behavior be : list)
        	m.put(be.toString(),0);
        Biotope b = new
        	Biotope(100, 100, 1.00, 12, 7, 3, 10, 13, 40, list);
        World<GalapagosFinch> world = b.world;
        assertTrue(b.round() == 0);
        
        // The following tests that the right amount of finches are placed
        // in the world.
        // We run through the world and count the number of finches
        // belonging to each of the behavior types.
        for (Iterator<World<GalapagosFinch>.Place> it = b.worldIterator();
        	it.hasNext();)
        {
        	World<GalapagosFinch>.Place i = it.next();
        	if (i.getElement() != null)
        	{
        		GalapagosFinch fi = i.getElement();
        		// We test that the non-empty place contains a finch with one
        		// of the behaviors in list, and that the finch has correct
        		// qualities.
        		assertTrue(stringList.contains(fi.behavior().toString()));
        		assertTrue(fi.age() == 1);
        		assertTrue(fi.status() == FinchStatus.ALIVE);
        	    m.put(fi.behavior().toString(),m.get(fi.behavior().toString())+1);
        	}
        }
        
        // We test that 40 of each type were counted.
        for (Behavior be : list)
        {
        	assertTrue(m.get(be.toString()) == 40);
        	Statistics s = b.statistics(be.toString());
        	assertTrue(s.getBorn() == 0);
        	assertTrue(s.getBornThisRound() == 0);
        	assertTrue(s.getDeadByAge() == 0);
        	assertTrue(s.getDeadByAgeThisRound() == 0);
        	assertTrue(s.getPopulation() == 40);
        }
    }
    
    public void testStart2 () {
        // Make a Biotope at size 4x4 with 0 Samaritans,
    	// Grudgers and FlipFloppers.
    	ArrayList<Behavior> list = new ArrayList<Behavior>();
        list.add(new Samaritan());
        list.add(new Grudger());
        list.add(new FlipFlopper());
        
        Biotope b = new
        	Biotope(4, 4, 0.33, 12, 7, 3, 10, 13, 0, list);
        // The following tests that there are no finches in the world.
        for (Iterator<World<GalapagosFinch>.Place> it = b.worldIterator();
        	it.hasNext();)
        {
        	World<GalapagosFinch>.Place i = it.next();
        	assertTrue(i.getElement() == null);
        }
        for (Behavior be : list)
        {
        	Statistics s = b.statistics(be.toString());
        	assertTrue(s.getBorn() == 0);
        	assertTrue(s.getBornThisRound() == 0);
        	assertTrue(s.getDeadByTicks() == 0);
        	assertTrue(s.getDeadByTicksThisRound() == 0);
        	assertTrue(s.getDeadByAge() == 0);
        	assertTrue(s.getDeadByAgeThisRound() == 0);
        }
    }
    
    public void testPutFinch () {
        // Make a Biotope at size 4x4 with 0 Samaritans,
    	// Grudgers and FlipFloppers. In this biotope the finches don't
    	// loose any hotpoints, and they become 100 rounds old.
    	ArrayList<Behavior> list = new ArrayList<Behavior>();
        list.add(new Samaritan());
        list.add(new Grudger());
        list.add(new FlipFlopper());
        
        Biotope b = new
        	Biotope(4, 4, 0.33, 12, 7, 0, 100, 100, 0, list);
        World<GalapagosFinch> world = b.world;
    
        // We make a new Samaritan at place (2,2).
        b.putFinch(2,2, new Samaritan());
        
        // Check that the statistics has been correctly updated.
        Statistics s = b.statistics((new Samaritan()).toString());
        assertTrue(s.getBorn() == 0);
    	assertTrue(s.getBornThisRound() == 0);
    	assertTrue(s.getDeadByTicks() == 0);
    	assertTrue(s.getDeadByTicksThisRound() == 0);
    	assertTrue(s.getDeadByAge() == 0);
    	assertTrue(s.getDeadByAgeThisRound() == 0);
    	assertTrue(s.getPopulation() == 1);
    	
    	World<GalapagosFinch>.Place p = world.getAt(2, 2);
    	assertTrue(p.getElement() != null);
    	
    	GalapagosFinch fi = p.getElement();
    	assertTrue(fi.age() == 1);
		assertTrue(fi.behavior().toString().equals("Samaritan"));
		assertTrue(fi.status() == FinchStatus.ALIVE);
		
		// Make a new samaritan and check that the statistics is still OK.
		b.putFinch(0,2, new Samaritan());
        
        // Check that the statistics has been correctly updated.
        assertTrue(s.getBorn() == 0);
    	assertTrue(s.getBornThisRound() == 0);
    	assertTrue(s.getDeadByTicks() == 0);
    	assertTrue(s.getDeadByTicksThisRound() == 0);
    	assertTrue(s.getDeadByAge() == 0);
    	assertTrue(s.getDeadByAgeThisRound() == 0);
    	assertTrue(s.getPopulation() == 2);
	}
    
    public void testBreed () {
        // Make a Biotope at size 4x4 with 0 Samaritans,
    	// Grudgers and FlipFloppers. In this biotope the finches don't
    	// loose any hotpoints, and they become 100 rounds old.
    	ArrayList<Behavior> list = new ArrayList<Behavior>();
        list.add(new Samaritan());
        list.add(new Grudger());
        list.add(new FlipFlopper());
        
        Biotope b = new
        	Biotope(4, 4, 0.33, 12, 7, 0, 100, 100, 0, list);
        World<GalapagosFinch> world = b.world;
    
        // We make a new Samaritan at place (2,2).
        b.putFinch(2,2, new Samaritan());
        
    	World<GalapagosFinch>.Place p = world.getAt(2, 2);
    	
    	GalapagosFinch fi = p.getElement();
    	
    	Statistics s = b.statistics(fi.behavior().toString());
		
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
    	ArrayList<Behavior> list = new ArrayList<Behavior>();
        list.add(new Samaritan());
        list.add(new Grudger());
        list.add(new FlipFlopper());
        
        Biotope b = new
        	Biotope(4, 4, 0.00, 10, 1, 1, 100, 100, 0, list);
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
    	ArrayList<Behavior> list = new ArrayList<Behavior>();
        list.add(new Samaritan());
        list.add(new Grudger());
        list.add(new FlipFlopper());
        
        Biotope b = new
        	Biotope(4, 4, 0.00, 10, 1, 0, 3, 3, 0, list);
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
    	
    	Statistics s = b.statistics("Samaritan");
    	
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
    	ArrayList<Behavior> list = new ArrayList<Behavior>();
        list.add(new Samaritan());
        list.add(new Grudger());
        list.add(new FlipFlopper());
        
        Biotope b = new
        	Biotope(4, 4, 0.00, 10, 2, 1, 100, 100, 0, list);
        World<GalapagosFinch> world = b.world;
    
        // We make a new Samaritan at place (2,2).
        b.putFinch(2,2, new Samaritan());
        
    	GalapagosFinch fi = world.getAt(2, 2).getElement();
    	
    	b.runRound();
    	
    	// fi should be older now.
    	assertTrue(fi.age() == 2);
    	
    	// fi should be alive
    	assertTrue(fi.status() == FinchStatus.ALIVE);
    	
    	// fi should be in the world
    	assertTrue(world.getAt(2, 2).getElement() == fi);
    	
    	Statistics s = b.statistics("Samaritan");
    	
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
    	assertTrue(fi.status() == FinchStatus.DEAD_TICKS);
    	
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
    	ArrayList<Behavior> list = new ArrayList<Behavior>();
        list.add(new Samaritan());
        list.add(new Grudger());
        list.add(new FlipFlopper());
        
        Biotope b = new
        	Biotope(4, 4, 0.00, 10, 2, 1, 100, 100, 0, list);
        
        assertTrue(b.round() == 0);
        b.runRound();
        b.runRound();
        assertTrue(b.round() == 2);
    }
    
    public void testBehaviors () {
        // Make a Biotope at size 4x4 with 0 Samaritans,
    	// Grudgers and FlipFloppers.
    	ArrayList<Behavior> list = new ArrayList<Behavior>();
        list.add(new Samaritan());
        list.add(new Grudger());
        list.add(new FlipFlopper());
        
        Biotope b = new
        	Biotope(4, 4, 0.00, 10, 2, 1, 100, 100, 0, list);
        
        assertTrue(b.behaviors() == list);
    }
}
