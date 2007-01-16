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
    
    /**
     * The number of finches.
     */
    public int getPopulation () {
        return population;
    }
    
    /**
     * The number of finches born.
     */
    public int getBorn () {
        return born;
    }
    
    /**
     * The number of finches that has died by age.
     */
    public int getDeadByAge () {
        return deadByAge;
    }
    
    /**
     * The number of finches that has died by ticks.
     */
    public int getDeadByTicks () {
        return deadByTicks;
    }
    
    /**
     * The number of finches born in this round.
     */
    public int getBornThisRound () {
        return bornThisRound;
    }
    
    /**
     * The number number of finches dead by age in this round.
     */
    public int getDeadByAgeThisRound () {
        return deadByAgeThisRound;
    }
    
    /**
     * The number of finches dead by ticks in this round.
     */
    public int getDeadByTicksThisRound () {
        return deadByTicksThisRound;
    }
    
    /**
     * Increase the population by 1.
     */
    public void incPopulation () {
        population++;
    }
    
    /**
     * Decrease the population by 1.
     */
    public void decPopulation () {
        population--;
    }
    
    /**
     * Increase the number of finches born in total and the number of finches
     * born this round by one.
     */
    public void incBorn () {
        born++;
        bornThisRound++;
    }
    
    /**
     * Increase the number of finches dead by age in total and the number of finches
     * dead by age this round by 1.
     */
    public void incDeadByAge () {
        deadByAge++;
        deadByAgeThisRound++;
    }
    
    /**
     * Increase the number of finches dead by ticks in total and the number of finches
     * dead by ticks this round by 1.
     */
    public void incDeadByTicks () {
        deadByTicks++;
        deadByTicksThisRound++;
    }
}