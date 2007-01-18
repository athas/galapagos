package galapagos.tests;

import galapagos.RevisedSpinnerNumberModel;
import javax.swing.SpinnerNumberModel;
import junit.framework.TestCase;

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
        assertTrue(standardModel.getNumber().equals(0));
        assertTrue(standardModel.getMinimum() == null);
        assertTrue(standardModel.getMaximum() == null);
        assertTrue(standardModel.getStepSize().equals(1));
        assertTrue(modelHasState(doubleModel, 0.5, 0.0, 1.0, 0.4));
        assertTrue(modelHasState(intModel, 5, 0, 10, 4));
        assertTrue(modelHasState(numberModel, 5.0, 0.0, 10.0, 4.0));
    }
    
    public boolean modelHasState(SpinnerNumberModel model, Number value, Comparable minValue, Comparable maxValue, Number stepSize) {
        return model.getNumber().equals(value) && model.getMinimum().equals(minValue) 
                && model.getMaximum().equals(maxValue) && model.getStepSize().equals(stepSize);
    }

    /**
     * Test method for RevisedSpinnerNumberModel#getNextValue().
     */
    public void testGetNextValue() {
        assertTrue(standardModel.getNextValue().equals(1));
        assertTrue(standardModel.getNextValue().equals(1));
        standardModel.setValue(1);
        assertTrue(standardModel.getNextValue().equals(2));
        
        assertTrue(doubleModel.getNextValue().equals(0.9));
        assertTrue(doubleModel.getNextValue().equals(0.9));
        doubleModel.setValue(0.7);
        assertTrue(doubleModel.getNextValue().equals(1.0));
        
        assertTrue(intModel.getNextValue().equals(9));
        assertTrue(intModel.getNextValue().equals(9));
        intModel.setValue(7);
        assertTrue(intModel.getNextValue().equals(10));
        
        assertTrue(numberModel.getNextValue().equals(9.0));
        assertTrue(numberModel.getNextValue().equals(9.0));
        numberModel.setValue(7.0);
        assertTrue(numberModel.getNextValue().equals(10.0));
    }

    /**
     * Test method for RevisedSpinnerNumberModel#getPreviousValue().
     */
    public void testGetPreviousValue() {
        assertTrue(standardModel.getPreviousValue().equals(-1));
        assertTrue(standardModel.getPreviousValue().equals(-1));
        standardModel.setValue(-1);
        assertTrue(standardModel.getPreviousValue().equals(-2));
        
        assertTrue(Math.abs(((Double) doubleModel.getPreviousValue()) - 0.1) < epsilon);
        assertTrue(Math.abs(((Double) doubleModel.getPreviousValue()) - 0.1) < epsilon);
        doubleModel.setValue(0.3);
        assertTrue(Math.abs(((Double) doubleModel.getPreviousValue()) - 0.0) < epsilon);
        
        assertTrue(intModel.getPreviousValue().equals(1));
        assertTrue(intModel.getPreviousValue().equals(1));
        intModel.setValue(3);
        assertTrue(intModel.getPreviousValue().equals(0));
        
        assertTrue(numberModel.getPreviousValue().equals(1.0));
        assertTrue(numberModel.getPreviousValue().equals(1.0));
        numberModel.setValue(3.0);
        assertTrue(numberModel.getPreviousValue().equals(0.0));
    }
}
