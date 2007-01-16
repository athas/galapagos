package galapagos;

import java.util.*;
import java.lang.*;

public class Biotope extends Observable {
    public final int width, height;
    private final double breedingProbability;
    private final int maxHitpoints, initialHitpoints, hitpointsPerRound;
    private final int minMaxAge, maxMaxAge;
    private final int finchesPerBehavior;
    private int round;
    public final World<GalapagosFinch> world;
    private final TreeMap<String,Statistics> statisticsTree;
    private final List<Behavior> finchBehaviors;

    private final static int HelpedGotHelpValue = 3;
    private final static int HelpedDidntGetHelpValue = 0;
    private final static int DidntHelpGotHelpValue = 5;
    private final static int DidntHelpDidntGetHelpValue = 1;
    private ArrayList<Boolean> engagedFinches;
    
    public Biotope (List<Behavior> behaviors) {
        this(100, 100, 1.00/3.00, 12, 7, 3, 10, 13, 40, behaviors);
    }
    
    public Biotope (int width, int height, double breedingProbability, int maxHitpoints, 
            int initialHitpoints, int hitpointsPerRound, int minMaxAge, int maxMaxAge,
            int finchesPerBehavior, List<Behavior> behaviors) {
        this.width = width;
        this.height = height;
        this.breedingProbability = breedingProbability;
        this.maxHitpoints = maxHitpoints;
        this.initialHitpoints = initialHitpoints;
        this.hitpointsPerRound = hitpointsPerRound;
        this.minMaxAge = minMaxAge;
        this.maxMaxAge = maxMaxAge;
        this.finchesPerBehavior = finchesPerBehavior;
        this.finchBehaviors = behaviors;
        world = new World<GalapagosFinch>(width, height);
        statisticsTree = new TreeMap<String,Statistics>();
        initialize();
    }
    
    /**
     * Do initialization of objects common to all constructors.
     */
    private void initialize () {
        engagedFinches = new ArrayList<Boolean>(width * height);
        
        for (int i = 0; i < width * height; i++)
            engagedFinches.add(false);
        
        addStartFinches();
    }
    
    private void addStartFinches()
    {
        Iterator<World<GalapagosFinch>.Place> worldIterator = world.randomIterator();
        for (Iterator<Behavior> bIterator = finchBehaviors.iterator();
             bIterator.hasNext();)
        {
            Behavior b = bIterator.next();
            statisticsTree.put(b.toString(),new Statistics());
            
            
            for (int i = 0;i < finchesPerBehavior && worldIterator.hasNext();i++)
                {   
                    World<GalapagosFinch>.Place p = worldIterator.next();
                    placeFinch(p,b,false);
                }

        }
    }
    
    private void placeFinch (World<GalapagosFinch>.Place p,Behavior b,Boolean born)
    {
        Statistics stat = statisticsTree.get(b.toString());
        stat.incPopulation();
        GalapagosFinch finch = new GalapagosFinch(initialHitpoints,maxHitpoints,randomMaxAge(),b);
        if (born) 
            stat.incBorn();
        else
            finch.makeOlder();
        p.setElement(finch);
    }
        
    private int randomMaxAge () {
        return minMaxAge + (int)(Math.random() * (maxMaxAge - minMaxAge));
    }

    public void runRound () {
        for (Statistics stat : statisticsTree.values())
            stat.newRound();
        breed();
        makeMeetings();
        grimReaper();
        round++;
        setChanged();
        notifyObservers();
    }
    
    /**
     * Lets some of the finches breed.   if possible (A finch needs an empty neighbour place to breed).
     *
     */
    private void breed () {
        for (Iterator<World<GalapagosFinch>.Place> i = world.randomIterator(); i.hasNext();) {
            World<GalapagosFinch>.Place place = i.next();
            GalapagosFinch finch = place.getElement();
            if (finch != null && finch.age() > 0 && Math.random() <= breedingProbability) {
                List<World<GalapagosFinch>.Place> neighbours = place.emptyNeighbours(); 
                if (!neighbours.isEmpty())
                    placeFinch(neighbours.get(0), finch.behavior().clone(), true);
            }
        }
    }
    
