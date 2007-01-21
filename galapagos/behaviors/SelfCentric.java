package galapagos.behaviors;

import galapagos.biotope.*;

/**
 * A behavior that will try to identify finches of its own species based
 * on their first actions. If it thinks another finch is also SelfCentric,
 * then it would always help it, if it can't be a SelfCentric finch then it
 * only helps it in the identification process.
 * It identifies it self by using a start-scheme that not many other finches use.
 * At first it ignores the finch, then it cleans. From this simple pattern it can
 * distinguish it self from many of the other finches.
 */
public class SelfCentric extends MemoryBehavior<SelfCentric.IdentificationLevel> {

	private static String DESCRIPTION = "A finch with this behavior will only help similar species";

	
	/**
	 * Unknown finches and finches that can't be SelfCentric is ignored.
	 * Those only met once and those that could be SelfCentric is cleaned.
	 */
	public Action decide(Finch finch) {
		IdentificationLevel state = recall(finch);

		if(state == null || state == IdentificationLevel.OTHER_SPECIES)
			return Action.IGNORING;
		else
			return Action.CLEANING;
	}

	/**
	 * Categorizes the finch after how many times it is met once or
	 * is identified as a SelfCentric or another kind.
	 */
	public void response(Finch finch, Action action) {
		IdentificationLevel state = recall(finch);
		// A finch that cleans at first meeting can't be SelfCentric
		if(state == null && action == Action.CLEANING)
			remember(finch, IdentificationLevel.OTHER_SPECIES);
		// Finches that ignores at first could be.
		else if(state == null && action == Action.IGNORING)
			remember(finch, IdentificationLevel.MET_ONCE);
		//A finch that is cleaning and is only met once should be identified as SelfCentric
		//likewise SelfCentric finches that cleans should still be self-centric
		else if( ( state == IdentificationLevel.MET_ONCE
				   || state == IdentificationLevel.SELF_CENTRIC )
				 && action == Action.CLEANING)
			remember(finch, IdentificationLevel.SELF_CENTRIC);
		//finches identified as SelfCentric or is met once that ignores, must be wrongly identified.
		else
			remember(finch, IdentificationLevel.OTHER_SPECIES);
	}
	
	/**
	 * How another finch is identified.
	 */
	private enum IdentificationLevel {
		MET_ONCE,
		SELF_CENTRIC,
		OTHER_SPECIES
	}
	
    /**
     * @inheritDoc
     */
	public Behavior clone() {
		return new SelfCentric();
	}
	
	/**
     * @inheritDoc
     */
	public String description() {
		return DESCRIPTION;
	}
	
    /**
     * @inheritDoc
     */
	public String toString() {
		return "Self-Centric";
	}
	
    /**
     * @inheritDoc
     */
	public boolean equals(Object obj) {
		return obj instanceof SelfCentric;
	}
	
    /**
     * @inheritDoc
     */
	public int hashCode () {
		return toString().hashCode();
	}
}
