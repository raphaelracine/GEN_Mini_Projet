/**
 * Cette classe est l'interface graphique permettant de afficher la liste des
 * cartes
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardserver.admin.map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.*;
import java.util.logging.*;
import javax.swing.table.DefaultTableModel;
import scotlandyardserver.Server;

public class GUIMap extends JFrame {

    private final Server server;

    private final JLabel lblMap;
    private final JTextField txtMap;

    private final JScrollPane scrollMapView;
    private JTable mapView;

    private final JButton add;
    private final JButton edit;
    private final JButton remove;

    private static final Object[] columnsNames = {
        "Carte", "Url"
    };

    /**
     * Constructeur
     *
     * @param server Référence vers le serveur
     */
    public GUIMap(Server server) {
        this.server = server;

        lblMap = new JLabel("Carte:");

        txtMap = new JTextField();
        txtMap.setColumns(20);
        txtMap.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                updateMapList();
            }
        });

        mapView = new JTable();
        scrollMapView = new JScrollPane(mapView);

        add = new JButton("Ajouter");
        add.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GUIAddMap form = new GUIAddMap(GUIMap.this);
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
                if (mapView.getSelectedRow() >= 0) {
                    try {
                        new GUIEditMap(
                                GUIMap.this,
                                (String) mapView.getValueAt(mapView.getSelectedRow(), 0),
                                (String) mapView.getValueAt(mapView.getSelectedRow(), 1)
                        );
                    } catch (IOException ex) {
                        Logger.getLogger(GUIMap.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Selectionner une carte", "Erreur", JOptionPane.ERROR_MESSAGE);
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
                    if (!(mapView.getSelectedRow() < 0)) {
                        server.executeCRUDrequest(
                                "DELETE FROM map WHERE name='"
                                + mapView.getValueAt(mapView.getSelectedRow(), 0) + "'"
                        );
                        updateMapList();
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Sélectionner une carte", "Erreur", JOptionPane.ERROR_MESSAGE);
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
        findingArea.add(lblMap);
        findingArea.add(txtMap);

        JPanel controlsArea = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlsArea.add(add);
        controlsArea.add(edit);
        controlsArea.add(remove);

        getContentPane().add(findingArea, BorderLayout.NORTH);
        getContentPane().add(scrollMapView, BorderLayout.CENTER);
        getContentPane().add(controlsArea, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Gestion des cartes");
        pack();

        updateMapList();
        setVisible(true);
    }

    public void updateMapList() {
        try {
            ResultSet rs = server.getSQLSelection(
                    "SELECT name, picture FROM map WHERE name LIKE '%" + txtMap.getText() + "%'");

            DefaultTableModel model = new DefaultTableModel(columnsNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("name"),
                    rs.getString("picture")
                });
            }

            mapView.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(GUIMap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Server getServer() {
        return server;
    }
}
