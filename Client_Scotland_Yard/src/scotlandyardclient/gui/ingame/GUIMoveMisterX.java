package scotlandyardclient.gui.ingame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import scotlandyardclient.json.GameMap;
import scotlandyardclient.json.Station;

public class GUIMoveMisterX extends JFrame {

    private final JRadioButton radTaxi = new JRadioButton("Taxi", true);
    private final JRadioButton radBus = new JRadioButton("Bus");
    private final JRadioButton radSubway = new JRadioButton("Metro");
    private final JRadioButton radBlackTicket = new JRadioButton("Ticket noir");
    
    private final Station destination;
    private final GameMap map;
    
    private final JButton move = new JButton("Se déplacer");
    private final JButton cancel = new JButton("Annuler");
    
    public GUIMoveMisterX(GameMap map, Station destination) {
        this.destination = destination;
        this.map = map;
        
        move.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Tester si assez de ticket
                // Tester aussi qu'il est possible de se déplacer
                // avec le moyen de déplacement sélectionné entre les 
                // Si ok
                //  procéder au déplacement
            }
        });
        
        cancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                GUIMoveMisterX.this.dispose();
            }
        });
        
        ButtonGroup mainGroup = new ButtonGroup();
        mainGroup.add(radTaxi);
        mainGroup.add(radBus);
        mainGroup.add(radSubway);
        mainGroup.add(radBlackTicket);
        
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        northPanel.add(radTaxi);
        northPanel.add(radBus);
        northPanel.add(radSubway);
        northPanel.add(radBlackTicket);
        
        southPanel.add(move);
        southPanel.add(cancel);
        
        getContentPane().add(northPanel, BorderLayout.NORTH);
        getContentPane().add(southPanel, BorderLayout.SOUTH);
        
        setTitle("Choix du moyen de déplacement");
        pack();
        setVisible(true);
    }
}