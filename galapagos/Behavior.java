package galapagos;

public interface Behavior {
  public Action decide (Finch finch);
  public void response (Finch finch, Action action);
}