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

    public void draw(Graphics2D g) {
        for (String locomotion : model.getLocomotions()) {
            drawLine(Locomotion.valueOf(locomotion), g);
        }
    }

    private void drawLine(Locomotion l, Graphics2D g) {
        g.setStroke(new BasicStroke(2));
        g.setColor(l.color());
        /*.drawLine(model.getFirst().getX() + l.increment(),
         model.getFirst().getY() + l.increment(),
         model.getSecond().getX() + l.increment(),
         model.getSecond().getY() + l.increment());*/

        /*int x0 = model.getFirst().getX();
        int x1 = model.getSecond().getX();
        int y0 = model.getFirst().getY();
        int y1 = model.getSecond().getY();        
        
        int r = (int) Math.sqrt((x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0));
        int x = x0 - r;
        int y = y0 - r;
        int width = 2 * r;
        int height = 2 * r;
        int startAngle = (int) (180 / Math.PI * Math.atan2(y1 - y0, x1 - x0));
        int endAngle = (int) (180 / Math.PI * Math.atan2(y2 - y0, x2 - x0));
        g.drawArc(x, y, width, height, startAngle, endAngle);*/

    }
}
