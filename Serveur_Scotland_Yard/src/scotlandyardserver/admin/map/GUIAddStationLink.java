/**
 * Cette classe est l'interface graphique permettant d'ajouter un lien entre
 * deux stations
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
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUIAddStationLink extends JDialog {

    private final GUIEditMap parent;

    private final JLabel lblFrom;
    private final JLabel lblTo;
    private final JLabel lblType;

    private final JComboBox<String> cbxFrom;
    private final JComboBox<String> cbxTo;
    private final JComboBox<String> cbxType;

    private final JButton add;
    private final JButton cancel;

    /**
     * Constructeur
     *
     * @param parent Editeur de carte parent
     * @throws SQLException
     */
    public GUIAddStationLink(GUIEditMap parent) throws SQLException {
        this.parent = parent;

        lblFrom = new JLabel("Origine:");
        lblTo = new JLabel("Destination:");
        lblType = new JLabel("Type:");

        cbxFrom = new JComboBox<>();
        cbxTo = new JComboBox<>();

        ResultSet rs = parent.getServer().getSQLSelection(
                "SELECT number FROM station WHERE map_fk = '" + parent.getMapName() + "'");
        while (rs.next()) {
            cbxFrom.addItem(rs.getString("number"));
            cbxTo.addItem(rs.getString("number"));
        }

        cbxType = new JComboBox<>();
        cbxType.addItem("Taxi");
        cbxType.addItem("Bus");
        cbxType.addItem("Métro");
        cbxType.setSelectedIndex(0);

        add = new JButton("Ajouter");
        add.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // teste que deux stations sont bien sélectionnées
                if (cbxFrom.getSelectedIndex() < 0 || cbxTo.getSelectedIndex() < 0) {
                    JOptionPane.showMessageDialog(rootPane, "Une station n'est pas sélectionnée", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // teste que les deux stations sélectionnées sont différentes
                if (cbxFrom.getSelectedItem().equals(cbxTo.getSelectedItem())) {
                    JOptionPane.showMessageDialog(rootPane, "Un lien ne peut lier une même station", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    String query
                            = "SELECT station_id FROM station WHERE map_fk = '" + parent.getMapName() + "' "
                            + "AND number = '" + cbxFrom.getSelectedItem().toString() + "' ";
                    ResultSet rs = parent.getServer().getSQLSelection(query);
                    rs.next();
                    String idFrom = rs.getString("station_id");

                    query
                            = "SELECT station_id FROM station WHERE map_fk = '" + parent.getMapName() + "' "
                            + "AND number = '" + cbxTo.getSelectedItem().toString() + "' ";
                    rs = parent.getServer().getSQLSelection(query);
                    rs.next();
                    String idTo = rs.getString("station_id");

                    // teste que le lien de type donné entre le couple de station n'existe pas déjà
                    query
                            = "SELECT * FROM link WHERE type = '" + cbxType.getSelectedItem().toString() + "' "
                            + "AND (first_station_fk = '" + idFrom + "' "
                            + "AND second_station_fk = '" + idTo + "') "
                            + "OR (first_station_fk = '" + idTo + "' "
                            + "AND second_station_fk = '" + idFrom + "') ";
                    rs = parent.getServer().getSQLSelection(query);
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(rootPane, "Un lien de même type existe déjà entre ces deux stations", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // teste que la première station est compatible avec le type de lien
                    query
                            = "SELECT * FROM station "
                            + "WHERE station_id ='" + idFrom + "' "
                            + "AND type LIKE '%" + cbxType.getSelectedItem().toString() + "%'";
                    rs = parent.getServer().getSQLSelection(query);
                    if (!rs.next()) {
                        JOptionPane.showMessageDialog(rootPane, "La première station n'est pas compatible avec le type du lien", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // teste que la deuxième station est compatible avec le type de lien
                    query
                            = "SELECT * FROM station "
                            + "WHERE station_id ='" + idTo + "' "
                            + "AND type LIKE '%" + cbxType.getSelectedItem().toString() + "%'";
                    rs = parent.getServer().getSQLSelection(query);
                    if (!rs.next()) {
                        JOptionPane.showMessageDialog(rootPane, "La deuxième station n'est pas compatible avec le type du lien", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    parent.getServer().executeCRUDrequest(
                            "INSERT INTO link "
                            + "VALUES('" + idFrom + "','" + idTo + "','" + cbxType.getSelectedItem().toString() + "')");
                    JOptionPane.showMessageDialog(rootPane, "Lien saisi avec succès", "Confirmation", JOptionPane.DEFAULT_OPTION);
                    parent.updateStationLinkList();
                    GUIAddStationLink.this.dispose();
                } catch (SQLException ex) {
                    Logger.getLogger(GUIAddStationLink.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        cancel = new JButton("Annuler");
        cancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                GUIAddStationLink.this.dispose();
            }
        });

        JPanel formArea = new JPanel(new GridLayout(3, 2));
        formArea.add(lblFrom);
        formArea.add(cbxFrom);
        formArea.add(lblTo);
        formArea.add(cbxTo);
        formArea.add(lblType);
        formArea.add(cbxType);

        JPanel controlsArea = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlsArea.add(add);
        controlsArea.add(cancel);

        getContentPane().add(formArea, BorderLayout.CENTER);
        getContentPane().add(controlsArea, BorderLayout.SOUTH);

        setModal(true);
        setTitle("Lien");
        pack();
        setVisible(true);
    }
}
