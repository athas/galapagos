package galapagos;

import java.util.*;

/**
 * A behavior using the tit for tat-strategy.
 */
public class TitForTat implements Behavior {
  private Map<Finch,Action> finches;
  
  /**
   * Make a new tit for tat-behavior.
   */
  public TitForTat () {
    finches = new HashMap<Finch,Action> ();
  };
  
  /**
   * Decide what to do to the finch just met. If these finches has met before this finch
   * does what the other did last time.
   * @ensure finches.containsKey(finch) implies decide == finches.get(finch)
   */
  public Action decide (Finch finch) {
    if (finches.containsKey(finch)) {
      return finches.get(finch);
    } else return Action.CLEANING;
  }
  
  /**
   * Remembers what this finch did.
   */
  public void response (Finch finch, Action action) {
    finches.put(finch,action);
  }
  
}