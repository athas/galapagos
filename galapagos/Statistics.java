package galapagos;

/**
 * A class for remembering importent data about one type of finch
 * (i.e. all the finches having one certain behavior).
 */
public class Statistics {
    
    // Vairables used through the whole simulation.
    private int population;
    private int born;
    private int deadByAge;
    private int deadByTicks;
    
    // Variables reset each round.
    private int bornThisRound;
    private int deadByAgeThisRound;
    private int deadByTicksThisRound;
    
    /**
     * Create statistics about a finch type.
     */
    public Statistics () {
        population = 0;
        born = 0;
        deadByAge = 0;
        deadByTicks = 0;
        bornThisRound = 0;
        deadByAgeThisRound = 0;
        deadByTicksThisRound = 0;
    }
    
    /**
     * Reset round variables.
     */
    public void newRound () {
        bornThisRound = 0;
        deadByAgeThisRound = 0;
        deadByTicksThisRound = 0;
    }
    
    public int getPopulation () {
        return population;
    }
    
    public int getBorn () {
        return born;
    }
    
    public int getDeadByAge () {
        return deadByAge;
    }
    
    public int getDeadByTicks () {
        return deadByTicks;
    }
    
    public int getBornThisRound () {
        return bornThisRound;
    }
    
    public int getDeadByAgeThisRound () {
        return deadByAgeThisRound;
    }
    
    public int getDeadByTicksThisRound () {
        return deadByTicksThisRound;
    }
    
    public void incPopulation () {
        population++;
    }
    
    public void decPopulation () {
        population--;
    }
    
    public void incBorn () {
        born++;
        bornThisRound++;
    }
    
    public void incDeadByAge () {
        deadByAge++;
        deadByAgeThisRound++;
    }
    
    public void incDeadByTicks () {
        deadByTicks++;
        deadByTicksThisRound++;
    }
}