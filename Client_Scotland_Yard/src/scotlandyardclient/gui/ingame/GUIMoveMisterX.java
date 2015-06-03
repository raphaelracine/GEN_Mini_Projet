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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import scotlandyardclient.Client;
import scotlandyardclient.json.GameMap;
import scotlandyardclient.json.Link;
import scotlandyardclient.json.Station;
import scotlandyardclient.pone.Pone;
import scotlandyardclient.pone.PoneMisterX;

public class GUIMoveMisterX extends JFrame {

    private final JRadioButton radTaxi = new JRadioButton("Taxi", true);
    private final JRadioButton radBus = new JRadioButton("Bus");
    private final JRadioButton radSubway = new JRadioButton("Metro");
    private final JRadioButton radBlackTicket = new JRadioButton("Ticket noir");

    private final Station destination;
    private final Link link;

    private final JButton move = new JButton("Se déplacer");
    private final JButton cancel = new JButton("Annuler");

    public GUIMoveMisterX(Link link, Station destination) {
        this.destination = destination;
        this.link = link;

        move.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String locomotionUsed;
                
                PoneMisterX pone = (PoneMisterX)Client.getInstance().getPone();
                
                if (radTaxi.isSelected()) {
                    locomotionUsed = "taxi";
                    if (!link.getLocomotions().contains(locomotionUsed)) {
                        JOptionPane.showMessageDialog(rootPane, "Pas de connexion taxi entre ces deux stations", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else if (radBus.isSelected()) {
                    locomotionUsed = "bus";
                    if (!link.getLocomotions().contains(locomotionUsed)) {
                        JOptionPane.showMessageDialog(rootPane, "Pas de connexion bus entre ces deux stations", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else if (radSubway.isSelected()) {
                    locomotionUsed = "subway";
                    if (!link.getLocomotions().contains(locomotionUsed)) {
                        JOptionPane.showMessageDialog(rootPane, "Pas de connexion métro entre ces deux stations", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                
                // VERIFIER TICKETS POUR SE DEPLACER
                
                
                pone.moveToStation(destination);
                GUIMoveMisterX.this.dispose();
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
