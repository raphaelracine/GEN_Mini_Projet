/**
 * Classe qui représente la vue d'un lien entre deux stations
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardclient.gui.views;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.LinkedList;
import scotlandyardclient.json.Link;

/**
 * Permet de représenter le moyen de transport de ce lien
 */
enum Locomotion {

    taxi(10, Color.BLACK),
    bus(5, Color.BLUE),
    subway(1, Color.ORANGE);

    private final int width;
    private final Color color;

    /**
     * Constructeur
     *
     * @param width L'épaisseur du lien selon le type de transport
     * @param color La couleur du lien selon le type de transport
     */
    private Locomotion(int width, Color color) {
        this.color = color;
        this.width = width;
    }

    /**
     * Permet d'obtenir l'épaisseur du lien
     *
     * @return L'épaisseur du lien
     */
    public int width() {
        return width;
    }

    /**
     * Permet d'obtenir la couleur du lien
     *
     * @return Couleur du lien
     */
    public Color color() {
        return color;
    }
}

public class LinkView {

    private final Link model; // Le modèle associé à la vue

    /**
     * Constructeur
     *
     * @param model Le modèle associé à la vue
     */
    public LinkView(Link model) {
        this.model = model;
    }

    /**
     * Permet d'obtenir le modèle
     *
     * @return Le modèle
     */
    public Link model() {
        return model;
    }

    /**
     * Permet de dessiner le lien
     *
     * @param g Contexte graphique
     */
    public void draw(Graphics2D g) {
        LinkedList<String> locomotions = model.getLocomotions();
        boolean taxi = false;
        boolean bus = false;
        boolean subway = false;

        for (String s : locomotions) {
            drawLine(Locomotion.valueOf(s), g);
        }
    }

    /**
     * Permet de dessiner une ligne représente le lien
     *
     * @param l Moyen de transport à dessiner
     * @param g Contexte graphique pour dessiner
     */
    private void drawLine(Locomotion l, Graphics2D g) {
        int x1 = model.getFirst().getX();
        int x2 = model.getSecond().getX();
        int y1 = model.getFirst().getY();
        int y2 = model.getSecond().getY();
        g.setStroke(new BasicStroke(l.width()));
        g.setColor(l.color());
        g.drawLine(x1 + 15, y1 + 15, x2 + 15, y2 + 15);
    }
}
