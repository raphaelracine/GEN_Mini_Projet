/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scotlandyardclient.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import scotlandyardclient.Client;
import scotlandyardclient.json.Game;

/**
 *
 * @author Yassin
 */
public class GUIPlayerWaiting extends JFrame {
    private GUIGameRoom gameRoom;
    
    private JLabel lblPlayers = new JLabel("Joueurs");
    
    private JButton quit = new JButton("Quitter");
    
    private JList<String> players = new JList<>();
    private JScrollPane scrollPlayers = new JScrollPane(players);
    private DefaultListModel playersModel = new DefaultListModel();
    
    private JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    
    private static final Object[] columnsNames = {
        "Joueur"
    };
    
    public GUIPlayerWaiting(GUIGameRoom gameRoom) {
        this.gameRoom = gameRoom;
        
        quit.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // envoyer message au serveur
                //GUIPlayerWaiting.this.dispose();
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
        
        players.setModel(playersModel);
        
        getContentPane().add(northPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPlayers);
        getContentPane().add(southPanel, BorderLayout.SOUTH);
        
        setTitle("Attente de joueurs");
        pack();
        setVisible(true);
        
        waiting();
    }
    
    private void waiting() {
        while (true) {
            String[] args = parseCommand(Client.getInstance().receiveCommand());
            String cmd = args[0];
            switch (cmd) {
                case "PLAYERJOINEDGAME": 
                    if (args.length  == 2)
                        playersModel.addElement(args[1]);
                    break;
                case "PLAYERQUITGAME": 
                    if (args.length  == 2)
                        playersModel.removeElement(args[1]);
                    break;
                // case préparation jeu ou game start
            }
        }
    }
    
    private String[] parseCommand(String command) {
        return command.split("#");
    }
}
