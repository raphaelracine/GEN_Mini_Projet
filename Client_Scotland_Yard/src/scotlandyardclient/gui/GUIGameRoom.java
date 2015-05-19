package scotlandyardclient.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import scotlandyardclient.Client;
import scotlandyardclient.json.*;

public class GUIGameRoom extends JFrame {

    class UserPanel extends JPanel {

        private final JLabel lblWelcome = new JLabel();
        private JButton disconnect = new JButton("Se déconnecter");
        private JButton editAccount = new JButton("Editer compte");

        UserPanel() {
            setLayout(new FlowLayout(FlowLayout.RIGHT));
            add(lblWelcome, BorderLayout.NORTH);
            add(disconnect, BorderLayout.CENTER);
            add(editAccount, BorderLayout.CENTER);
            setUsername(username);

            disconnect.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    Client.getInstance().logOut();
                    GUIGameRoom.this.dispose();
                    new GUIConnectServer();
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

            editAccount.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    new GUIUserEditForm(GUIGameRoom.this, username);
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
        }

        private void setUsername(String username) {
            lblWelcome.setText("Bienvenue <" + username + ">");
        }
    }

    class GamePanel extends JPanel {

        private JButton refresh = new JButton("Rafraîchir");
        private JButton createGame = new JButton("Créer une partie");
        private JButton joinGame = new JButton("Rejoindre partie");

        GamePanel() {
            add(refresh);
            add(createGame);
            add(joinGame);

            refresh.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                   GUIGameRoom.this.refreshGameList();
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
            
            createGame.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                   new GUICreateGame(GUIGameRoom.this);
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

            joinGame.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (games.getSelectedRow() >= 0) {
                        if (Client.getInstance().joinGame((String) games.getValueAt(games.getSelectedRow(), 0))) {
                            new GUIPlayerWaiting(GUIGameRoom.this, (String) games.getValueAt(games.getSelectedRow(), 2));
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "Impossible de rejoindre la partie", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Selectionner une partie", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
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
        }
    }

    private JLabel lblGames = new JLabel("Liste des parties");

    private JPanel northPanel = new JPanel(new GridLayout(1, 2));
    private JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    private UserPanel userPanel;

    private JTable games = new JTable();
    private JScrollPane scrollGames = new JScrollPane(games);

    private static final Object[] columnsNames = {
        "Partie", "Hôte", "Carte", "Joueurs"
    };
    
    private String username;

    public GUIGameRoom(String username) {
        this.username = username;
        userPanel = new UserPanel();

        northPanel.add(lblGames);
        northPanel.add(userPanel);

        southPanel.add(new GamePanel());

        getContentPane().add(northPanel, BorderLayout.NORTH);
        getContentPane().add(scrollGames);
        getContentPane().add(southPanel, BorderLayout.SOUTH);

        refreshGameList();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Salle de jeu");
        pack();
        setVisible(true);
    }

    public void setUsername(String username) {
        this.username = username;
        userPanel.setUsername(username);
    }
    
    public void refreshGameList() {
        GameList gameList = Client.getInstance().getGameList();
        
        DefaultTableModel model = new DefaultTableModel(columnsNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (Game game : gameList.games()) {
            model.addRow(new Object[] {
                game.name(), 
                game.host(), 
                game.map(), 
                (
                        new Integer(game.getCurrentPlayers()).toString() 
                        + "/" 
                        + new Integer(game.getNumberPlayers()).toString()
                        )
            });
        }
        
        games.setModel(model);
    }
}
