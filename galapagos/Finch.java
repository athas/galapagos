package galapagos;

/**
 * The Finch-interface is used for referencing a Finch in places where
 * the Finch itself is not suposed to be manipulated in any way.
 */
public interface Finch {
    /**
     * Is this Finch dead or alive?
     */
    public FinchStatus status ();
}