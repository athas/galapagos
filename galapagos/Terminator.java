package galapagos;
import java.awt.*;
import java.awt.event.*;

class Terminator extends WindowAdapter
{
  
  public void windowClosing(WindowEvent e)
  {
    Window w = e.getWindow();
    w.dispose();
  }
  
  public void windowClosed(WindowEvent e)
  {
    System.exit(0);
  }
}
