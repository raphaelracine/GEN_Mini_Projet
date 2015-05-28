/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scotlandyardclient.gui.waitinggame;

import scotlandyardclient.gui.ingame.GUIGame;
import java.awt.*;
import javax.swing.*;
import scotlandyardclient.Client;
import scotlandyardclient.Utils;
import scotlandyardclient.gui.GUIGameRoom;

public class GUIPlayerWaiting extends GUIWaitingGame {
    JLabel label = new JLabel("En attente de joueurs...");
    private final JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    public GUIPlayerWaiting(GUIGameRoom room, String game) {        
        super(room, game);
        centerPanel.add(label);
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        
        pack();
    }

    @Override
    public void run() {
        while(true) {
            String[] cmd = Utils.parseCommand(Client.getInstance().receiveCommand());
            
            switch(cmd[0]) {
                case "GAMESTART":
                    new GUIGame();
                    dispose();
                    return;                  
                case "HOSTLEFTGAME":
                    Client.getInstance().leaveGame();
                    break;
                case "PLAYERLEFTGAME":
                    new GUIGameRoom(Client.getInstance().username());
                    dispose();
                    return;   
            }
        }
    }
}
