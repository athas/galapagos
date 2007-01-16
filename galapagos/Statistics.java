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
     * Represents a element in a statistic
     *
     */
    public enum StatisticsElement {
        POPULATION ("Population"),
        BORN_THIS_ROUND ("Born"),
        DEAD_TICKS_THIS_ROUND ("Dead by ticks"),
        DEAD_AGE_THIS_ROUND ("Dead by age"),
        BORN_TOTAL ("Total born"),
        DEAD_TICKS_TOTAL ("Total dead by ticks"),
        DEAD_AGE_TOTAL ("Total dead by age");
        
        public final String name;
        StatisticsElement (String name)
        {
            this.name = name;
        }
    }
    
    public int getStatByElement(StatisticsElement element) {
        switch(element)
        {
            case POPULATION:
                return population;
                
            case BORN_THIS_ROUND:
                return bornThisRound;
                
            case DEAD_TICKS_THIS_ROUND:
                return deadByAgeThisRound;
                
            case DEAD_AGE_THIS_ROUND:
                return deadByTicksThisRound;
                
            case BORN_TOTAL:
                return born;
                
            case DEAD_TICKS_TOTAL:
                return deadByTicks;
                
            case DEAD_AGE_TOTAL:
                return deadByAge;
                
            default:
                return 0;
        }
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