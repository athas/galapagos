package galapagos;

import java.util.*;

public class TitForTat implements Behavior {
  private Map<Finch,Action> finches;
    
  public TitForTat () {
    finches = new HashMap<Finch,Action> ();
  };
  
  public Action decide (Finch finch) {
    if (finches.containsKey(finch)) {
      return finches.get(finch);
    } else return Action.CLEANING;
  }
  
  public void response (Finch finch, Action action) {
    finches.put(finch,action);
  }
  
}