package galapagos;

import java.util.*;

/**
 * A behavior using the suspicious variation of the tit for
 tat-strategy.
 */
public class SuspiciousTitForTat extends TitForTat {
  private Map<Finch,Action> finches;
  
  /**
   * Decide what to do to the finch just met. If these finches has met before this finch
   * does what the other did last time.
   * @ensure finches.containsKey(finch) implies decide == finches.get(finch)
   */
  public Action decide (Finch finch) {
    if (finches.containsKey(finch)) {
      return finches.get(finch);
    } else return Action.IGNORING;
  }
  
  public Behavior clone() {
      return new SuspiciousTitForTat();
  }
  
  public String toString() {
      return "TitForTat";
  }
}
