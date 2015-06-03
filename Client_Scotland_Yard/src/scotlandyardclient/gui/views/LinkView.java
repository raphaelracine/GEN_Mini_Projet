package scotlandyardclient.gui.views;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.LinkedList;
import scotlandyardclient.json.Link;

enum Locomotion {

    taxi(10, Color.BLACK),
    bus(5, Color.BLUE),
    subway(1, Color.ORANGE);

    private final int WIDTH;
    private final Color color;

    private Locomotion(int increment, Color color) {
        this.color = color;
        this.WIDTH = increment;
    }

    public int width() {
        return WIDTH;
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
        LinkedList<String> locomotions = model.getLocomotions();
        boolean taxi = false;
        boolean bus = false;
        boolean subway = false;
        
        for (String s : locomotions) {
            if (s.equals("taxi"))
                taxi = true;
            else if (s.equals("bus"))
                bus = true;
            else if (s.equals("subway"))
                subway = true;
        }
        
        if (taxi) {
            drawLine(Locomotion.valueOf("taxi"), g);
        }
        
        if (bus) {
            drawLine(Locomotion.valueOf("bus"), g);
        }
        
        if (subway) {
            drawLine(Locomotion.valueOf("subway"), g);
        } 
    }

    private void drawLine(Locomotion l, Graphics2D g) {
        int x1 = model.getFirst().getX();
        int x2 = model.getSecond().getX();
        int y1 = model.getFirst().getY();
        int y2 = model.getSecond().getY();
        g.setStroke(new BasicStroke(l.width()));
        g.setColor(l.color());
        g.drawLine(x1 +15, y1 +15, x2 +15, y2 +15);
    }
}
