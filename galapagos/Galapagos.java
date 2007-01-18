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
        behaviors.put(new FlipFlopper(), Color.getHSBColor(0, 0, (float)0.5));
        behaviors.put(new RandomFinch(), Color.MAGENTA);
        behaviors.put(new Grudger(), Color.ORANGE.darker());
        behaviors.put(new TitForTat(), Color.GREEN);
        behaviors.put(new InverseTitForTat(), Color.PINK);
        behaviors.put(new ProbingTitForTat(), Color.CYAN);
        behaviors.put(new SuspiciousTitForTat(), Color.ORANGE);
        
        GalapagosFrame frame = new GalapagosFrame(behaviors);
        frame.setVisible(true);
    }

}
