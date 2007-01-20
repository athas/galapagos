package galapagos.tests;

import galapagos.*;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.*;
import junit.framework.TestCase;

/**
 * Test of the BiotopeController
 */
public class BiotopeControllerTest extends TestCase {
    List<Behavior> behaviors;
    BiotopeController controller;
    Biotope biotope; 
        
    protected void setUp() {
        behaviors = new LinkedList<Behavior>();
        behaviors.add(new Samaritan());
        behaviors.add(new Cheater());
        behaviors.add(new Grudger());
        behaviors.add(new TitForTat());

        biotope = new Biotope(30, 30, 0.167, 10, 7, 4, 10, 13, 10, behaviors);
        controller = new BiotopeController(biotope);
    }

    /**
     * Tests that the controller answers the nextRound command.
     */
    public void testNextRound() {
        int oldRound = biotope.round();
        for (int i = 0; i < 10; i++) {
            controller.actionPerformed(new ActionEvent(this, 0, "nextRound"));
            assertEquals(oldRound + 1, biotope.round());
            oldRound = biotope.round();
        }
    }

    // The putFinches/removeFinches tests kinda cover each other in
    // scope, but that only increases test coverage.
    public void testPutFinches() {
        // First, remove all finches.
        controller.takeFinches(0, 0, 200);
        
        // Then, add some and see if they were actually added.
        controller.putFinches(0, 0, 10, new Samaritan());

        Behavior samaritan = null;

        for (Behavior b : biotope.behaviors())
            if (b instanceof Samaritan)
                samaritan = b;

        assertNotNull(samaritan);

        Statistics s = biotope.statistics(samaritan);
        assertEquals(305, s.getStatByElement(Statistics.StatisticsElement.POPULATION));
    }

    public void testRemoveFinches() {
        // First, fill everything with samaritans.
        controller.putFinches(0, 0, 50, new Samaritan());

        Behavior samaritan = null;

        for (Behavior b : biotope.behaviors())
            if (b instanceof Samaritan)
                samaritan = b;

        assertNotNull(samaritan);

        Statistics s = biotope.statistics(samaritan);
        assertEquals(900, s.getStatByElement(Statistics.StatisticsElement.POPULATION));

        // Now remove everything.
        controller.takeFinches(0, 0, 200);
        assertEquals(0, s.getStatByElement(Statistics.StatisticsElement.POPULATION));
    }
    
    /**
     * Tests that the controller answers the severalRounds command.
     * Will make an infinite loop if the severalRounds-method doesn't work
     */
    public void testSeveralRounds() {
    	assertEquals(0, biotope.round());   	
    	controller.runSeveralRounds(10);

    	// wait for the biotope to complete the 
    	//specified number of rounds
    	while(biotope.round() < 10); 
    	
    	assertEquals(10, biotope.round());
    }
    
    /**
     * Tests that the controller answers the loop and stopSimulation commands.
     * The test will make an infinite loop if the stopSimulation-command doesn't work.
     */
    public void testLoopingRounds() {
    	assertEquals(0, biotope.round());   	
    	controller.loop();

    	while(biotope.round() < 40); // wait 40 rounds 
    	controller.stopSimulation();
    	assertEquals(40, biotope.round());
    }
}
