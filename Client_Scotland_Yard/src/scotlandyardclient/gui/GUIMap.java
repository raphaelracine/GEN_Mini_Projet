/**
 * Classe qui représente l'interface graphique de la carte du jeu (dans une
 * partie)
 *
 * Cette classe est un Observateur car elle observe les pions qui se déplacent.
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardclient.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import scotlandyardclient.Client;
import scotlandyardclient.gui.ingame.GUIGame;
import scotlandyardclient.gui.ingame.GUIMove;
import scotlandyardclient.gui.ingame.GUIMoveMisterX;
import scotlandyardclient.gui.views.LinkView;
import scotlandyardclient.gui.views.StationView;
import scotlandyardclient.json.GameMap;
import scotlandyardclient.json.Link;
import scotlandyardclient.json.Station;

public class GUIMap extends JPanel implements Observer {

    private final BufferedImage background; // Image de fond de la carte
    private final GameMap map; // La carte de la partie
    private final GUIGame parent; // L'interface graphique de jeu parent

    // Les vues à dessiner
    private final LinkedList<StationView> stationViews = new LinkedList<>();
    private final LinkedList<LinkView> linkViews = new LinkedList<>();

    /**
     * Constructeur
     *
     * @param parent L'interface graphique de la partie parente
     * @param map // Données de la carte du jeu
     * @param background // L'image de fond de la carte
     */
    public GUIMap(GUIGame parent, GameMap map, BufferedImage background) {
        this.background = background;
        this.map = map;
        this.parent = parent;

        setPreferredSize(new Dimension(background.getWidth(), background.getHeight()));

        for (Station s : map.getStations()) {
            stationViews.add(new StationView(s));
        }

        for (Link l : map.getLinks()) {
            Link newLink = new Link(map.getStation(l.getFirst().getId()), map.getStation(l.getSecond().getId()));

            for (String locomotion : l.getLocomotions()) {
                newLink.addLocomotion(locomotion);
            }

            linkViews.add(new LinkView(newLink));
        }

        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (!parent.isMyTurn()) {
                    JOptionPane.showMessageDialog(GUIMap.this, "Ce n'est pas votre tour !", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                for (StationView sv : stationViews) {
                    if (sv.contains(e.getX(), e.getY())) {
                        Station destination = sv.getStation();

                        if ((destination.getPone() != null) && !destination.getPone().isMisterX()) {
                            JOptionPane.showMessageDialog(GUIMap.this, "Il y a un détective sur cette station", "Erreur", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        for (Station s : map.getStations()) {
                            for (LinkView lv : linkViews) {
                                if (lv.model().connectStations(s, destination)) {
                                    if (s.getPone() == Client.getInstance().getPone()) {
                                        if (s.getPone().isMisterX()) {
                                            new GUIMoveMisterX(lv.model(), destination);
                                        } else {
                                            new GUIMove(lv.model(), destination);
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
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

    }

    /**
     * Permet de dessiner la carte, avec les stations les liens et les pions
     *
     * @param g Contexte graphique pour dessiner
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(background, 0, 0, this);

        for (LinkView lv : linkViews) {
            lv.draw((Graphics2D) g);
        }

        for (StationView sv : stationViews) {
            sv.draw((Graphics2D) g);
        }
    }

    /**
     * Mise à jour de l'affichage de la carte à chaque fois qu'un pion se
     * déplace
     *
     * @param o Le pion qui s'est déplacé
     * @param arg Arguments (pas utile ici...)
     */
    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}
