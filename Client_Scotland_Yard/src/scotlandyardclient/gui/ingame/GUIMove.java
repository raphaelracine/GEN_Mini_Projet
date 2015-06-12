/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scotlandyardclient.gui.ingame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class GUIMove extends JFrame {

    private final JRadioButton radTaxi = new JRadioButton("Taxi", true);
    private final JRadioButton radBus = new JRadioButton("Bus");
    private final JRadioButton radSubway = new JRadioButton("Metro");
    private final Station destination;

    private final JButton move = new JButton("Se déplacer");
    private final JButton cancel = new JButton("Annuler");
    private final Link link;
    private final GUIGame parent;

    public GUIMove(GUIGame parent, Link link, Station destination) {
        this.parent = parent;
        this.link = link;
        this.destination = destination;

        move.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                String locomotionUsed;

                Pone pone = Client.getInstance().getPone();

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

                pone.moveToStation(destination);
                GUIMove.this.dispose();
            }
        });

        cancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                GUIMove.this.dispose();
            }
        });

        ButtonGroup group = new ButtonGroup();
        group.add(radTaxi);
        group.add(radBus);
        group.add(radSubway);

        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        northPanel.add(radTaxi);
        northPanel.add(radBus);
        northPanel.add(radSubway);

        southPanel.add(move);
        southPanel.add(cancel);

        getContentPane().add(northPanel, BorderLayout.NORTH);
        getContentPane().add(southPanel, BorderLayout.SOUTH);

        setTitle("Choisir un moyen de déplacement");
        pack();
        setVisible(true);
    }
}
