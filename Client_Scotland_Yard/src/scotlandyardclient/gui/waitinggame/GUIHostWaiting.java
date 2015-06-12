package scotlandyardclient.gui.waitinggame;

import scotlandyardclient.gui.ingame.GUIGameMisterX;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import scotlandyardclient.Client;
import scotlandyardclient.Utils;
import scotlandyardclient.gui.GUIGameRoom;

public class GUIHostWaiting extends GUIWaitingGame {

    private final JLabel lblPlayers = new JLabel("Joueurs");
    private final JButton start = new JButton("Lancer");

    private final JList players = new JList();
    private final JScrollPane scrollPlayers = new JScrollPane(players);
    private final DefaultListModel playersModel = new DefaultListModel();

    private final JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

    public GUIHostWaiting(GUIGameRoom room, String game) {
        super(room, game);

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client.getInstance().sendCommand("STARTGAME#" + game);
            }
        });

        start.setEnabled(false);

        northPanel.add(lblPlayers);
        southPanel().add(start);

        players.setModel(playersModel);
        playersModel.addElement(Client.getInstance().username());

        getContentPane().add(northPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPlayers);

        pack();
    }

    @Override
    public void run() {
        while (true) {
            String[] cmd = Utils.parseCommand(Client.getInstance().receiveCommand());

            switch (cmd[0]) {
                case "GAMESTART":
                    new GUIGameMisterX().startGame();
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
}
