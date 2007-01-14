package galapagos;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * A BiotopeLogger prints (to System.out) a table containing statistics for the current round in a Biotope-simulation. 
 */
public class BiotopeLogger implements Observer {
    private static final int COLUMNS = 5; // The number of columns in the output-table.
    private static int[] topRowSizes; // An array containing the sizes titles contained in the top row.
    
    private static final String[] TOP_ROW = {"Behavior", "Population", "Born", "Dead by Age", "Dead by Ticks"};
    // The top row of the table, containing headers of the table columns.
        
    public BiotopeLogger() {
        topRowSizes = new int[COLUMNS];
        for (int i = 0; i < COLUMNS; i++)
            topRowSizes[i] = TOP_ROW[i].length();
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
        int rows = behaviors.size() + 2; // The number of rows in the output table - including the header row and a row containing totals.
        int[] columnSizes = topRowSizes; /* An array for containing the column sizes in the output table, 
                                          * i.e. the maximum size of a field in the respective columns.
                                          */
        String[][] output = new String[rows][COLUMNS]; // An array for containing the text in the fields of the table.
        
        output[0] = TOP_ROW; // The first row in the table is the header row.
        
        // Variables accumulating the data for the "totals"-row of the table.
        Integer populationTotal = 0;
        Integer bornTotal = 0;
        Integer deadByAgeTotal = 0;
        Integer deadByTicksTotal = 0;
        
        // Variables containing data of the current row in the table (and the corresponding Behavior).
        Statistics currentStat;
        Behavior currentBehavior;
        String[] currentRow = new String[COLUMNS];
        
        for (int i = 0; i < rows - 2; i++) {
            currentBehavior = behaviors.get(i);
            currentStat = biotope.statistics(currentBehavior);
            currentRow = output[i+1];
            
            // The texts of the fields in the table are generated, and added to the output-array.
            currentRow[0] = currentBehavior.toString();
            currentRow[1] = ((Integer) currentStat.getPopulation()).toString();
            currentRow[2] = ((Integer) currentStat.getBornThisRound()).toString();
            currentRow[3] = ((Integer) currentStat.getDeadByAgeThisRound()).toString();
            currentRow[4] = ((Integer) currentStat.getDeadByTicksThisRound()).toString();
            
            // The totals are accumulated.
            populationTotal += currentStat.getPopulation();
            bornTotal += currentStat.getBornThisRound();
            deadByAgeTotal += currentStat.getDeadByAgeThisRound();
            deadByTicksTotal += currentStat.getDeadByTicksThisRound();
            
            // The column sizes are increased if necessary.
            for (int j = 0; j < COLUMNS; j++)
                if (currentRow[j].length() > columnSizes[j])
                    columnSizes[j] = currentRow[j].length();
        }
        
        // The texts of the totals row are generated, and the column sizes are increased if necessary.
        currentRow = output[rows-1];
        currentRow[0] = "Total";
        currentRow[1] = populationTotal.toString();
        currentRow[2] = bornTotal.toString();
        currentRow[3] = deadByAgeTotal.toString();
        currentRow[4] = deadByTicksTotal.toString();
        for (int j = 0; j < COLUMNS; j++)
            if (currentRow[j].length() > columnSizes[j])
                columnSizes[j] = currentRow[j].length();
        
        // Initial lines of output before the table.
        System.out.println("----------------");
        System.out.println("Round number " + biotope.round() + ":");
        
        // The current line of the output.
        StringBuilder currentLine;
        
        // The lines of the table (except the totals line) are created and printed.
        for (int i = 0; i < rows - 1; i++) {
            currentRow = output[i];
            currentLine = flushLeft(currentRow[0], columnSizes[0]);
            for (int j = 1; j < COLUMNS; j++)
                currentLine.append(" | ").append(flushRight(currentRow[j], columnSizes[j])); // The " | " are column seperators.
            System.out.println(currentLine);
        }
        
        // A horizontal line of '-'s spanning the table is created and printed.
        currentLine = new StringBuilder();
        int outputWidth = 0;
        for (int j = 0; j < COLUMNS; j++)
            outputWidth += columnSizes[j];
        outputWidth += (COLUMNS - 1) * 3; // To compensate for the column seperators.
        for (int i = 0; i < outputWidth; i++)
            currentLine.append('-');
        System.out.println(currentLine);
        
        // The totals row of the table is created and printed.
        currentRow = output[rows - 1];
        currentLine = flushLeft(currentRow[0], columnSizes[0]);
        for (int j = 1; j < COLUMNS; j++)
            currentLine.append(" | ").append(flushRight(currentRow[j], columnSizes[j]));
        System.out.println(currentLine);
    }
}
