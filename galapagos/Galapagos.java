package galapagos;

import galapagos.behaviors.*;
import galapagos.biotope.Behavior;
import galapagos.ui.BiotopeViewer;

import java.util.*;
import java.awt.Color;

/**
 * An application simulating the interaction of finches with different
 * behaviors.
 */
public class Galapagos {

    /**
     * Run the Galapagos application.
     *
     * @param args The arguments are ignored.
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
        behaviors.put(new SelfCentric(), new Color(100, 60, 150));
        behaviors.put(new FriendlySelfCentric(), new Color(10, 100, 30));
        List<Behavior> Schizo1 = new ArrayList<Behavior>(2);
        Schizo1.add(new Cheater());
        Schizo1.add(new TitForTat());
        behaviors.put(new Schizophrenic("Cheating Tit for Tat", Schizo1), new Color(230, 220, 20));
        List<Behavior> Schizo2 = new ArrayList<Behavior>(3);
        Schizo2.add(new Predictor());
        Schizo2.add(new Analyzer());
        Schizo2.add(new Statistical());
        behaviors.put(new Schizophrenic("Analyzing Combo", Schizo2), new Color(67, 136, 201));
        
        
        
        BiotopeViewer frame = new BiotopeViewer(behaviors);
        frame.setVisible(true);
    }

}
