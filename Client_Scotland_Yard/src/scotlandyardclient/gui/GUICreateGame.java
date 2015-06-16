/**
 * Classe qui représente l'interface graphique permettant créer une partie sur
 * le serveur.
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardclient.gui;

import scotlandyardclient.gui.waitinggame.GUIHostWaiting;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import scotlandyardclient.Client;
import scotlandyardclient.Utils;

public class GUICreateGame extends JFrame {

    private final GUIGameRoom gameRoom; // Fenêtre salle de jeu appelante
    private final GamePanel gamePanel;
    private final UserPanel userpanel;

    /**
     * Constructeur
     *
     * @param gameRoom Fenêtre graphique de salle de jeu appelante
     */
    public GUICreateGame(GUIGameRoom gameRoom) {
        this.gameRoom = gameRoom;
        gamePanel = new GamePanel();
        userpanel = new UserPanel();

        getContentPane().add(gamePanel, BorderLayout.CENTER);
        getContentPane().add(userpanel, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("GUICreateGame");
        pack();
        setVisible(true);
    }

    /**
     * Classe interne qui représente un JPanel avec les boutons OK et annuler
     */
    class UserPanel extends JPanel {

        // Attributs composants graphiques...
        private final JButton cancel = new JButton("Annuler");
        private final JButton ok = new JButton("Ok");

        /**
         * Constructeur
         */
        UserPanel() {
            setLayout(new FlowLayout(FlowLayout.RIGHT));
            add(cancel, BorderLayout.CENTER);
            add(ok, BorderLayout.CENTER);

            cancel.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    GUICreateGame.this.dispose();
                }
            });

            ok.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!Utils.checkPatternMatches(gamePanel.getTxtParty().getText(),
                            "^(?=.*.)(?!.*[#|\\s]).{6,20}$")) {
                        JOptionPane.showMessageDialog(rootPane, "Nom de la partie invalide...",
                                "Erreur", JOptionPane.ERROR_MESSAGE);
                    } else {
                        boolean result = Client.getInstance().createGame(
                                gamePanel.txtParty.getText(), gamePanel.playersNumber(), gamePanel.mapName());
                        if (result) {
                            JOptionPane.showMessageDialog(rootPane, "La partie a été créee.",
                                    "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                            GUICreateGame.this.dispose();
                            new GUIHostWaiting(gameRoom, gamePanel.getTxtParty().getText()).startWaiting();

                        } else {
                            JOptionPane.showMessageDialog(rootPane, "Impossible de créer la partie",
                                    "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });
        }
    }

    /**
     * Classe interne qui représente les champs de saisie de l'utilisateur pour
     * créer la partie
     */
    class GamePanel extends JPanel {

        // Attributs composants graphiques...
        private final JLabel lblParty;
        private final JLabel lblPlayersNunmber;
        private final JLabel lblMap;

        private JTextField txtParty;
        private final JComboBox comboPlayersNumber;
        private final JComboBox comboMap;

        /**
         * Constructeur
         */
        public GamePanel() {

            setLayout(new GridLayout(3, 2));
            lblParty = new JLabel("Nom de la partie");
            lblPlayersNunmber = new JLabel("Nombre de joueurs");
            lblMap = new JLabel("Carte");

            txtParty = new JTextField(20);

            comboPlayersNumber = new JComboBox();
            for (int i = 2; i <= 6; i++) {
                comboPlayersNumber.addItem(i);
            }

            comboMap = new JComboBox();

            Client.getInstance().sendCommand("REQUESTMAPLIST");

            for (String map : Client.getInstance().getMapNames().names()) {
                comboMap.addItem(map);
            }

            add(lblParty);
            add(txtParty);
            add(lblPlayersNunmber);
            add(comboPlayersNumber);
            add(lblMap);
            add(comboMap);
        }

        /**
         * Permet d'obtenir le textField du nom de la partie
         *
         * @return Le textfield contenant le nom de la partie
         */
        public JTextField getTxtParty() {
            return txtParty;
        }

        /**
         * Permet d'obtenir le nom de la carte qui a été sélectionée
         *
         * @return Le nom de la carte sélectionné
         */
        public String mapName() {
            return comboMap.getSelectedItem().toString();
        }

        /**
         * Permet d'obtenir le nombre de joueurs choisi par l'utilisateur
         *
         * @return Le nombre de joueurs choisi
         */
        public int playersNumber() {
            return (Integer) comboPlayersNumber.getSelectedItem();
        }

    }

}
