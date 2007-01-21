package galapagos.tests;

import galapagos.behaviors.*;
import galapagos.biotope.*;
import java.util.*;

/**
 * Test that the Schizophrenic Behavior works.
 * Since the Schizophrenic should just delegate all decision making 
 * to its personalities, we make a Schizophrenic with only one
 * personality (the Grudger) and uses the test methods of the Grudger.
 */
public class SchizophrenicGrudgerTest extends GrudgerTest {
    public Behavior getBehavior() {
        List<Behavior> grudger = new ArrayList<Behavior>(1);
        grudger.add(new Grudger());
        return new Schizophrenic("Schizophrenic Grudger", grudger);
    }
    
    public String behaviorName () {
        return "Schizophrenic Grudger";
    }
}
