/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scotlandyardclient.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Yassin
 */
public class GUIPlayerWaiting extends JFrame {
    private JLabel lblPlayers = new JLabel("Joueurs");
    
    private JButton quit = new JButton("Quitter");
    
    private JTable players = new JTable();
    private JScrollPane scrollPlayers = new JScrollPane(players);
    
    private JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    
    public GUIPlayerWaiting() {
            quit.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // envoyer message au serveur
                GUIPlayerWaiting.this.dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }
        });
        
        northPanel.add(lblPlayers);
        southPanel.add(quit);
        
        getContentPane().add(northPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPlayers);
        getContentPane().add(southPanel, BorderLayout.SOUTH);
        
        setTitle("Attente de joueurs");
        pack();
        setVisible(true);
    }
}