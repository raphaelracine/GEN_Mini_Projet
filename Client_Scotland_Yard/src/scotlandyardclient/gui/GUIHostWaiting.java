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

    public GUIHostWaiting(GUIGameRoom gameRoom, String game) {
        this.gameRoom = gameRoom;

        start.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Client.getInstance().sendCommand("STARTGAME#" + game);
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
                Client.getInstance().leaveGame(game);
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
        playersModel.addElement(Client.getInstance().username());

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
            String[] cmd = parseCommand(Client.getInstance().receiveCommand());
            
            System.out.println(cmd[0]);

            switch (cmd[0]) {
                case "GAMESTART":
                    new GUIGame();
                    dispose();
                    return;
                case "GAMEREADY":
                    start.setEnabled(true);
                    break;
                case "PLAYERJOINEDGAME":
                    playersModel.addElement(cmd[1]);
                    break;
                case "PLAYERLEFTGAME":
                    playersModel.removeElement(cmd[1]);
                    start.setEnabled(false);
                    break;
                case "HOSTLEFTGAME":
                    new GUIGameRoom(Client.getInstance().username());
                    dispose();
                    return;
            }
        }
    }

    private String[] parseCommand(String command) {
        return command.split("#");
    }
}
