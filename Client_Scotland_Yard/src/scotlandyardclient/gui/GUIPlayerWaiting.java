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
public class GUIPlayerWaiting extends JFrame implements Runnable {
    private GUIGameRoom gameRoom;
    private Thread activity;
    
    JLabel label = new JLabel("En attente de joueurs...");
    
    private JButton quit = new JButton("Quitter");
    
    private JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    
    private boolean isQuit = false;
    
    public GUIPlayerWaiting(GUIGameRoom gameRoom, String game) {
        this.gameRoom = gameRoom;
        
        quit.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isQuit = true;
                Client.getInstance().sendCommand("PLAYERLEAVEGAME#" + game);
                GUIPlayerWaiting.this.dispose();
                new GUIGameRoom(Client.getInstance().username());
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
        
        centerPanel.add(label);
        southPanel.add(quit);
 
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        getContentPane().add(southPanel, BorderLayout.SOUTH);
        
        setTitle("Attente de joueurs");
        pack();
        setVisible(true);
        
        activity = new Thread(this);
        activity.start();
    }
    
    public void run() {      
        while (!isQuit) {
            String[] args = parseCommand(Client.getInstance().receiveCommand());
            String cmd = args[0];
            System.out.println("args:" + args[0]);
            switch (cmd) {
                case "HOSTLEFTGAME":
                    new GUIGameRoom(Client.getInstance().username());
                    GUIPlayerWaiting.this.dispose();
                    return;
                case "GAMESTART":                     
                    new GUIGame();
                    GUIPlayerWaiting.this.dispose();
                    return;
            }          
        }
    }
    
    private String[] parseCommand(String command) {
        return command.split("#");
    }
}
