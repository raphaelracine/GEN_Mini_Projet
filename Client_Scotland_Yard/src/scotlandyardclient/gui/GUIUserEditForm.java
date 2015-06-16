/**
 * Classe qui représente l'interface graphique qui permet d'éditer un compte
 * utilisateur.
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardclient.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import scotlandyardclient.Client;
import scotlandyardclient.Utils;

public class GUIUserEditForm extends JFrame {

    // Composants graphiques...
    private final JLabel lblUsername;
    private final JLabel lblPassword;
    private final JLabel lblPasswordConfirm;

    private final JTextField txtUsername;
    private final JPasswordField txtPassword;
    private final JPasswordField txtPasswordConfirm;

    private final JButton edit;
    private final JButton cancel;

    private String username; // Nom de l'utilisateur

    /**
     * Constructeur
     *
     * @param parent Fenêtre de salle de jeu parente
     * @param username Nom d'utilisateur
     */
    public GUIUserEditForm(GUIGameRoom parent, String username) {
        this.username = username;

        lblUsername = new JLabel("Nom d'utilisateur:");
        lblPassword = new JLabel("Mot de passe:");
        lblPasswordConfirm = new JLabel("Confirmation mot de passe:");

        txtUsername = new JTextField(username, 20);
        txtPassword = new JPasswordField(20);
        txtPasswordConfirm = new JPasswordField(20);

        edit = new JButton("Editer");

        edit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Utils.checkPatternMatches(txtPassword.getText(), "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*[#|\\s]).{6,20}$")) {
                    JOptionPane.showMessageDialog(rootPane, "Mot de passe invalide...", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!Utils.checkPatternMatches(txtUsername.getText(), "^(?=.*.)(?!.*[#|\\s]).{6,20}$")) {
                    JOptionPane.showMessageDialog(rootPane, "Nom d'utilisateur invalide...", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!txtPassword.getText().equals(txtPasswordConfirm.getText())) {
                    JOptionPane.showMessageDialog(rootPane, "Les deux mots de passe ne correspondent pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (Client.getInstance().editAccount(txtUsername.getText(), txtPassword.getText())) {
                    JOptionPane.showMessageDialog(rootPane, "Compte edité avec succès.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                    GUIUserEditForm.this.dispose();
                    parent.setUsername(txtUsername.getText());
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Erreur lors de l'édition du compte.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancel = new JButton("Annuler");

        cancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                GUIUserEditForm.this.dispose();
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
        controlsArea.add(edit);
        controlsArea.add(cancel);

        getContentPane().add(formArea, BorderLayout.CENTER);
        getContentPane().add(controlsArea, BorderLayout.SOUTH);

        setTitle("Utilisateur");
        pack();
        setVisible(true);
    }
}
