package scotlandyardserver.admin.user;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import scotlandyardserver.Utils;

public class GUIAddUser extends JFrame {

    private GUIUser userManager;

    private JLabel lblUsername;
    private JLabel lblPassword;
    private JLabel lblPasswordConfirm;

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtPasswordConfirm;

    private JButton add;
    private final JButton cancel;

    public GUIAddUser(GUIUser userManager) {
        this.userManager = userManager;

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

                    userManager.getServer().executeCRUDrequest(
                            "INSERT INTO user(username, password) "
                            + "VALUES('" + txtUsername.getText() + "','"
                            + txtPassword.getText() + "')");
                    JOptionPane.showMessageDialog(rootPane, "Utilisateur saisi avec succès", "Confirmation", JOptionPane.DEFAULT_OPTION);

                    userManager.updateUserList();
                    GUIAddUser.this.dispose();
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
                GUIAddUser.this.dispose();
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
