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
        roundTimer = new Timer(0, this);
        roundTimer.stop();
        roundsToGo = 0;
        numberOfRounds = 0;
    }
    
    /**
     * Specify a new Biotope for this BiotopeController to control.
     */
    public void setBiotope (Biotope biotope) {
        this.biotope = biotope;
    }
    
    public void stopSimulation() {
    	roundTimer.stop();
    	roundsToGo = 0;
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
            roundsToGo = numberOfRounds;
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

    /**
     * Add a finch with a behavior of the provided type to the Biotope
     * controlled by this BiotopeController. Any finch already at the
     * provided position will be replaced.
     *
     * @param x The x coordinate of the position of the inserted finch.
     * @param y The y coordinate of the position of the inserted finch.
     * @param b A behavior object of the type that the new finch
     * should have.
     *
     * @require 0 <= x < biotope-world-width
     * @require 0 <= y <= biotope-world-height
     */
    public void putFinch(int x, int y, Behavior b) {
        biotope.putFinch(x, y, b);
    }
}
