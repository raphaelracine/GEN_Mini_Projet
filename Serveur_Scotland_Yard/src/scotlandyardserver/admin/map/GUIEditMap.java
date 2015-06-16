/**
 * Cette classe est l'interface graphique permettant d'éditer une carte
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardserver.admin.map;

import scotlandyardserver.admin.user.GUIUser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.util.logging.*;
import javax.swing.table.*;
import scotlandyardserver.Server;

public class GUIEditMap extends JDialog {

    private final GUIMap parent;
    private final String name;

    private final JLabel imageLabel;
    private final ImageIcon image;

    private final JScrollPane scrollStationView;
    private final JScrollPane scrollStationLinkView;

    private final JTable stationView;
    private final JTable stationLinkView;

    private final JButton addStation;
    private final JButton editStation;
    private final JButton removeStation;
    private final JButton addStationLink;
    private final JButton editStationLink;
    private final JButton removeStationLink;

    private final JButton close;

    private static final Object[] stationColumnsNames = {
        "Numéro", "x", "y", "Type"
    };

    private static final Object[] stationLinkColumnsNames = {
        "Origine", "Destination", "Type"
    };

    /**
     * Constructeur
     *
     * @param parent GUIMap parent
     * @param name Nom de la carte
     * @param url Url de l'image
     * @throws IOException
     */
    public GUIEditMap(GUIMap parent, String name, String url) throws IOException {
        this.parent = parent;
        this.name = name;

        image = new ImageIcon(url);
        imageLabel = new JLabel(image);

        stationView = new JTable();
        scrollStationView = new JScrollPane(stationView);

        stationLinkView = new JTable();
        scrollStationLinkView = new JScrollPane(stationLinkView);

        addStation = new JButton("+");
        addStation.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GUIAddStation frm = new GUIAddStation(GUIEditMap.this);
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

        editStation = new JButton("...");
        editStation.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

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

        removeStation = new JButton("-");
        removeStation.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!(stationView.getSelectedRow() < 0)) {
                    try {
                        parent.getServer().executeCRUDrequest("DELETE FROM station WHERE map_fk='"
                                + GUIEditMap.this.name + "' AND number ='"
                                + stationView.getValueAt(stationView.getSelectedRow(), 0) + "'"
                        );
                        updateStationList();
                    } catch (SQLException ex) {
                        Logger.getLogger(GUIEditMap.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Sélectionner une station", "Erreur", JOptionPane.ERROR_MESSAGE);
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

        addStationLink = new JButton("+");
        addStationLink.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    GUIAddStationLink frm = new GUIAddStationLink(GUIEditMap.this);
                } catch (SQLException ex) {
                    Logger.getLogger(GUIEditMap.class.getName()).log(Level.SEVERE, null, ex);
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

        editStationLink = new JButton("...");
        editStationLink.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

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

        removeStationLink = new JButton("-");
        removeStationLink.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!(stationLinkView.getSelectedRow() < 0)) {
                    try {
                        parent.getServer().executeCRUDrequest(
                                "DELETE FROM link "
                                + "WHERE type = '" + stationLinkView.getValueAt(stationLinkView.getSelectedRow(), 2) + "' "
                                + "AND first_station_fk = (SELECT station_id FROM station WHERE map_fk = '" + getMapName() + "' AND number = '" + stationLinkView.getValueAt(stationLinkView.getSelectedRow(), 0) + "') "
                                + "AND second_station_fk = (SELECT station_id FROM station WHERE map_fk = '" + getMapName() + "' AND number = '" + stationLinkView.getValueAt(stationLinkView.getSelectedRow(), 1) + "') "
                        );
                        updateStationLinkList();
                    } catch (SQLException ex) {
                        Logger.getLogger(GUIEditMap.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    updateStationList();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Sélectionner un lien", "Erreur", JOptionPane.ERROR_MESSAGE);
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

        close = new JButton("Fermer");
        close.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GUIEditMap.this.dispose();
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

        JPanel stationControlsArea = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        stationControlsArea.add(addStation);
        stationControlsArea.add(removeStation);
        stationControlsArea.add(editStation);

        JPanel stationPanel = new JPanel(new BorderLayout());
        stationPanel.setBorder(BorderFactory.createTitledBorder("Stations"));
        stationPanel.add(scrollStationView, BorderLayout.CENTER);
        stationPanel.add(stationControlsArea, BorderLayout.SOUTH);

        JPanel stationLinkControlsArea = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        stationLinkControlsArea.add(addStationLink);
        stationLinkControlsArea.add(removeStationLink);
        stationLinkControlsArea.add(editStationLink);

        JPanel stationLinkPanel = new JPanel(new BorderLayout());
        stationLinkPanel.setBorder(BorderFactory.createTitledBorder("Liens"));
        stationLinkPanel.add(scrollStationLinkView, BorderLayout.CENTER);
        stationLinkPanel.add(stationLinkControlsArea, BorderLayout.SOUTH);

        JPanel westArea = new JPanel(new GridLayout(4, 1));
        westArea.add(stationPanel);
        westArea.add(stationControlsArea);
        westArea.add(stationLinkPanel);
        westArea.add(stationLinkControlsArea);

        JPanel controlsArea = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlsArea.add(close);

        JScrollPane scrollPictureArea = new JScrollPane(imageLabel);
        scrollPictureArea.setBorder(BorderFactory.createTitledBorder("Aperçu"));

        getContentPane().add(westArea, BorderLayout.WEST);
        getContentPane().add(scrollPictureArea, BorderLayout.CENTER);
        getContentPane().add(controlsArea, BorderLayout.SOUTH);

        setModal(true);
        setTitle("Edition carte");
        pack();
        updateStationList();
        updateStationLinkList();
        setVisible(true);
    }

    public void updateStationList() {
        try {
            ResultSet rs = parent.getServer().getSQLSelection(
                    "SELECT number, x, y, type FROM station WHERE map_fk = '" + name + "'");

            DefaultTableModel model = new DefaultTableModel(stationColumnsNames, 0);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("number"),
                    rs.getString("x"),
                    rs.getString("y"),
                    rs.getString("type")
                });
            }

            stationView.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(GUIUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getMapName() {
        return name;
    }

    public ImageIcon getImage() {
        return image;
    }

    public Server getServer() {
        return parent.getServer();
    }

    public void updateStationLinkList() {
        try {
            ResultSet rs = parent.getServer().getSQLSelection(
                    "SELECT s.number AS 'from', (SELECT number FROM station WHERE station_id = l.second_station_fk) AS 'to', l.type FROM link AS l "
                    + "INNER JOIN station AS s "
                    + "ON s.station_id = l.first_station_fk "
                    + "WHERE s.map_fk = '" + getMapName() + "'"
            );

            DefaultTableModel model = new DefaultTableModel(stationLinkColumnsNames, 0);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("from"),
                    rs.getString("to"),
                    rs.getString("type"),});
            }

            stationLinkView.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(GUIUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
