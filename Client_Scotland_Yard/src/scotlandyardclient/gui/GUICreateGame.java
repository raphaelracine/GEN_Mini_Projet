package scotlandyardclient.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import scotlandyardclient.Client;
import scotlandyardclient.Utils;


public class GUICreateGame extends JFrame {

    class UserPanel extends JPanel {

        private JButton cancel = new JButton("Annuler");
        private JButton ok = new JButton("Ok");

        UserPanel() {
            setLayout(new FlowLayout(FlowLayout.RIGHT));
            add(cancel, BorderLayout.CENTER);
            add(ok, BorderLayout.CENTER);

            cancel.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    GUICreateGame.this.dispose();
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

            ok.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!Utils.checkPatternMatches(gamePanel.getTxtParty().getText(),
                            "^(?=.*.)(?!.*[#|\\s]).{6,20}$")) {
                        JOptionPane.showMessageDialog(rootPane, "Nom de la partie invalide...",
                                "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    } else {
                        boolean result = Client.getInstance().createGame(
                               gamePanel.txtParty.getText(),gamePanel.playersNumber()
                                , gamePanel.mapName());
                        if (result) {
                            JOptionPane.showMessageDialog(rootPane, "La partie a été créee.",
                                    "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "Impossible de créer la partie",
                                    "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
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

    class GamePanel extends JPanel {

        private JLabel lblParty;
        private JLabel lblPlayersNunmber;
        private JLabel lblMap;

        private JTextField txtParty;
        private JComboBox comboPlayersNumber;
        private JComboBox comboMap;

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
            
            for(String map : Client.getInstance().getMapNames().names())
                comboMap.addItem(map);

            add(lblParty);
            add(txtParty);
            add(lblPlayersNunmber);
            add(comboPlayersNumber);
            add(lblMap);
            add(comboMap);
        }

        public JTextField getTxtParty() {
            return txtParty;
        }

        public String mapName() {
            return comboMap.getSelectedItem().toString();
        }

        public int playersNumber() {
            return (Integer) comboPlayersNumber.getSelectedItem();
        }

    }
    private final GUIGameRoom gameRoom;
    private final GamePanel gamePanel;
    private final UserPanel userpanel;

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

}
