/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scotlandyardserver.admin.map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import javax.imageio.*;
import javax.swing.filechooser.*;
import scotlandyardserver.Utils;

/**
 *
 * @author Yassin
 */
public class GUIAddMap extends JDialog {

    private final int WIDTH_MIN = 400;
    private final int HEIGHT_MIN = 300;

    private GUIMap mapManager;

    private final JLabel lblMap;
    private final JLabel lblUrl;

    private JTextField txtMap;

    private final JButton url;
    private final JButton add;
    private final JButton cancel;

    private String mapUrl;

    public GUIAddMap(GUIMap mapManager) {
        this.mapManager = mapManager;

        lblMap = new JLabel("Carte:");
        lblUrl = new JLabel("URL:");

        txtMap = new JTextField();
        txtMap.setColumns(20);

        url = new JButton("...");
        url.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "JPG & GIF Images", "jpg", "gif");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(GUIAddMap.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String name = chooser.getSelectedFile().getAbsolutePath();
                    try {
                        Image image = ImageIO.read(new File(name));
                        if (image == null) {
                            JOptionPane.showMessageDialog(rootPane, "Le fichier ne peut pas être ouvert, ce n'est pas une image", "Erreur", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        if (image.getWidth(null) < WIDTH_MIN || image.getHeight(null) < HEIGHT_MIN) {
                            JOptionPane.showMessageDialog(rootPane, "L'image doit être au minimum " + WIDTH_MIN + "x" + HEIGHT_MIN, "Erreur", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        mapUrl = chooser.getSelectedFile().getPath().replace("\\", "\\\\");

                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(rootPane, "Le fichier ne peut pas être ouvert, une erreur s'est produite", "Erreur", JOptionPane.ERROR_MESSAGE);

                    }
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
                if (!Utils.checkPatternMatches(txtMap.getText(), "^(?=.*.)(?!.*[#|\\s]).{6,20}$")) {
                    JOptionPane.showMessageDialog(rootPane, "Nom de carte invalide...", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (mapUrl.isEmpty()) {

                    return;
                }
                try {
                    mapManager.getServer().executeCRUDrequest(
                            "INSERT INTO map(name, picture) "
                            + "VALUES('" + txtMap.getText() + "','"
                            + mapUrl + "')");
                    JOptionPane.showMessageDialog(rootPane, "Carte saisie avec succès", "Confirmation", JOptionPane.DEFAULT_OPTION);

                    mapManager.updateMapList();
                    GUIAddMap.this.dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(rootPane, "Une carte de même nom existe déjà", "Erreur", JOptionPane.ERROR_MESSAGE);
//Logger.getLogger(GUIAddMap.class.getName()).log(Level.SEVERE, null, ex);
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
                GUIAddMap.this.dispose();
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
        formArea.add(lblMap);
        formArea.add(txtMap);
        formArea.add(lblUrl);
        formArea.add(url);

        JPanel controlsArea = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlsArea.add(add);
        controlsArea.add(cancel);

        getContentPane().add(formArea, BorderLayout.CENTER);
        getContentPane().add(controlsArea, BorderLayout.SOUTH);

        setModal(true);
        setTitle("Nouvelle carte");
        pack();
        setVisible(true);
    }
}
