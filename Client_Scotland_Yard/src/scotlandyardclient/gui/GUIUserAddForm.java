package scotlandyardclient.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import scotlandyardclient.Client;
import scotlandyardclient.Utils;

public class GUIUserAddForm extends JFrame {

    private JLabel lblUsername;
    private JLabel lblPassword;
    private JLabel lblPasswordConfirm;

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtPasswordConfirm;

    private JButton add;
    private final JButton cancel;

    public GUIUserAddForm() {

        lblUsername = new JLabel("Nom d'utilisateur:");
        lblPassword = new JLabel("Mot de passe:");
        lblPasswordConfirm = new JLabel("Confirmation mot de passe:");

        txtUsername = new JTextField(20);
        txtPassword = new JPasswordField(20);
        txtPasswordConfirm = new JPasswordField(20);

        add = new JButton("Ajouter");
        add.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (!Utils.checkPatternMatches(txtPassword.getText(), "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*[#|\\s]).{6,20}$")) {
                    JOptionPane.showMessageDialog(rootPane, "Mot de passe invalide...", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return; // Ne pas enlever !
                }

                if (!Utils.checkPatternMatches(txtUsername.getText(), "^(?=.*.)(?!.*[#|\\s]).{6,20}$")) {
                    JOptionPane.showMessageDialog(rootPane, "Nom d'utilisateur invalide...", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return; // Ne pas enlever !
                }

                // Je dois encore vérifier que les deux mot de passes sont les mêmes...
                if (!txtPassword.getText().equals(txtPasswordConfirm.getText())) {
                    JOptionPane.showMessageDialog(rootPane, "Les deux mots de passe ne correspondent pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return; // Ne pas enlever !
                }

                // Demander au serveur d'ajouter l'utilisateur
                if (Client.getInstance().createAccount(txtUsername.getText(), txtPassword.getText())) {
                    JOptionPane.showMessageDialog(rootPane, "Le compte a été créé.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                    GUIUserAddForm.this.dispose();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Erreur lors de la création du compte.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
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

        cancel = new JButton("Annuler");
        cancel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GUIUserAddForm.this.dispose();
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

        JPanel formArea = new JPanel(new GridLayout(3, 2));
        formArea.add(lblUsername);
        formArea.add(txtUsername);
        formArea.add(lblPassword);
        formArea.add(txtPassword);
        formArea.add(lblPasswordConfirm);
        formArea.add(txtPasswordConfirm);

        JPanel controlsArea = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlsArea.add(add);
        controlsArea.add(cancel);

        getContentPane().add(formArea, BorderLayout.CENTER);
        getContentPane().add(controlsArea, BorderLayout.SOUTH);

        setTitle("Utilisateur");
        pack();
        setVisible(true);
    }
}
