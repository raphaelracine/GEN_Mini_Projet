package scotlandyardclient.gui.views;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Color;
import scotlandyardclient.json.Link;

enum Locomotion {

    taxi(0, Color.WHITE),
    bus(-10, Color.BLUE),
    subway(10, Color.ORANGE);

    private final int increment;
    private final Color color;

    private Locomotion(int increment, Color color) {
        this.color = color;
        this.increment = increment;
    }

    public int increment() {
        return increment;
    }

    public Color color() {
        return color;
    }
}

public class LinkView {

    private final Link model;

    public LinkView(Link model) {
        this.model = model;
    }

    public Link model() {
        return model;
    }
    
    public void draw(Graphics2D g) {
        
    }

    private void drawLine(Locomotion l, Graphics2D g) {
        g.setStroke(new BasicStroke(2));
        g.setColor(l.color());
    }
}
