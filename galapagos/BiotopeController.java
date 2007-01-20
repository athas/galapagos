package galapagos;

import java.awt.event.*;
import java.util.*;
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
        roundTimer = new Timer(0, new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
                if (unlimited) {
                	BiotopeController.this.biotope.runRound();
                } else {
                    if (roundsToGo > 0) {
                        roundsToGo--;
                        BiotopeController.this.biotope.runRound();
                    }
                    
                    if (roundsToGo <= 0)
                        roundTimer.stop();
                }
        	}
        });
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
    
    public void actionPerformed(ActionEvent e) {        
        String command = e.getActionCommand();
        
        if (command.equals("nextRound"))
        	nextRound();
        else if (command.equals("severalRounds"))
        	runSeveralRounds(numberOfRounds);
        else if (command.equals("unlimitedRounds"))
        	loop();
        else if (command.equals("stopRounds"))
        	stopSimulation();
    }
    
    public void runSeveralRounds(int rounds) {
    	unlimited = false;
    	roundsToGo = rounds;
    	roundTimer.start();
    }
    
    public void nextRound() {
    	stopSimulation();
        biotope.runRound();
    }
    
    public void stopSimulation() {
        unlimited = false;
        roundsToGo = 0;
        roundTimer.stop();
    }
    
    public void loop() {
    	unlimited = true;
        roundsToGo = 0;
        roundTimer.start();
    }
    
    public void delay(int value) {
    	roundTimer.setDelay(value);
    }
    
    public void stateChanged (ChangeEvent e) {
    	JSpinner spinner = (JSpinner)e.getSource(); 
    	
    	int value = (Integer)spinner.getValue();
    	
    	if(spinner.getName().equals("timerIntervalSpinner"))
    		delay(value);
    	else if(spinner.getName().equals("numberOfRoundsSpinner"))
    		this.numberOfRounds = value;
    }

    /**
     * Interface used in lieu of true higher-order
     * functions. Subclasses are meant to be used for iterating over a
     * set of points in a coordinate system.
     */
    private interface pointFrobber {
        /**
         * Perform an action.
         *
         * @param x The x coordinate of the point.
         * @param y The y coordinate of the point.
         */
        public void call(int x, int y);
    }

    /**
     * For the points inside the specified circle, invoke the call()
     * on the provided pointFrobber.
     *
     * @param centerX The x coordinate of the center of the circle.
     * @param centerY The y coordinate of the center of the circle.
     * @param radius The (approximate) radius of the circle.
     * @param frobber The object that the call() method will be
     * invoked upon for each point that lies inside the circle.
     */
    private void forPointsInCircle(int centerX, int centerY, 
                                   int radius, pointFrobber frobber) {
        for (int x = -(radius + 2); x < radius + 1; x++)
            for (int y = -(radius + 2); y < radius + 1; y++)
                if ((x * x + y * y) < radius * radius)
                    frobber.call(centerX + x, centerY + y);
    }

    /**
     * Add a circle of finches with a behavior of the provided type to
     * the Biotope controlled by this BiotopeController. Any finches
     * already at the provided positions will be replaced.
     *
     * @param centerX The x coordinate of the center of the circle.
     * @param centerY The y coordinate of the center of the circle.
     * @param radius The (approximate) radius of the circle that
     * will be filled with finches.
     * @param b A behavior object of the type that the new finch
     * should have.
     *
     * @require 0 <= x < biotope-world-width
     * @require 0 <= y <= biotope-world-height
     */
    public void putFinches(int centerX, int centerY, int radius, final Behavior b) {
        assert (0 <= centerX && centerX <= biotope.world.width() &&
                0 <= centerY && centerY <= biotope.world.height())
            : "Provided coordinate (" + centerX + ", " + centerY + ") lies outside the world.";
            
        final List<Biotope.FinchDescriptor> list = new LinkedList<Biotope.FinchDescriptor>();

        forPointsInCircle(centerX, centerY, radius, new pointFrobber() {
                public void call(int x, int y) {
                    list.add(biotope.new 
                             AddFinchDescriptor(biotope.world.wrappedX(x),
                                                biotope.world.wrappedY(y), 
                                                b));
                }
            });
        
        biotope.applyDescriptors(list);
    }

    /**
     * Remove the finches in the circle at the specified position with
     * the specified radius. If there are no finches at that location,
     * calling this method is a no-op.
     *
     * @param centerX The x coordinate of the position of the circle.
     * @param centerY The y coordinate of the position of the circle.
     * @param radius The radius of the circle.
     *
     * @require 0 <= x < biotope-world-width
     * @require 0 <= y <= biotope-world-height
     */
    public void takeFinches(int centerX, int centerY, int radius) {
        assert (0 <= centerX && centerX <= biotope.world.width() &&
                0 <= centerY && centerY <= biotope.world.height())
            : "Provided coordinate (" + centerX + ", " + centerY + ") lies outside the world.";
        
        final List<Biotope.FinchDescriptor> list = new LinkedList<Biotope.FinchDescriptor>();

        forPointsInCircle(centerX, centerY, radius, new pointFrobber() {
                public void call(int x, int y) {
                    list.add(biotope.new 
                             RemoveFinchDescriptor(biotope.world.wrappedX(x), 
                                                   biotope.world.wrappedY(y)));
                }
            });

        biotope.applyDescriptors(list);
    }
}
