/**
 * Classe qui représente l'interface graphique de la salle des parties.
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardclient.gui;

import scotlandyardclient.gui.waitinggame.GUIPlayerWaiting;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import scotlandyardclient.Client;
import scotlandyardclient.json.*;

public class GUIGameRoom extends JFrame {

    class UserPanel extends JPanel {

        // Composants graphiques...
        private final JLabel lblWelcome = new JLabel();
        private final JButton disconnect = new JButton("Se déconnecter");
        private final JButton editAccount = new JButton("Editer compte");

        /**
         * Constructeur
         */
        UserPanel() {
            setLayout(new FlowLayout(FlowLayout.RIGHT));
            add(lblWelcome, BorderLayout.NORTH);
            add(disconnect, BorderLayout.CENTER);
            add(editAccount, BorderLayout.CENTER);
            setUsername(username);

            disconnect.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    Client.getInstance().logOut();
                    GUIGameRoom.this.dispose();
                }
            });

            editAccount.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    new GUIUserEditForm(GUIGameRoom.this, username);
                }
            });
        }

        /**
         * Permet de changer le username dans le label qui affiche le nom de
         * l'utilisateur
         *
         * @param username Nom de l'utilisateur
         */
        private void setUsername(String username) {
            lblWelcome.setText("Bienvenue <" + username + ">");
        }
    }

    /**
     * Classe interne qui représente un JPanel avec les 3 boutons rafraichir,
     * créer et rejoindre une partie
     */
    class GamePanel extends JPanel {

        // Composants graphiques...
        private final JButton refresh = new JButton("Rafraîchir");
        private final JButton createGame = new JButton("Créer une partie");
        private final JButton joinGame = new JButton("Rejoindre partie");

        /**
         * Constructeur
         */
        GamePanel() {
            add(refresh);
            add(createGame);
            add(joinGame);

            refresh.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    GUIGameRoom.this.refreshGameList();
                }
            });

            createGame.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    new GUICreateGame(GUIGameRoom.this);
                    GUIGameRoom.this.dispose();
                }
            });

            joinGame.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (games.getSelectedRow() >= 0) {
                        if (Client.getInstance().joinGame((String) games.getValueAt(games.getSelectedRow(), 0))) {
                            new GUIPlayerWaiting(GUIGameRoom.this, (String) games.getValueAt(games.getSelectedRow(), 0)).startWaiting();
                            GUIGameRoom.this.dispose();
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "Impossible de rejoindre la partie", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Selectionner une partie", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }
    }

    private final JLabel lblGames = new JLabel("Liste des parties");

    private final JPanel northPanel = new JPanel(new GridLayout(1, 2));
    private final JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    private final UserPanel userPanel;

    private final JTable games = new JTable();
    private final JScrollPane scrollGames = new JScrollPane(games);

    // Noms des colonnes de la JTable
    private static final Object[] columnsNames = {
        "Partie", "Hôte", "Carte", "Joueurs"
    };

    private String username; // Nom d'utilisateur

    /**
     * Constructeur
     *
     * @param username Le nom d'utilisateur du client
     */
    public GUIGameRoom(String username) {
        this.username = username;
        userPanel = new UserPanel();

        northPanel.add(lblGames);
        northPanel.add(userPanel);

        southPanel.add(new GamePanel());

        getContentPane().add(northPanel, BorderLayout.NORTH);
        getContentPane().add(scrollGames);
        getContentPane().add(southPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Salle de jeu");
        pack();
        setVisible(true);
    }

    /**
     * Permet de changer le nom d'utilisateur affiché à l'écran
     *
     * @param username Le nouveau nom d'utilisateur
     */
    public void setUsername(String username) {
        this.username = username;
        userPanel.setUsername(username);
    }

    /**
     * Permet de rafraichir la liste des parties en cours
     */
    public void refreshGameList() {
        GameList gameList = Client.getInstance().getGameList();

        DefaultTableModel model = new DefaultTableModel(columnsNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Game game : gameList.games()) {
            model.addRow(new Object[]{
                game.name(),
                game.host(),
                game.map(),
                (new Integer(game.getCurrentPlayers()).toString()
                + "/"
                + new Integer(game.getNumberPlayers()).toString())
            });
        }

        games.setModel(model);
    }
}
