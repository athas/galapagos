package galapagos;

import java.awt.event.*;
import javax.swing.Timer;
import javax.swing.event.*;

public class BiotopeController implements ActionListener, ChangeListener {
    private Biotope biotope;
    private final GalapagosFrame frame;
    private final Timer roundTimer;
    private int roundsToGo;
    private boolean unlimited;
    
    /**
     * Create a new BiotopeController, controlling the specified Biotope according to data from frame.
     */
    public BiotopeController (GalapagosFrame frame, Biotope biotope) {
        this.frame = frame;
        this.biotope = biotope;
        roundTimer = new Timer(200, this);
        roundTimer.stop();
        roundsToGo = 0;
    }
    
    /**
     * Specify a new Biotope for this BiotopeController to control.
     */
    public void setBiotope (Biotope biotope) {
        this.biotope = biotope;
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == roundTimer) {
            if (unlimited) {
                biotope.runRound();
            } else {
                if (roundsToGo > 0) {
                    roundsToGo--;
                    biotope.runRound();
                }
                if (roundsToGo <= 0)
                    roundTimer.stop();
            }
            return;
        }
        
        String command = e.getActionCommand();
        if (command.equals("nextRound")) {
            unlimited = false;
            roundsToGo = 0;
            roundTimer.stop();
            biotope.runRound();
        } else if (command.equals("severalRounds")) {
            unlimited = false;
            roundsToGo += frame.getNumberOfRounds();
            roundTimer.start();
        } else if (command.equals("unlimitedRounds")) {
            unlimited = true;
            roundsToGo = 0;
            roundTimer.start();
        } else if (command.equals("stopRounds")) {
            unlimited = false;
            roundsToGo = 0;
            roundTimer.stop();
        } else if (command.equals("newBiotope")) {
            frame.biotopeCreator.openPanel();
        } else if (command.equals("cancelButton")) {
            frame.biotopeCreator.close();
        } else if (command.equals("okButton")) {
            frame.biotopeCreator.createBiotope();
        }
    }
    
    public void stateChanged (ChangeEvent e) {
        roundTimer.setDelay(frame.getTimerInterval());
    }
}
