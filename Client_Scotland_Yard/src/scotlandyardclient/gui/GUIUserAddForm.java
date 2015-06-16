/**
 * Classe qui représente l'interface graphique qui permet de créer un compte
 * utilisateur
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

public class GUIUserAddForm extends JFrame {

    // Composants graphiques...
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JLabel lblPasswordConfirm;

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtPasswordConfirm;

    private JButton add;
    private final JButton cancel;

    /**
     * Constructeur
     */
    public GUIUserAddForm() {

        lblUsername = new JLabel("Nom d'utilisateur:");
        lblPassword = new JLabel("Mot de passe:");
        lblPasswordConfirm = new JLabel("Confirmation mot de passe:");

        txtUsername = new JTextField(20);
        txtPassword = new JPasswordField(20);
        txtPasswordConfirm = new JPasswordField(20);

        add = new JButton("Ajouter");

        add.addActionListener(new ActionListener() {

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

                if (Client.getInstance().createAccount(txtUsername.getText(), txtPassword.getText())) {
                    JOptionPane.showMessageDialog(rootPane, "Le compte a été créé.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                    GUIUserAddForm.this.dispose();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Erreur lors de la création du compte.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancel = new JButton("Annuler");

        cancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                GUIUserAddForm.this.dispose();
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
