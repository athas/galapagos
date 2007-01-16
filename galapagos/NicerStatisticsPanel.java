package galapagos;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class NicerStatisticsPanel extends JPanel implements Observer {
    //The number of columns in the output-table, computed by the number of StatisticElements
    private static final int COLUMNS;
    
    //The top row of the table, containing headers of the table columns.
    private static final JLabel[] TITLE_ROW;
    
    private static final Font font = new Font("InputDialog", Font.BOLD, 14);
    
    static {
        //the number of columns is the number of statistic elements + one for the behaviornames
        Statistics.StatisticElement[] statElements = Statistics.StatisticElement.values();
        COLUMNS = 1 + statElements.length;
        
        TITLE_ROW = new JLabel[COLUMNS];
        
        //First column title
        TITLE_ROW[0] = new JLabel("Behavior");
        TITLE_ROW[0].setFont(font);
        
        //The statisticelement names next
        for(int i = 0; i < statElements.length; ++i) {
            TITLE_ROW[i+1] = new JLabel(statElements[i].name);
            TITLE_ROW[i+1].setHorizontalAlignment(SwingConstants.RIGHT);
            TITLE_ROW[i+1].setFont(font);
        }

    }
    
    private JLabel[][] informationLabels;
    private Map<String, Color> colorMap;
    private int numberOfRows;
    
    /**
     * Create a new StatisticsPanel for monitoring a Biotope.
     * @param frame The StatisticsPanel uses the colors specified in frame.
     */
    public NicerStatisticsPanel(Map<String, Color> colorMap) {
        setLayout(new GridBagLayout());
        this.colorMap = colorMap;

        numberOfRows = colorMap.size() + 2;
        
        informationLabels = new JLabel[numberOfRows][COLUMNS];
        informationLabels[0] = TITLE_ROW;
        
        for(int j = 0; j < COLUMNS; ++j)
        {
            this.add(informationLabels[0][j], getConstraints(j, 1));
        }
        
        for(int i = 1; i < numberOfRows; ++i)    
        {
            JLabel[] row = new JLabel[COLUMNS];
            
            for(int j = 0; j < COLUMNS; ++j)
            {
                row[j] = new JLabel();
                row[j].setFont(font);
                row[j].setHorizontalAlignment(SwingConstants.RIGHT);
                this.add(row[j], getConstraints(j, i+1));
            }
            
            row[0].setHorizontalAlignment(SwingConstants.LEFT);
            
            informationLabels[i] = row;
        }
    }
    
    /**
     * A set of GridBagConstraints for use with the GridBagLayout.
     * @param x the horisontal position of the component.
     * @param y the vertical position of the component.
     * @param anchor the alignment of the component inside its display area.
     */
    private GridBagConstraints getConstraints (int x, int y) {
        return new GridBagConstraints(x,y,1,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
    }
    
    public void update(Observable observableBiotope, Object arg) {
        Biotope biotope = (Biotope) observableBiotope;
        List<Behavior> behaviors = biotope.behaviors();
        Statistics.StatisticElement[] statElements = Statistics.StatisticElement.values();

        int numberOfBehaviors = behaviors.size(); // The number of rows in the output table - including the header row and a row containing totals.

        // Array accumulating the data for the "totals"-row of the table.
        int totals[] = new int[statElements.length];
        
        { //iterate through behaviors and create a statistic rows
            int i = 0;
            for (Behavior currentBehavior : behaviors) {
                // Variables containing data of the current row in the table (and the corresponding Behavior).
                Statistics currentStat = biotope.statistics(currentBehavior.toString());
                JLabel[] currentRow = informationLabels[i+1];
                
                Color color = colorMap.get(currentBehavior.toString());
                currentRow[0].setText(currentBehavior.toString());
                currentRow[0].setForeground(color);
                currentRow[0].setVisible(true);
                
                for(Statistics.StatisticElement element : statElements)
                {
                    int value = currentStat.getStatByElement(element);
                    currentRow[element.ordinal()+1].setText(Integer.toString(value));
                    currentRow[element.ordinal()+1].setForeground(color);
                    currentRow[element.ordinal()+1].setVisible(true);
                    
                    //The totals are accumulated.
                    totals[element.ordinal()] += value;
                }
                i++;
            }
        }
        
        JLabel[] totalsRow = informationLabels[numberOfBehaviors+1];
        totalsRow[0].setText("Totals");
        totalsRow[0].setForeground(Color.BLACK);
        totalsRow[0].setVisible(true);
        
        for(int i = 0; i < totals.length; ++i) {
            totalsRow[i+1].setText(Integer.toString(totals[i]));
            totalsRow[i+1].setForeground(Color.BLACK);
            totalsRow[i+1].setVisible(true);
        }
        
        // Set the remaining rows to invisible.
        for(int i = numberOfBehaviors+2; i < numberOfRows; ++i)
            for (int j = 0; j < COLUMNS; ++j)
                informationLabels[i][j].setVisible(false);
    }

}
