package galapagos;

public class Statistics {
    private int population;
    private int born;
    private int deadByAge;
    private int deadByTicks;
    private int bornThisRound;
    private int deadByAgeThisRound;
    private int deadByTicksThisRound;
    
    public Statistics () {
        population = 0;
        born = 0;
        deadByAge = 0;
        deadByTicks = 0;
        bornThisRound = 0;
        deadByAgeThisRound = 0;
        deadByTicksThisRound = 0;
    }
    
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