/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scotlandyardserver.admin.map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.*;
import scotlandyardserver.Utils;

/**
 *
 * @author Yassin
 */
public class GUIAddStation extends JDialog {

    private final GUIEditMap parent;

    private final JLabel lblNumber;
    private final JLabel lblX;
    private final JLabel lblY;
    private final JLabel lblType;

    private final JTextField txtNumber;
    private final JTextField txtX;
    private final JTextField txtY;

    private final JComboBox<String> cbxType;

    private final JButton add;
    private final JButton cancel;

    public GUIAddStation(GUIEditMap parent) {
        this.parent = parent;

        lblNumber = new JLabel("Numéro:");
        lblX = new JLabel("x:");
        lblY = new JLabel("y:");
        lblType = new JLabel("Type:");

        txtNumber = new JTextField(20);
        txtX = new JTextField(20);
        txtY = new JTextField(20);

        cbxType = new JComboBox<>();
        cbxType.addItem("Taxi");
        cbxType.addItem("TaxiBus");
        cbxType.addItem("TaxiBusMétro");
        cbxType.setSelectedIndex(0);

        add = new JButton("Ajouter");
        add.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String number = txtNumber.getText();
                if (number.isEmpty() || !Utils.isInteger(number) || Integer.valueOf(number) < 0) {
                    JOptionPane.showMessageDialog(rootPane, "Numéro de station invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ImageIcon image = parent.getImage();
                String x = txtX.getText();
                if (x.isEmpty() || !Utils.isInteger(x) || Integer.valueOf(x) < 0 || Integer.valueOf(x) >= image.getIconWidth()) {
                    JOptionPane.showMessageDialog(rootPane, "Coordonnée x invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String y = txtY.getText();
                if (y.isEmpty() || !Utils.isInteger(y) || Integer.valueOf(y) < 0 || Integer.valueOf(y) >= image.getIconHeight()) {
                    JOptionPane.showMessageDialog(rootPane, "Coordonnée y invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    ResultSet rs = parent.getServer().getSQLSelection(
                            "SELECT number FROM station WHERE map_fk = '" + parent.getMapName() + "' AND number = '" + number + "'");
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(rootPane, "Une station de même numéro existe déjà", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    rs = parent.getServer().getSQLSelection(
                            "SELECT x, y FROM station WHERE map_fk = '" + parent.getMapName() + "' AND x = '" + x + "' AND y = '" + y + "'");
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(rootPane, "Une station de mêmes coordonnées existe déjà", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    parent.getServer().executeCRUDrequest(
                            "INSERT INTO station(number, x, y, type, map_fk) "
                            + "VALUES('" + number + "','"
                            + x + "','"
                            + y + "','"
                            + (String) cbxType.getSelectedItem() + "','"
                            + parent.getMapName() + "')");
                    JOptionPane.showMessageDialog(rootPane, "Station saisie avec succès", "Confirmation", JOptionPane.DEFAULT_OPTION);
                    parent.updateStationList();
                    GUIAddStation.this.dispose();
                } catch (SQLException ex) {
                    Logger.getLogger(GUIAddStation.class.getName()).log(Level.SEVERE, null, ex);
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
                GUIAddStation.this.dispose();
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

        JPanel formArea = new JPanel(new GridLayout(4, 2));
        formArea.add(lblNumber);
        formArea.add(txtNumber);
        formArea.add(lblX);
        formArea.add(txtX);
        formArea.add(lblY);
        formArea.add(txtY);
        formArea.add(lblType);
        formArea.add(cbxType);

        JPanel controlsArea = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlsArea.add(add);
        controlsArea.add(cancel);

        getContentPane().add(formArea, BorderLayout.CENTER);
        getContentPane().add(controlsArea, BorderLayout.SOUTH);

        setModal(true);
        setTitle("Station");
        pack();
        setVisible(true);
    }
}
