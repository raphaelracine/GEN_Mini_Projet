/**
 * Classe qui représente l'interface graphique qui permet à un joueur d'attendre
 * le début de la partie
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardclient.gui.waitinggame;

import scotlandyardclient.gui.ingame.GUIGame;
import java.awt.*;
import javax.swing.*;
import scotlandyardclient.Client;
import scotlandyardclient.Utils;
import scotlandyardclient.gui.GUIGameRoom;

public class GUIPlayerWaiting extends GUIWaitingGame {

    // Composants graphiques...
    JLabel label = new JLabel("En attente de joueurs...");
    private final JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    /**
     * Constructeur
     *
     * @param room Interface graphique de la salle de jeu parente
     * @param game Nom de la partie
     */
    public GUIPlayerWaiting(GUIGameRoom room, String game) {
        super(room, game);
        centerPanel.add(label);
        getContentPane().add(centerPanel, BorderLayout.CENTER);

        pack();
    }

    /**
     * Attente active du début de la partie...
     */
    @Override
    public void run() {
        while (true) {
            String[] cmd = Utils.parseCommand(Client.getInstance().receiveCommand());

            switch (cmd[0]) {
                case "GAMESTART":
                    new GUIGame().startGame();
                    dispose();
                    return;
                case "HOSTLEFTGAME":
                    Client.getInstance().leaveGame();
                    return;
                case "PLAYERLEFTGAME":
                    new GUIGameRoom(Client.getInstance().username()).refreshGameList();
                    dispose();
                    return;
            }
        }
    }
}
