package galapagos;

import java.util.HashMap;
import java.awt.Color;

public class Galapagos {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
        HashMap<Behavior, Color> behaviors = new HashMap<Behavior, Color>();
        behaviors.put(new Samaritan(), Color.RED);
        behaviors.put(new Cheater(), Color.BLUE);
        behaviors.put(new FlipFlopper(), Color.getHSBColor(0, 0, 0.5F));
        behaviors.put(new RandomFinch(), Color.MAGENTA);
        behaviors.put(new Grudger(), Color.ORANGE.darker());
        behaviors.put(new LenientGrudger(), Color.RED.darker());
        behaviors.put(new TitForTat(), Color.GREEN);
        behaviors.put(new InverseTitForTat(), Color.PINK);
        behaviors.put(new ProbingTitForTat(), Color.CYAN);
        behaviors.put(new SuspiciousTitForTat(), Color.ORANGE);
        behaviors.put(new Predictor(), Color.GREEN.darker());
        behaviors.put(new Analyzer(), Color.BLUE.darker());
        behaviors.put(new Statistical(), Color.getHSBColor(0.5F,0.7F,0.7F));
        
        GalapagosFrame frame = new GalapagosFrame(behaviors);
        frame.setVisible(true);
    }

}
