package galapagos;

import java.awt.event.*;
import javax.swing.Timer;
import javax.swing.event.*;
import javax.swing.JSpinner;

public class BiotopeController implements ActionListener, ChangeListener {
    private Biotope biotope;
    private final Timer roundTimer;
    private int roundsToGo;
    private boolean unlimited;
    private int numberOfRounds;
    
    /**
     * Create a new BiotopeController, controlling the specified Biotope according to data from frame.
     */
    public BiotopeController (Biotope biotope) {
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
            roundsToGo += numberOfRounds;
            roundTimer.start();
        } else if (command.equals("unlimitedRounds")) {
            unlimited = true;
            roundsToGo = 0;
            roundTimer.start();
        } else if (command.equals("stopRounds")) {
            unlimited = false;
            roundsToGo = 0;
            roundTimer.stop();
        }
    }
    
    public void stateChanged (ChangeEvent e) {
    	JSpinner spinner = (JSpinner)e.getSource(); 
    	
    	int value = (Integer)spinner.getValue();
    	
    	if(spinner.getName().equals("timerIntervalSpinner"))
    		roundTimer.setDelay(value);
    	else if(spinner.getName().equals("numberOfRoundsSpinner"))
    		this.numberOfRounds = value;
    }
}
