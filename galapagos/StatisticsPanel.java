package galapagos;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class StatisticsPanel extends JPanel implements Observer {
    public TreeMap<String, Color> colorMap;
    private JLabel[] roundLabels; // Labels for the round counter.
    
    // Labels for the table headers.
    private static JLabel[] headerLabels = {new JLabel("Behavior", SwingConstants.LEFT), new JLabel("Population", SwingConstants.RIGHT),
        new JLabel("Born", SwingConstants.RIGHT), new JLabel("Total Born", SwingConstants.RIGHT), new JLabel("Dead by Ticks", SwingConstants.RIGHT),
        new JLabel("Total Dead by Ticks", SwingConstants.RIGHT), new JLabel("Dead by Age", SwingConstants.RIGHT), new JLabel("Total Dead by Age", SwingConstants.RIGHT)};
    
    private static int columns = 8; // Number of columns in the table.
    private int numberOfBehaviors; // Number of possible behaviors. The number of behaviors with specified colors.
    private String[] behaviors; // The names of the possible behaviors.
    private JLabel[] behaviorLabels; // Labels for the behavior names. The leftmost column of the table.
    private JLabel[][] informationLabels; // Labels for containing til statistics of the biotope.
    
    /**
     * Create a new StatisticsPanel for monitoring a Biotope.
     * @param frame The StatisticsPanel uses the colors specified in frame.
     */
    public StatisticsPanel(GalapagosFrame frame) {
        setLayout(new GridBagLayout());
        colorMap = frame.colorMap;
        
        // The round labels are created and added to the panel.
        roundLabels = new JLabel[2];
        roundLabels[0] = new JLabel("Round", SwingConstants.LEFT);
        roundLabels[1] = new JLabel("", SwingConstants.RIGHT);
        add(roundLabels[0],getConstraints(0, 0, GridBagConstraints.WEST));
        add(roundLabels[1],getConstraints(1, 0, GridBagConstraints.EAST));
        
        // The header labels are added to the panel.
        for(int j = 0; j < columns; j++)
            this.add(headerLabels[j], getConstraints(j, 1, GridBagConstraints.WEST));
        
        // The behavior and information label-arrays are created.
        numberOfBehaviors = colorMap.size();
        behaviors = new String[numberOfBehaviors];
        behaviorLabels = new JLabel[numberOfBehaviors + 1];
        informationLabels = new JLabel[numberOfBehaviors + 1][columns];
        
        // The behavior and information labels (except the totals row) are created and added to the panel.
        int i = 0;
        JLabel[] informationRow = new JLabel[columns-1];
        for (Iterator<Map.Entry<String, Color>> iterator = colorMap.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry<String, Color> entry = iterator.next(); // A pair of a Behavior name and the corresponding color.
            behaviors[i] = entry.getKey();
            
            // Behavior labels
            behaviorLabels[i] = new JLabel(entry.getKey(), SwingConstants.LEFT);
            behaviorLabels[i].setForeground(entry.getValue());
            this.add(behaviorLabels[i], getConstraints(0, i+2, GridBagConstraints.WEST));
            
            //Information labels
            informationRow = informationLabels[i];
            for (int j = 0; j < columns - 1; j++) {
                informationRow[j] = new JLabel("", SwingConstants.RIGHT);
                informationRow[j].setForeground(entry.getValue());
                this.add(informationRow[j], getConstraints(j+1, i+2, GridBagConstraints.EAST));
            }
            i++;
        }
        assert (i == numberOfBehaviors);
        
        // The labels of the totals row are created and added to the panel.
        // The last Behavior label.
        behaviorLabels[numberOfBehaviors] = new JLabel("Total", SwingConstants.LEFT);
        this.add(behaviorLabels[numberOfBehaviors], getConstraints(0, numberOfBehaviors+2, GridBagConstraints.WEST));
        
        // The last row of information labels.
        informationRow = informationLabels[numberOfBehaviors];
        for (int j = 0; j < columns - 1; j++) {
            informationRow[j] = new JLabel("", SwingConstants.RIGHT);
            this.add(informationRow[j], getConstraints(j+1, numberOfBehaviors+2, GridBagConstraints.EAST));
        }
    }
    
    /**
     * A set of GridBagConstraints for use with the GridBagLayout.
     * @param x the horisontal position of the component.
     * @param y the vertical position of the component.
     * @param anchor the alignment of the component inside its display area.
     */
    private GridBagConstraints getConstraints (int x, int y, int anchor) {
        return new GridBagConstraints(x,y,1,1,1.0,1.0,anchor,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
    }
    
    /**
     * Updates the panel when called by a Biotope.
     * @require <code>observableBiotope instanceof Biotope</code>
     */
    public void update(Observable observableBiotope, Object arg) {
        Biotope biotope = (Biotope) observableBiotope;
        Set<String> activeBehaviors = new TreeSet<String>();
        
        // A set of the behaviors in the biotope i made.
        for (Behavior b : biotope.behaviors())
            activeBehaviors.add(b.toString());
        
        // The round number is updated.
        roundLabels[1].setText(((Integer) biotope.round()).toString());
        
        // The information labels are updated.
        JLabel[] informationRow = new JLabel[columns-1];
        Statistics currentStat;
        int population = 0;
        int bornThisRound = 0;
        int born = 0;
        int deadByTicksThisRound = 0;
        int deadByTicks = 0;
        int deadByAgeThisRound = 0;
        int deadByAge = 0;
        for (int i = 0; i < numberOfBehaviors; i++) {
            informationRow = informationLabels[i]; // The row of information labels belonging to behaviors[i]. 
            
            if (activeBehaviors.contains(behaviors[i])) {
                currentStat = biotope.statistics(behaviors[i]);
                
                // If behaviors[i] is in the biotope, the statistics for behaviors[i] are shown.
                informationRow[0].setText(((Integer) currentStat.getPopulation()).toString());
                informationRow[1].setText(((Integer) currentStat.getBornThisRound()).toString());
                informationRow[2].setText(((Integer) currentStat.getBorn()).toString());
                informationRow[3].setText(((Integer) currentStat.getDeadByTicksThisRound()).toString());
                informationRow[4].setText(((Integer) currentStat.getDeadByTicks()).toString());
                informationRow[5].setText(((Integer) currentStat.getDeadByAgeThisRound()).toString());
                informationRow[6].setText(((Integer) currentStat.getDeadByAge()).toString());
                
                // The labels in the specific row of the table are made visible.
                behaviorLabels[i].setVisible(true);
                for (int j = 0; j < columns - 1; j++)
                    informationRow[j].setVisible(true);
                
                // The statistics for the totals row are accumulated.
                population += currentStat.getPopulation();
                bornThisRound += currentStat.getBornThisRound();
                born += currentStat.getBorn();
                deadByTicksThisRound += currentStat.getDeadByTicksThisRound();
                deadByTicks += currentStat.getDeadByTicks();
                deadByAgeThisRound += currentStat.getDeadByAgeThisRound();
                deadByAge += currentStat.getDeadByAge();
            } else {
                // If behaviors[i] isn't in the biotope, the labels of the corresponding table row are made invisible.
                behaviorLabels[i].setVisible(false);
                for (int j = 0; j < columns - 1; j++)
                    informationRow[j].setVisible(false);
            }
        }
        
        // The totals row is updated.
        informationRow = informationLabels[numberOfBehaviors];
        informationRow[0].setText(((Integer) population).toString());
        informationRow[1].setText(((Integer) bornThisRound).toString());
        informationRow[2].setText(((Integer) born).toString());
        informationRow[3].setText(((Integer) deadByTicksThisRound).toString());
        informationRow[4].setText(((Integer) deadByTicks).toString());
        informationRow[5].setText(((Integer) deadByAgeThisRound).toString());
        informationRow[6].setText(((Integer) deadByAge).toString());
    }
}
