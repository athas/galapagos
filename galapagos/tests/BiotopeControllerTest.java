package galapagos.tests;

import galapagos.*;
import java.util.HashMap;
import java.awt.Color;
import java.awt.event.*;
import junit.framework.TestCase;

public class BiotopeControllerTest extends TestCase {
    HashMap<Behavior, Color> behaviors;
    GalapagosFrame frame;
    BiotopeController controller;
        
    protected void setUp() {
        behaviors= new HashMap<Behavior, Color>();
        behaviors.put(new Samaritan(), Color.RED);
        behaviors.put(new Cheater(), Color.BLUE);
        behaviors.put(new FlipFlopper(), Color.getHSBColor(0, 0, (float)0.5));
        behaviors.put(new RandomFinch(), Color.MAGENTA);
        behaviors.put(new Grudger(), Color.ORANGE.darker());
        behaviors.put(new TitForTat(), Color.GREEN);
        behaviors.put(new ProbingTitForTat(), Color.CYAN);
        behaviors.put(new SuspiciousTitForTat(), Color.ORANGE);
        frame = new GalapagosFrame(behaviors);
        controller = frame.controller();
    }

    public void testNextRound() {
        int oldRound = frame.biotope().round();
        for (int i = 0; i < 50; i++) {
            controller.actionPerformed(new ActionEvent(this, 0, "nextRound"));
            assertEquals(oldRound + 1, frame.biotope().round());
            oldRound = frame.biotope().round();
        }
    }
}
