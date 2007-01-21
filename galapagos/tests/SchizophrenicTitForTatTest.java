package galapagos.tests;

import galapagos.behaviors.*;
import galapagos.biotope.*;
import java.util.*;

/**
 * Test that the Schizophrenic Behavior works.
 * Since the Schizophrenic should just delegate all decision making 
 * to its personalities, we make a Schizophrenic with only one
 * personality (the TitForTat) and uses the test methods of the TitForTat.
 */
public class SchizophrenicTitForTatTest extends TitForTatTest {
    public Behavior getBehavior() {
        List<Behavior> titForTat = new ArrayList<Behavior>(1);
        titForTat.add(new TitForTat());
        return new Schizophrenic("Schizophrenic Tit for Tat", titForTat);
    }
    
    public String behaviorName () {
        return "Schizophrenic Tit for Tat";
    }
}
