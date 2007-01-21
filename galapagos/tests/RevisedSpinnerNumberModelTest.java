package galapagos.tests;

import galapagos.ui.RevisedSpinnerNumberModel;

import javax.swing.SpinnerNumberModel;
import junit.framework.TestCase;

/**
 * Test that our RevisedSpinnerNumberModel works as expected.
 */
public class RevisedSpinnerNumberModelTest extends TestCase {
    private SpinnerNumberModel standardModel;
    private SpinnerNumberModel doubleModel;
    private SpinnerNumberModel intModel;
    private SpinnerNumberModel numberModel;
    private static final Double epsilon = 0.0000000000001;
    
    
    protected void setUp() {
        standardModel = new SpinnerNumberModel();
        doubleModel = new RevisedSpinnerNumberModel(0.5, 0.0, 1.0, 0.4);
        intModel = new RevisedSpinnerNumberModel(5, 0, 10, 4);
        numberModel = new RevisedSpinnerNumberModel(5.0, 0.0, 10, 4);
    }

    /**
     * Test method for RevisedSpinnerNumberModel constructors.
     */
    public void testRevisedSpinnerNumberModel() {
        assertEquals(standardModel.getNumber(),0);
        assertNull(standardModel.getMinimum());
        assertNull(standardModel.getMaximum());
        assertEquals(standardModel.getStepSize(), 1);
        modelStateTester(doubleModel, 0.5, 0.0, 1.0, 0.4);
        modelStateTester(intModel, 5, 0, 10, 4);
        modelStateTester(numberModel, 5.0, 0.0, 10.0, 4.0);
    }
    
    /**
     * Test whether the specified RevisedSpinnerNumberModel has the specified state.
     * @param model the RevisedSpinnerNumberModel whose state we would like to check.
     * @param value the expected value of the model.
     * @param minValue the expected minimum value of the model.
     * @param maxValue the expected maximum value of the model.
     * @param stepSize the expected step size of the model.
     */
    public void modelStateTester(SpinnerNumberModel model, Number value, Comparable minValue, Comparable maxValue, Number stepSize) {
        assertEquals(model.getNumber(), value);
        assertEquals(model.getMinimum(), minValue); 
        assertEquals(model.getMaximum(), maxValue);
        assertEquals(model.getStepSize(), stepSize);
    }

    /**
     * Test method for RevisedSpinnerNumberModel#getNextValue().
     */
    public void testGetNextValue() {
        assertEquals(standardModel.getNextValue(), 1);
        assertEquals(standardModel.getNextValue(), 1);
        standardModel.setValue(1);
        assertEquals(standardModel.getNextValue(), 2);
        
        assertEquals(doubleModel.getNextValue(), 0.9);
        assertEquals(doubleModel.getNextValue(), 0.9);
        doubleModel.setValue(0.7);
        assertEquals(doubleModel.getNextValue(), 1.0);
        
        assertEquals(intModel.getNextValue(), 9);
        assertEquals(intModel.getNextValue(), 9);
        intModel.setValue(7);
        assertEquals(intModel.getNextValue(), 10);
        
        assertEquals(numberModel.getNextValue(), 9.0);
        assertEquals(numberModel.getNextValue(), 9.0);
        numberModel.setValue(7.0);
        assertEquals(numberModel.getNextValue(), 10.0);
    }

    /**
     * Test method for RevisedSpinnerNumberModel#getPreviousValue().
     */
    public void testGetPreviousValue() {
        assertEquals(standardModel.getPreviousValue(), -1);
        assertEquals(standardModel.getPreviousValue(), -1);
        standardModel.setValue(-1);
        assertEquals(standardModel.getPreviousValue(), -2);
        
        assertTrue(Math.abs(((Double) doubleModel.getPreviousValue()) - 0.1) < epsilon);
        assertTrue(Math.abs(((Double) doubleModel.getPreviousValue()) - 0.1) < epsilon);
        doubleModel.setValue(0.3);
        assertTrue(Math.abs(((Double) doubleModel.getPreviousValue()) - 0.0) < epsilon);
        
        assertEquals(intModel.getPreviousValue(), 1);
        assertEquals(intModel.getPreviousValue(), 1);
        intModel.setValue(3);
        assertEquals(intModel.getPreviousValue(), 0);
        
        assertEquals(numberModel.getPreviousValue(), 1.0);
        assertEquals(numberModel.getPreviousValue(), 1.0);
        numberModel.setValue(3.0);
        assertEquals(numberModel.getPreviousValue(), 0.0);
    }
}
