package galapagos;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class BiotopeLogger implements Observer {
    private static final int COLUMNS = 5;
    private static final String[] TOP_ROW = {"Behavior", "Population", "Born", "Dead by Age", "Dead by Ticks"};
    private static int[] topRowSizes;
    
    public BiotopeLogger() {
        topRowSizes = new int[COLUMNS];
        for (int i = 0; i < COLUMNS; i++)
            topRowSizes[i] = TOP_ROW[i].length();
    }
    
    private StringBuilder flushLeft (String str, int fieldLength) {
        int strLength = str.length();
        StringBuilder result = new StringBuilder(fieldLength);
        result.append(str);
        for (int i = fieldLength - strLength; i > 0; i--)
            result.append(' ');
        return result;
    }
    
    private StringBuilder flushRight (String str, int fieldLength) {
        int strLength = str.length();
        StringBuilder result = new StringBuilder(fieldLength);
        for (int i = fieldLength - strLength; i > 0; i--)
            result.append(' ');
        result.append(str);
        return result;
    }
    
    public void update(Observable observable, Object arg) {
        Biotope biotope = (Biotope) observable;
        List<Behavior> behaviors = biotope.behaviors(); 
        int rows = behaviors.size() + 2;
        int[] columnSizes = topRowSizes;
        String[][] output = new String[rows][COLUMNS];
        output[0] = TOP_ROW;
        
        Integer populationTotal = 0;
        Integer bornTotal = 0;
        Integer deadByAgeTotal = 0;
        Integer deadByTicksTotal = 0;
        Statistics currentStat;
        Behavior currentBehavior;
        String[] currentRow = new String[COLUMNS];
        
        for (int i = 0; i < rows - 2; i++) {
            currentBehavior = behaviors.get(i);
            currentStat = biotope.statistics(currentBehavior);
            currentRow = output[i+1];
            
            currentRow[0] = currentBehavior.toString();
            currentRow[1] = ((Integer) currentStat.getPopulation()).toString();
            populationTotal += currentStat.getPopulation();
            currentRow[2] = ((Integer) currentStat.getBornThisRound()).toString();
            bornTotal += currentStat.getBornThisRound();
            currentRow[3] = ((Integer) currentStat.getDeadByAgeThisRound()).toString();
            deadByAgeTotal += currentStat.getDeadByAgeThisRound();
            currentRow[4] = ((Integer) currentStat.getDeadByTicksThisRound()).toString();
            deadByTicksTotal += currentStat.getDeadByTicksThisRound();
            
            for (int j = 0; j < COLUMNS; j++)
                if (currentRow[j].length() > columnSizes[j])
                    columnSizes[j] = currentRow[j].length();
        }
        currentRow = output[rows-1];
        currentRow[0] = "Total";
        currentRow[1] = populationTotal.toString();
        currentRow[2] = bornTotal.toString();
        currentRow[3] = deadByAgeTotal.toString();
        currentRow[4] = deadByTicksTotal.toString();
        for (int j = 0; j < COLUMNS; j++)
            if (currentRow[j].length() > columnSizes[j])
                columnSizes[j] = currentRow[j].length();
        
        System.out.println("----------------");
        System.out.println("Round number " + biotope.round() + ":");
        for (int i = 0; i < rows; i++) {
            currentRow = output[i];
            StringBuilder currentLine = flushLeft(currentRow[0], columnSizes[0]);
            for (int j = 1; j < COLUMNS; j++)
                currentLine.append(" | ").append(flushRight(currentRow[j], columnSizes[j]));
            System.out.println(currentLine);
        }
    }
}
