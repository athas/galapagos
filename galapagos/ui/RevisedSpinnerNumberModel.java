package galapagos.ui;

import javax.swing.SpinnerNumberModel;

/**
 * A SpinnerNumberModel that that changes the behavior of getNextValue() and getPreviousValue().
 * getNextValue() returns value + stepSize unless value + stepSize > maximum in which case it returns maximum
 * (in the last case SpinnerNumberModel.getNextValue() returns null, resulting in the JSpinner not updating its value).
 * getPreviousValue() behaves analogously.
 */
public class RevisedSpinnerNumberModel extends SpinnerNumberModel {
    public RevisedSpinnerNumberModel() {
        super();
    }
    
    public RevisedSpinnerNumberModel (double baseValue, double minimum, double maximum, double stepSize) {
        super(baseValue, minimum, maximum, stepSize);
    }
    
    public RevisedSpinnerNumberModel (int baseValue, int minimum, int maximum, int stepSize) {
        super(baseValue, minimum, maximum, stepSize);
    }
    
    public RevisedSpinnerNumberModel (Number baseValue, Comparable minimum, Comparable maximum, Number stepSize) {
        super(baseValue, minimum, maximum, stepSize);
    }
    
    /**
     * Returns <code>value + stepSize</code>, or <code>maximum</code> if the sum exceeds <code>maximum</code>.
     */
    public Object getNextValue() {
        Object next = super.getNextValue();
        if (next == null)
            return super.getMaximum();
        else
            return next;
    }
    
    /**
     * Returns <code>value - stepSize</code>, or <code>minimum</code> if the sum is less than <code>minimum</code>.
     */
    public Object getPreviousValue() {
        Object previous = super.getPreviousValue();
        if (previous == null)
            return super.getMinimum();
        else
            return previous;
    }
}
