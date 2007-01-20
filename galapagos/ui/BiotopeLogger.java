package galapagos.ui;

import galapagos.biotope.*;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * A BiotopeLogger prints (to System.out) a table containing statistics for the current round in a Biotope-simulation. 
 */
public class BiotopeLogger implements Observer {
    //The number of columns in the output-table, computed by the number of StatisticElements
    private static final int COLUMNS;
    private static final char COLUMN_SEPERATOR = '|';
    private static final char ROW_SEPERATOR = 'X';
    
    //The top row of the table, containing headers of the table columns.
    private static final String[] TITLE_ROW;
    
    static {
        //the number of columns is the number of statistic elements + one for the behaviornames
        Statistics.StatisticsElement[] statElements = Statistics.StatisticsElement.values();
        COLUMNS = 1 + statElements.length;
        TITLE_ROW = new String[COLUMNS];
        
        //First column title
        TITLE_ROW[0] = "Behavior";
        
        //The statisticelement names next
        for(int i = 0; i < statElements.length; ++i) {
            TITLE_ROW[i+1] = statElements[i].name;
        }
    }
    
    public BiotopeLogger() {
        
    }
    
    /**
     * A StringBuilder containing str and additional spaces (at the end), the combined length is fieldLength if 
     * <code>str.length() < fieldLength</code>.
     * If <code>str.length() >= fieldLength</code>, the returned StringBuilder is only containing str. 
     */
    private StringBuilder flushLeft (String str, int fieldLength) {
        int strLength = str.length();
        StringBuilder result = new StringBuilder(fieldLength);
        result.append(str);
        for (int i = fieldLength - strLength; i > 0; i--)
            result.append(' ');
        return result;
    }
    
    /**
     * A StringBuilder containing str and additional spaces (at the beginning), the combined length is fieldLength if 
     * <code>str.length() < fieldLength</code>.
     * If <code>str.length() >= fieldLength</code>, the returned StringBuilder is only containing str. 
     */
    private StringBuilder flushRight (String str, int fieldLength) {
        int strLength = str.length();
        StringBuilder result = new StringBuilder(fieldLength);
        for (int i = fieldLength - strLength; i > 0; i--)
            result.append(' ');
        result.append(str);
        return result;
    }
    
    /**
     * Prints a table of round statistics from the last round in observableBiotope.
     * @require <code>observableBiotope instanceof Biotope</code>
     */
    public void update(Observable observableBiotope, Object arg) {
        Biotope biotope = (Biotope) observableBiotope;
        List<Behavior> behaviors = biotope.behaviors();
        Statistics.StatisticsElement[] statElements = Statistics.StatisticsElement.values();

        int numberOfRows = behaviors.size() + 2; // The number of rows in the output table - including the header row and a row containing totals.

        // An array for the text in the tablecells.
        String[][] outputTable = new String[numberOfRows][COLUMNS]; 
        
        // The first row in the table is the title row.
        outputTable[0] = TITLE_ROW;
        
        // Array accumulating the data for the "totals"-row of the table.
        int totals[] = new int[statElements.length];
        
        { //iterate through behaviors and create a statistic rows
            int i = 0;
            for (Behavior currentBehavior : behaviors) {
                // Variables containing data of the current row in the table (and the corresponding Behavior).
                Statistics currentStat = biotope.statistics(currentBehavior);
                String[] currentRow = outputTable[i+1];
                
                // The texts of the fields in the table are generated, and added to the output-array.
                currentRow[0] = currentBehavior.toString();
                
                for(Statistics.StatisticsElement element : statElements)
                {
                    int value = currentStat.getStatByElement(element);
                    currentRow[element.ordinal()+1] = Integer.toString(value);
                    
                    //The totals are accumulated.
                    totals[element.ordinal()] += value;
                }
                
                if(currentStat.getStatByElement(Statistics.StatisticsElement.POPULATION) == 0)
                    currentRow[1] = "EXTINCT";
                
                i++;
            }
        }
        
        // The texts of the totals row are generated, and the column sizes are increased if necessary.
        String[] totalsRow = new String[COLUMNS];
        totalsRow[0] = "Total"; //first column is the columnname
        
        //the rest is found in totals[]
        for(int i = 0; i < totals.length; ++i)
            totalsRow[i+1] = Integer.toString(totals[i]); 
        
        if(totals[0] == 0)
            totalsRow[1] = "APOCALYPSE";
        
        outputTable[numberOfRows-1] = totalsRow;
        
        int[] columnSizes = computeColumnSizes(outputTable);
        
        //compute the complete width of the table, for the horizontal line
        int outputWidth = 0;
        for (int j = 0; j < COLUMNS; ++j)
            outputWidth += columnSizes[j];
        //Column seperator compensation
        outputWidth += (COLUMNS - 1) * 3;
        
        // Initial lines of output before the table.
        System.out.println("Round number " + biotope.round() + ":");
        System.out.println(repeatCharacter(ROW_SEPERATOR, 30));
        
        // The lines of the table are created and printed.
        for (int i = 0; i < numberOfRows; i++) {
            //Create a horizontal line just before the last row
            if(i == numberOfRows - 1) 
                System.out.println(repeatCharacter(ROW_SEPERATOR, outputWidth));
            
            
            System.out.println(createTableRow(outputTable[i], columnSizes));
        }
        System.out.println(repeatCharacter(ROW_SEPERATOR, outputWidth));
    }
    
    /**
     * Compute the column sizes of a String-table. 
     * @return An array containing the widths of the columns.
     */
    private int[] computeColumnSizes(String[][] table) {
        int[] columnSizes = new int[COLUMNS];
        
        for(String[] row : table)
            for(int i = 0; i < COLUMNS; ++i)
            {
                //note this cells size if it is bigger than the noted for the column
                if(row[i] != null && row[i].length() > columnSizes[i])
                    columnSizes[i] = row[i].length();
            }
        return columnSizes;
    }
    
    /**
     * Creates a string representation of a row in the output table, adding column delimiters.
     * @param row The row in the table.
     * @param columnSizes The sizes of the columns.
     * @return The string representation of the row
     */
    private String createTableRow(String[] row, int[] columnSizes) {
        // the first row should be left-orientated
        StringBuilder currentLine = flushLeft(row[0], columnSizes[0]);
        for (int j = 1; j < COLUMNS; j++) {
            //add a seperator before each column
            currentLine.append(' ');
            currentLine.append(COLUMN_SEPERATOR);
            currentLine.append(' ');

            //the values should be right-orientated 
            currentLine.append(flushRight(row[j], columnSizes[j])); 
        }
        return currentLine.toString();
    }
    
    /**
     * Create a string with a specific number of repetitions of a character
     * @param character The character that should be repeated
     * @param times How many times the character should be repeated
     */
    private String repeatCharacter(char character, int times) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < times; i++)
            builder.append(character);
        
        return builder.toString();
    }
}
