package galapagos;

public interface Behavior {
  public Action decide (Finch);
  public void response (Finch, Action);
}