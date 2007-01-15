package galapagos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class BiotopeController implements ActionListener {
    Biotope biotope;
    GalapagosFrame frame;
    Timer roundTimer;
    int roundsToGo;
    
    public BiotopeController (GalapagosFrame frame, Biotope biotope) {
        this.frame = frame;
        this.biotope = biotope;
        roundTimer = new Timer(500, this);
        roundTimer.stop();
        roundsToGo = 0;
    }
    
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
        }
    }
}
