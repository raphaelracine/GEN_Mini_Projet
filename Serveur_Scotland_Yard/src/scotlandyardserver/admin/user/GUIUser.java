/**
 * Cette classe est l'interface graphique permettant d'afficher la liste des
 * utilisateurs
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import scotlandyardserver.Server;

public class GUIUser extends JFrame {

    private final Server server;

    private final JLabel lblUsername;
    private final JTextField txtUsername;

    private final JScrollPane scrollUserView;
    private JTable userView;

    private final JButton block;
    private final JButton unblock;
    private final JButton add;
    private final JButton edit;
    private final JButton remove;

    private static final Object[] columnsNames = {
        "Nom d'utilisateur", "Est connecté", "Est bloqué"
    };

    /**
     * Constructeur
     *
     * @param server Référence vers le serveur
     */
    public GUIUser(Server server) {
        this.server = server;

        lblUsername = new JLabel("Rechercher un utilisateur:");

        txtUsername = new JTextField();
        txtUsername.setColumns(20);

        txtUsername.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                updateUserList();
            }
        });

        userView = new JTable();
        scrollUserView = new JScrollPane(userView);

        block = new JButton("Bloquer");
        block.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (!(userView.getSelectedRow() < 0)) {
                        server.executeCRUDrequest(
                                "UPDATE user SET blocked=true WHERE username='"
                                + userView.getValueAt(userView.getSelectedRow(), 0) + "'"
                        );
                        updateUserList();
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Sélectionner un utilisateur", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }

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

        unblock = new JButton("Débloquer");
        unblock.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (!(userView.getSelectedRow() < 0)) {
                        server.executeCRUDrequest(
                                "UPDATE user SET blocked=false WHERE username='"
                                + userView.getValueAt(userView.getSelectedRow(), 0) + "'"
                        );
                        updateUserList();
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Sélectionner un utilisateur", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }

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

        add = new JButton("Ajouter");
        add.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GUIAddUser form = new GUIAddUser(GUIUser.this);
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

        edit = new JButton("Editer");
        edit.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (userView.getSelectedRow() >= 0) {
                    new GUIEditUser(
                            GUIUser.this,
                            (String) userView.getValueAt(
                                    userView.getSelectedRow(),
                                    userView.getSelectedColumn()
                            ));
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Selectionner un utilisateur", "Erreur", JOptionPane.ERROR_MESSAGE);
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

        remove = new JButton("Supprimer");
        remove.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (!(userView.getSelectedRow() < 0)) {
                        server.executeCRUDrequest(
                                "DELETE FROM user WHERE username='"
                                + userView.getValueAt(userView.getSelectedRow(), 0)
                                + "' AND connected=0"
                        );
                        updateUserList();
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Sélectionner un utilisateur", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(rootPane, ex, "Erreur", JOptionPane.ERROR_MESSAGE);
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

        JPanel findingArea = new JPanel(new FlowLayout(FlowLayout.LEFT));
        findingArea.add(lblUsername);
        findingArea.add(txtUsername);

        JPanel controlsArea = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlsArea.add(add);
        controlsArea.add(edit);
        controlsArea.add(remove);
        controlsArea.add(block);
        controlsArea.add(unblock);

        getContentPane().add(findingArea, BorderLayout.NORTH);
        getContentPane().add(scrollUserView, BorderLayout.CENTER);
        getContentPane().add(controlsArea, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Gestion des utilisateurs");
        pack();

        updateUserList();
        setVisible(true);
    }

    /**
     * Permet de rafraichir la liste des utilisateurs
     */
    public void updateUserList() {
        try {
            ResultSet rs = server.getSQLSelection(
                    "SELECT username, connected, blocked FROM user WHERE username LIKE '%" + txtUsername.getText() + "%'");

            DefaultTableModel model = new DefaultTableModel(columnsNames, 0) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("username"),
                    (rs.getBoolean("connected") ? "Oui" : "Non"),
                    (rs.getBoolean("blocked") ? "Oui" : "Non")
                });
            }

            userView.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(GUIUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Server getServer() {
        return server;
    }
}
