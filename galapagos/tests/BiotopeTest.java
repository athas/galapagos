package galapagos.tests;

import galapagos.*;
import junit.framework.*;
import java.util.*;

public class BiotopeTest extends TestCase {

    public void testBiotope () {
        // Make a Biotope at size 100x100 with 40 Samaritans,
    	// Grudgers and FlipFloppers.
    	ArrayList<Behavior> list = new ArrayList<Behavior>();
        list.add(new Samaritan());
        list.add(new Grudger());
        list.add(new FlipFlopper());
        
        // The following tests that the right amount of finches are placed
        // in the world.
        Map<String,Integer> m = new WeakHashMap<String,Integer>();
        for (Behavior be : list)
        	m.put(be.toString(),0);
        Biotope b = new
        	Biotope(100, 100, 1.00, 12, 7, 3, 10, 13, 40, list);
        
        // We run through the world and count the number of finches
        // belonging to each of the behavior types.
        for (Iterator<World<GalapagosFinch>.Place> it = b.worldIterator();
        	it.hasNext();)
        {
        	World<GalapagosFinch>.Place i = it.next();
        	if (i.getElement() != null)
        	{
        		Behavior be = i.getElement().behavior();
        		m.put(be.toString(),m.get(be.toString())+1);
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
        
        // Delete all finches in the world, and remember the last place as p.
        World<GalapagosFinch>.Place p;
        for (Iterator<World<GalapagosFinch>.Place> it = b.worldIterator();
    		it.hasNext();)
        {		
        	World<GalapagosFinch>.Place i = it.next();
        	i.setElement(null);
        	p = i;
        }
        p.setElement(new GalapagosFinch(5, 10, 10, new Samaritan()));
    }
}
