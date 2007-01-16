package galapagos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class BiotopeController implements ActionListener {
    Biotope biotope;
    GalapagosFrame frame;
    Timer roundTimer;
    int roundsToGo;
    
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
            if (roundsToGo > 0) {
                roundsToGo--;
                biotope.runRound();
            }
            if (roundsToGo <= 0)
                roundTimer.stop();
            return;
        }
        
        String command = e.getActionCommand();
        if (command.equals("nextRound")) {
            roundTimer.stop();
            biotope.runRound();
        } else if (command.equals("severalRounds")) {
            roundsToGo += frame.getNumberOfRounds();
            roundTimer.start();
        } else if (command.equals("stopRounds")) {
            roundTimer.stop();
        } else if (command.equals("newBiotope")) {
            frame.biotopeCreator.openPanel();
        } else if (command.equals("cancelButton")) {
            frame.biotopeCreator.abort();
        } else if (command.equals("okButton")) {
            frame.biotopeCreator.createBiotope();
        }
    }
}
