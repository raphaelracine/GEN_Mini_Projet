/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scotlandyardclient.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import scotlandyardclient.Client;

/**
 *
 * @author Yassin
 */
public class GUIHostWaiting extends JFrame implements Runnable {
    private GUIGameRoom gameRoom;
    private Thread activity;
    
    private JLabel lblPlayers = new JLabel("Joueurs");
    
    private JButton start = new JButton("Lancer");
    private JButton quit = new JButton("Quitter");
    
    private JList players = new JList();
    private JScrollPane scrollPlayers = new JScrollPane(players);
    private DefaultListModel playersModel = new DefaultListModel();
    
    private JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    
    public GUIHostWaiting(GUIGameRoom gameRoom) {
        this.gameRoom = gameRoom;
        
        start.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // envoyer message au serveur
                // changement d'état
                // ouverture fenêtre jeu
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
        start.setEnabled(false);
        
        quit.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // envoyer message au serveur
                //GUIHostWaiting.this.dispose();
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
        southPanel.add(start);
        southPanel.add(quit);
        
        players.setModel(playersModel);
        
        getContentPane().add(northPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPlayers);
        getContentPane().add(southPanel, BorderLayout.SOUTH);
        
        setTitle("Attente de joueurs");
        pack();
        setVisible(true);
        
        activity = new Thread(this);
        activity.start();
    }
    
    @Override
    public void run() {
        while (true) {
            String[] args = parseCommand(Client.getInstance().receiveCommand());
            String cmd = args[0];
            switch (cmd) {
                case "PLAYERJOINEDGAME": 
                    if (args.length  == 2)
                        playersModel.addElement(args[1]);
                    break;
                case "PLAYERQUITGAME": 
                    if (args.length  == 2) {
                        playersModel.removeElement(args[1]);
                        start.setEnabled(false);
                    }
                    
                    break;
                case "GAMEREADY":
                    start.setEnabled(true);
                    break;
                // case préparation jeu ou game start
            }
        }
    }
    
    private String[] parseCommand(String command) {
        return command.split("#");
    }
}
