package galapagos;

public interface Behavior extends Cloneable {
  public Action decide (Finch finch);
  public void response (Finch finch, Action action);
  public Behavior clone();
}