    /**
     * Sets up meetings between the finches of the world. 
     *
     */
    private void makeMeetings() {
        clearEngagementKnowledge();
        for (Iterator i = world.randomIterator(); i.hasNext(); ) {
            World<GalapagosFinch>.Place place = (World.Place) i.next();
            if(place.getElement() != null && !isEngaged(place))
                makeMeeting(place);
        }
    }

    /**
     * Engages the finch at the specified place with one of its neighbours (if any). 
     * And makes them meet eachother.
     * @require place.getElement() != null && !isEngaged(place)
     * @ensure isEngaged(place)
     * @param place the place holding the unengaged finch
     */
    private void makeMeeting(World<GalapagosFinch>.Place place) {
        assert (place.getElement() != null) : "Can't engage a null-finch";
        assert (!isEngaged(place)) : "The finch is already engaged";

        List<World<GalapagosFinch>.Place> filledNeighbours = place.filledNeighbours(); 

        for (World<GalapagosFinch>.Place p : filledNeighbours)
            if (!isEngaged(p)) {
                engage(p);
                engage(place);
                meet(place.getElement(), p.getElement());
                return;
            }
    }

    private void clearEngagementKnowledge() {
        for (int i = 0; i < engagedFinches.size(); i++)
            engagedFinches.set(i, false);
    }

    private void engage(World.Place place) {
        engagedFinches.set(place.xPosition() * height + place.yPosition(), true);
    }

    private boolean isEngaged(World.Place place) {
        return engagedFinches.get(place.xPosition() * height + place.yPosition());
    }
    
    private void meet(GalapagosFinch finch1, GalapagosFinch finch2) {
        //let both finches decide if they won't to help the other finch
        Action finch1Action = finch1.decide(finch2);
        Action finch2Action = finch2.decide(finch1);
        
        finch1.changeHitpoints(getMeetingResult(finch1Action, finch2Action));
        finch2.changeHitpoints(getMeetingResult(finch2Action, finch1Action));
        
        //tell the finches what was done to them (so they eventually can learn)
        finch1.response(finch2, finch2Action);
        finch2.response(finch1, finch1Action);
    }

    private int getMeetingResult(Action finch1Action, Action finch2Action) {
        if (finch1Action == Action.CLEANING) {
            if (finch2Action == Action.CLEANING) {
                return HelpedGotHelpValue;
            }
            else if (finch2Action == Action.IGNORING) {
                return HelpedDidntGetHelpValue;
            }
        } else if (finch1Action == Action.IGNORING) {
            if (finch2Action == Action.CLEANING) {
                return DidntHelpGotHelpValue;
            }
            else if (finch2Action == Action.IGNORING) {
                return DidntHelpDidntGetHelpValue;
            }
        }
        throw new Error("Unhandled Action combination");
    }
    
    /**
     * Decrease the hitpoints of all finches by hitpointsPerRound, and find dead finches
     * and remove them from world, and store the changes in statisticsTree.
     */
    private void grimReaper () {
        for (World<GalapagosFinch>.Place p : world) if (p.getElement() != null) {
            GalapagosFinch f = p.getElement();
            f.changeHitpoints(-hitpointsPerRound);
            f.makeOlder();
            FinchStatus newStatus = f.status();
            if (newStatus != FinchStatus.ALIVE) {
                Statistics s = statisticsTree.get(f.behavior().toString());
                if (newStatus == FinchStatus.DEAD_AGE)
                    s.incDeadByAge();
                    else s.incDeadByTicks();
                s.decPopulation();
                p.setElement(null);
            }
        }
    }
    
    public Statistics statistics(String behavior) {
        assert(statisticsTree.containsKey(behavior));
        return statisticsTree.get(behavior.toString());
    }
    
    public int round () {
        return round;
    }
    
    public Iterator<World<GalapagosFinch>.Place> worldIterator () {
        return world.iterator();
    }
    
    public List<Behavior> behaviors () {
        return finchBehaviors;
    }
    
    public void doNotifyObservers () {
        setChanged();
        notifyObservers();
    }
}
