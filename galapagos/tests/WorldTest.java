package galapagos.tests;

import galapagos.*;
import junit.framework.*;
import java.util.*;

public class WorldTest extends TestCase {

    public void testContainsTheSame(List<World<Integer>.Place> list1, List<Integer> list2) {
        for (World.Place a: list1)
            assertTrue(list2.contains(a.element()));
    }

    public void testWorld () {
        World<Integer> w = new World<Integer>(3, 3);
        List<Integer> list = new LinkedList<Integer>();

        for (int x = 0; x < w.width(); x++)
            for (int y = 0; y < w.height(); y++)
                testContainsTheSame(w.getAt(x, y).filledNeighbours(), list);

        for (int i = 0; i < 9; i++)
            list.add(i);

        for (int x = 0; x < w.width(); x++)
            for (int y = 0; y < w.height(); y++)
                w.setAt(x, y, x * w.width() + y);

        for (int x = 0; x < w.width(); x++)
            for (int y = 0; y < w.height(); y++)
                testContainsTheSame(w.getAt(x, y).filledNeighbours(), list);

        for (World.Place p : w) {
            testContainsTheSame(p.filledNeighbours(), list);
        }

        for (Iterator i = w.randomIterator(); i.hasNext(); ) {
            World.Place p = (World.Place)i.next();
            testContainsTheSame(p.filledNeighbours(), list);
        }
    }
}
