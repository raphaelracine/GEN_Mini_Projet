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
import javax.swing.JPanel;
import scotlandyardclient.gui.stationviews.StationView;
import scotlandyardclient.json.GameMap;
import scotlandyardclient.json.Station;

public class GUIMap extends JPanel implements Observer {
    
    private final BufferedImage background;
    private final GameMap map;
    
    private final LinkedList<StationView> stationViews = new LinkedList<>();
    
    public GUIMap(GameMap map, BufferedImage background) {
        this.background = background;
        this.map = map;  
        
        setPreferredSize(new Dimension(background.getWidth(), background.getHeight()));
        
        for(Station s : map.getStations())
            stationViews.add(new StationView(s));
        
        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                for(StationView sv : stationViews)
                    if(sv.contains(e.getX(), e.getY()))
                {
                        System.out.println(sv.getStation().getNumero());
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
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(background, 0, 0, this);
        
        for(StationView sv : stationViews)
            sv.draw((Graphics2D)g);            
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}
