package galapagos;

public class GalapagosFinch implements Finch {
  
  private int hitpoints;
  private int maxHitpoints;
  private int age;
  private int maxAge;
  
  private Behavior behavior;
  
  GalapagosFinch (int hitpoints, int maxAge, Behavior behavior) {
    this.hitpoints = hitpoints;
    this.age = 0;
    this.maxAge = maxAge;
    this.behavior = behavior;
  }
  
  public Action decide (Finch finch) {
    return behavior.decide(finch);
  }
  
  public FinchStatus status () {
    if (hitpoints == 0) return FinchStatus.DEAD_TICKS;
    if (age > maxAge) return FinchStatus.DEAD_AGE;
      else return FinchStatus.ALIVE;
  }
  
  public void addHitpoints (int add) {
    hitpoints += add;
  }
  
  public void getOld () {
    age++;
  }
  
  public void response (Finch finch, Action action) {
    behavior.response(finch, action);
  }
} 