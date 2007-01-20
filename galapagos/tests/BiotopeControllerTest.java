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
