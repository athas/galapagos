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
public class SchizophrenicTitForTatTest extends TitForTatTest {
    public Behavior getBehavior() {
        List<Behavior> titForTat = new ArrayList<Behavior>(1);
        titForTat.add(new TitForTat());
        return new Schizophrenic("Schizophrenic Tit for Tat", titForTat, false);
    }
    
    public String behaviorName () {
        return "Schizophrenic Tit for Tat";
    }
}
