/**
 * Classe qui représente la vue d'une station
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardclient.gui.views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import scotlandyardclient.json.Station;
import scotlandyardclient.pone.Pone;

public class StationView {

    private final Station model; // Le modèle de la vue
    private final Rectangle2D clickZone; // Zone de clique

    /**
     * Constructeur
     *
     * @param model Modèle de la vue
     */
    public StationView(Station model) {
        this.model = model;
        clickZone = new Rectangle2D.Double(model.getX(), model.getY(), 50, 50);
    }

    /**
     * Permet de savoir si le point P(x, y) est contenu dans la station ou pas
     *
     * @param x Coordonnée x du point P
     * @param y Coordonnée y du point P
     * @return Retourne true si P se trouve dans la station, false sinon
     */
    public boolean contains(int x, int y) {
        return clickZone.contains(x, y);
    }

    /**
     * Permet de dessiner la station
     *
     * @param g Contexte graphique de dessin
     */
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke());

        g.drawOval(model.getX(), model.getY(), 50, 50);

        g.setColor(Color.white);
        g.fillOval(model.getX() + 1, model.getY() + 1, 49, 49);

        g.setColor(Color.black);
        g.drawLine(model.getX(), model.getY() + 25, model.getX() + 50, model.getY() + 25);

        if (model.getType().equals("taxibussubway") || model.getType().equals("taxibus")) {
            g.setColor(Color.BLUE);
            g.fillArc(model.getX(), model.getY(), 50, 50, 180, 180);
        }

        g.setColor(Color.black);
        g.drawRect(model.getX() + 10, model.getY() + 13, 30, 25);

        g.setColor(Color.white);
        if (model.getType().equals("taxibussubway")) {
            g.setColor(Color.ORANGE);
        }
        g.fillRect(model.getX() + 11, model.getY() + 14, 28, 23);

        g.setColor(Color.black);
        g.drawString(String.valueOf(model.getNumero()), model.getX() + 15, model.getY() + 30);

        Pone p = model.getPone();

        if (p != null) {
            g.setStroke(new BasicStroke(3));
            g.setColor(p.getColor());
            g.drawOval(model.getX() - 1, model.getY() - 1, 53, 52);
        }
    }

    /**
     * Permet d'obtenir le modèle
     *
     * @return Le modèle
     */
    public Station getStation() {
        return model;
    }
}
