package galapagos.ui;

import java.awt.*;
import java.awt.event.*;

/**
 * A Terminator stop the program when a monitored window is closed.
 */
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
