package scotlandyardclient.gui.stationviews;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import scotlandyardclient.json.Station;

public class StationView {
    
    private final Station model;
    private final Rectangle2D clickZone;
    
    public StationView(Station model) {
        this.model = model;        
        clickZone = new Rectangle2D.Double(model.getX(), model.getY(), 50, 50);
    }
    
    public boolean contains(int x, int y) {
        return clickZone.contains(x, y);
    }
    
    public void draw(Graphics g) {
        g.drawOval(model.getX(), model.getY(), 50, 50);  
        
        g.setColor(Color.white);
        g.fillOval(model.getX() + 1, model.getY() + 1, 49, 49);
        
        g.setColor(Color.black);
        g.drawLine(model.getX(), model.getY() + 25, model.getX() + 50, model.getY() + 25);
        
        if(model.getType().equals("taxibussubway") || model.getType().equals("taxibus")) {
            g.setColor(Color.BLUE);
            g.fillArc(model.getX(), model.getY(), 50, 50, 180, 180);
        }
        
        g.setColor(Color.black);
        g.drawRect(model.getX() + 10, model.getY() + 13, 30, 25);
        
        g.setColor(Color.white);
        if(model.getType().equals("taxibussubway"))
            g.setColor(Color.ORANGE);
        g.fillRect(model.getX() + 11, model.getY() + 14, 28, 23);
        
        g.setColor(Color.black);
        g.drawString(String.valueOf(model.getNumero()), model.getX() + 15, model.getY() + 30);
    }
    
    public Station getStation() {
        return model;
    }
}
