package galapagos.tests;

import galapagos.*;
import junit.framework.*;
import java.util.*;

public class BiotopeTest extends TestCase {

    public void testBiotope () {
        ArrayList<Behavior> list = new ArrayList<Behavior>();
        list.add(new Samaritan());
        list.add(new Grudger());
        list.add(new FlipFlopper());
        Biotope b = new Biotope(list);
        for (int i = 0; i < 20; i++)
            b.runRound();
        for (Behavior be : list) {
            Statistics stat = b.statistics(be.toString());
            assertNotNull(stat);
            System.out.println("-----\nBehavior: " + be);
            System.out.println("Population: " + stat.getPopulation());
            System.out.println("Born this round: " + stat.getBornThisRound());
            System.out.println("Born total: " + stat.getBorn());
            System.out.println("Dead of old age this round: " + stat.getDeadByAgeThisRound());
            System.out.println("Dead of old age total: " + stat.getDeadByAge());
            System.out.println("Dead of sickness this round: " + stat.getDeadByTicksThisRound());
            System.out.println("Dead of sickness total: " + stat.getDeadByTicks());
        }
    }
}
