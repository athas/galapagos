package galapagos.tests;

import junit.framework.TestCase;
import galapagos.*;

public class StatisticsTest extends TestCase {

	public void elementTest(Statistics s) 
	{
		assertTrue(s.getStatByElement(Statistics.StatisticsElement.POPULATION) ==
			s.getPopulation());
		assertTrue(s.getStatByElement(Statistics.StatisticsElement.BORN_THIS_ROUND) ==
			s.getBornThisRound());
		assertTrue(s.getStatByElement(Statistics.StatisticsElement.DEAD_TICKS_THIS_ROUND) ==
			s.getDeadByTicksThisRound());
		assertTrue(s.getStatByElement(Statistics.StatisticsElement.DEAD_AGE_THIS_ROUND) ==
			s.getDeadByAgeThisRound());
		assertTrue(s.getStatByElement(Statistics.StatisticsElement.BORN_TOTAL) ==
			s.getBorn());
		assertTrue(s.getStatByElement(Statistics.StatisticsElement.DEAD_TICKS_TOTAL) ==
			s.getDeadByTicks());
		assertTrue(s.getStatByElement(Statistics.StatisticsElement.DEAD_AGE_TOTAL) ==
			s.getDeadByAge());
	}
	
	public void testStatistics () {
		Statistics s = new Statistics();
		// Check the initial values.
		assertTrue(s.getPopulation() == 0);
		assertTrue(s.getBorn() == 0);
		assertTrue(s.getDeadByAge() == 0);
		assertTrue(s.getDeadByTicks() == 0);
		assertTrue(s.getBornThisRound() == 0);
		assertTrue(s.getDeadByAgeThisRound() == 0);
		assertTrue(s.getDeadByTicksThisRound() == 0);
		elementTest(s);
		// Check increase mathods and get-methods.
		s.incBorn();
		s.incDeadByAge();
		s.incDeadByTicks();
		s.incPopulation();
		assertTrue(s.getPopulation() == 1);
		assertTrue(s.getBorn() == 1);
		assertTrue(s.getDeadByAge() == 1);
		assertTrue(s.getDeadByTicks() == 1);
		assertTrue(s.getBornThisRound() == 1);
		assertTrue(s.getDeadByAgeThisRound() == 1);
		assertTrue(s.getDeadByTicksThisRound() == 1);
		elementTest(s);
		// Check newRound().
		s.newRound();
		assertTrue(s.getBornThisRound() == 0);
		assertTrue(s.getDeadByAgeThisRound() == 0);
		assertTrue(s.getDeadByTicksThisRound() == 0);
		elementTest(s);
		// Check decPopulation().
		s.decPopulation();
		assertTrue(s.getPopulation() == 0);
		elementTest(s);
	}
}
