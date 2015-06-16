/**
 * Cette classe est l'interface graphique permettant d'éditer un utlisateur
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardserver.admin.user;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import scotlandyardserver.Utils;

public class GUIEditUser extends JFrame {

    private final JLabel lblUsername;
    private final JLabel lblPassword;
    private final JLabel lblPasswordConfirm;

    private final JTextField txtUsername;
    private final JPasswordField txtPassword;
    private final JPasswordField txtPasswordConfirm;

    private final JButton edit;
    private final JButton cancel;

    private String username;

    private GUIUser userManager;

    /**
     * Constructeur
     *
     * @param userManager GUIUser parent
     * @param username Le nom de l'utilisateur avant modification
     */
    public GUIEditUser(GUIUser userManager, String username) {
        this.userManager = userManager;
        this.username = username;

        lblUsername = new JLabel("Nom d'utilisateur:");
        lblPassword = new JLabel("Mot de passe:");
        lblPasswordConfirm = new JLabel("Confirmation mot de passe:");

        txtUsername = new JTextField(username, 20);
        txtPassword = new JPasswordField(20);
        txtPasswordConfirm = new JPasswordField(20);

        edit = new JButton("Editer");
        edit.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
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

                    String request = "UPDATE user SET username='" + txtUsername.getText()
                            + "', password='" + txtPassword.getText() + "' WHERE "
                            + "username='" + username + "'";
                    userManager.updateUserList();
                    userManager.getServer().executeCRUDrequest(request);

                    JOptionPane.showMessageDialog(rootPane, "Utilisateur édité avec succès", "Confirmation", JOptionPane.DEFAULT_OPTION);
                    userManager.updateUserList();

                    GUIEditUser.this.dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(rootPane, ex, "Erreur", JOptionPane.ERROR_MESSAGE);
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
                GUIEditUser.this.dispose();
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
        controlsArea.add(edit);
        controlsArea.add(cancel);

        getContentPane().add(formArea, BorderLayout.CENTER);
        getContentPane().add(controlsArea, BorderLayout.SOUTH);

        setTitle("Utilisateur");
        pack();
        setVisible(true);
    }
}
