package galapagos.tests;

import galapagos.behaviors.*;
import galapagos.biotope.*;
import java.util.*;

/**
 * Test that the Schizophrenic Behavior works.
 * Since the Schizophrenic should just delegate all decision making 
 * to its personalities, we make a Cheater Schizophrenic and
 * a Samaritan Schizophrenic and se that they behave like
 * a Cheater and a Samaritan respectively.
 * We also test that the toString()-method returns the specified name.
 * The response method is impossible to test properly since we
 * can't access the personalities directly and therefore can't 
 * determine which personalities have been informed of a 
 * given opponent action.
 */
public class SchizophrenicGrudgerTest extends GrudgerTest {
    public Behavior getBehavior() {
        List<Behavior> grudger = new ArrayList<Behavior>(1);
        grudger.add(new Grudger());
        return new Schizophrenic("Schizophrenic Grudger", grudger, false);
    }
    
    public String behaviorName () {
        return "Schizophrenic Grudger";
    }
}
