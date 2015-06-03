/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scotlandyardclient.gui.ingame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import scotlandyardclient.gui.views.StationView;
import scotlandyardclient.pone.PoneMisterX;

public class GUIMove extends JFrame {

    private final JRadioButton radTaxi = new JRadioButton("Taxi", true);
    private final JRadioButton radBus = new JRadioButton("Bus");
    private final JRadioButton radSubway = new JRadioButton("Metro");

    private PoneMisterX pone;
    private StationView stationView;
    
    private final JButton move = new JButton("Se déplacer");
    private final JButton cancel = new JButton("Annuler");
    
    public GUIMove(PoneMisterX pone, StationView stationView) {
        this.pone = pone;
        this.stationView = stationView;
        
        move.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Tester si assez de ticket
                // Si ok
                //  procéder au déplacement
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        
        cancel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GUIMove.this.dispose();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

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
        
        setTitle("Choix du moyen de déplacement");
        pack();
        setVisible(true);
    }
}