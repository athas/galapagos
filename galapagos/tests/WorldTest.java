package galapagos.tests;

import galapagos.biotope.World;
import junit.framework.*;
import java.util.*;

/**
 * Do black-box testing of World based on the public interface, but
 * still with an eye towards the corner cases of the implementation.
 */
public class WorldTest extends TestCase {

    /**
     * Convert a list of Place objects containing integers to a list
     * of integers. This is a utility method.
     */
    public List<Integer> toIntegerList(List<World<Integer>.Place> list)
    {
        LinkedList<Integer> result = new LinkedList<Integer>();
        for(World<Integer>.Place place : list)
        {
            result.add(place.getElement());
        }
        return result;
    }

    /**
     * Test that the two provided lists contains exactly the same
     * elements and no more (no specific order is required). If the
     * tests fail, JUnit assertation failures will happen. This is
     * used in implementing other test cases.
     */
    public void containsTheSame (List<?> list1, List<?> list2)
    {
        assertTrue("list2 doesn't contain all elements of list1" , list2.containsAll(list1));
        assertTrue("list1 doesn't contain all elements of list2" , list1.containsAll(list2));
        assertEquals("The lists are different in size", list2.size(), list1.size());
    }
    
    /**
     * The World object that will be used for testing throughout this
     * class.
     */
    public World<Integer> world;
    
    /**
     * A list object that is used for testing whether World contains
     * what it should.
     */
    public List<Integer> list;
    
    public void setUp() {
        world = new World<Integer>(3, 3);
        list = new LinkedList<Integer>();
    }
    
    /**
     * Fills the world with integers so it looks like:
     *      0 3 6
     *      1 4 7
     *      2 5 8
     */
    public void fillWorld()
    {
        for (int x = 0; x < world.width(); x++)
            for (int y = 0; y < world.height(); y++)
                world.setAt(x, y, x * world.width() + y);
    }

    public void testWorldContents() {
        fillWorld();
        
        for (int x = 0; x < world.width(); x++)
            for (int y = 0; y < world.height(); y++)
                assertEquals(x * world.width() + y, (int) world.getAt(x, y).getElement());
    }
    
    /**
       Testing whether the method to retrieve the neighbors of a place
       works properly, even when the place is at the border of the
       world.
     */
    public void testfilledNeighbours()
    {
        fillWorld();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        
        for (int x = 0; x < world.width(); x++)
            for (int y = 0; y < world.height(); y++) {
                List<Integer> neighbourList = toIntegerList(world.getAt(x,y).filledNeighbours());
                // Add the current element for ease of comparison.
                neighbourList.add(world.getAt(x,y).getElement());
                containsTheSame(neighbourList, list);
            }
    }

    public void testWrapping()
    {
        assertEquals(0, world.wrappedX(0));
        assertEquals(0, world.wrappedY(0));
        assertEquals(1, world.wrappedX(1));
        assertEquals(1, world.wrappedY(1));
        assertEquals(2, world.wrappedX(2));
        assertEquals(2, world.wrappedY(2));
        assertEquals(0, world.wrappedX(3));
        assertEquals(0, world.wrappedY(3));
        assertEquals(1, world.wrappedX(4));
        assertEquals(1, world.wrappedY(4));
        assertEquals(2, world.wrappedX(5));
        assertEquals(2, world.wrappedY(5));
        assertEquals(0, world.wrappedX(6));
        assertEquals(0, world.wrappedY(6));
    }
    
    /**
     * Test that RandomIterator goes through all elements
     */
    public void testRandomIterator() {
        fillWorld();
        //stores the elements it has passed
        LinkedList<Integer> found = new LinkedList<Integer>();
        
        for (Iterator<World<Integer>.Place> i = world.randomIterator(); i.hasNext(); ) {
            World<Integer>.Place place =  i.next();
            found.add(place.getElement());
        }
        
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        
        containsTheSame(found, list);
    }
    
    
    /**
     * When there are no elements in the world, no neighbours should
     * be found.
     */
    public void testNoNeighboursInEmptyWorld() {
        for(World<Integer>.Place place : world)
        {
            assertEquals(0, place.filledNeighbours().size());
        }
    }

    /**
     * Test that it is possible to change the state of the world,
     * using both the worlds own method and a given Place object.
     */
    public void testMutablePlaces () {
        fillWorld();
        
        World<Integer>.Place p = world.getAt(2,2);

        assertEquals(8, (int)p.getElement());

        world.setAt(2,2,42);
        assertEquals(42, (int)p.getElement());

        p.setElement(39);
        assertEquals(39, (int)p.getElement());;
    }
}
