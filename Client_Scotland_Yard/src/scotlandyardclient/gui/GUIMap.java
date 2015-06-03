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
import scotlandyardclient.Client;
import scotlandyardclient.gui.views.LinkView;
import scotlandyardclient.gui.views.StationView;
import scotlandyardclient.json.GameMap;
import scotlandyardclient.json.Link;
import scotlandyardclient.json.Station;

public class GUIMap extends JPanel implements Observer {
    
    private final BufferedImage background;
    private final GameMap map;
    
    private final LinkedList<StationView> stationViews = new LinkedList<>();
    private final LinkedList<LinkView> linkViews = new LinkedList<>();
    
    public GUIMap(GameMap map, BufferedImage background) {
        this.background = background;
        this.map = map;  
        
        setPreferredSize(new Dimension(background.getWidth(), background.getHeight()));
        
        for(Station s : map.getStations())
            stationViews.add(new StationView(s));
        
        for(Link l : map.getLinks()) {
            Link newLink = new Link(map.getStation(l.getFirst().getId()), map.getStation(l.getSecond().getId()));
            
            for(String locomotion : l.getLocomotions())
                newLink.addLocomotion(locomotion);
            
            linkViews.add(new LinkView(newLink));
        }
        
        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                for(StationView sv : stationViews)
                    if(sv.contains(e.getX(), e.getY()))
                {
                    Client.getInstance().getPone().moveToStation(sv.getStation());
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
        
        for(LinkView lv : linkViews)
            lv.draw((Graphics2D)g);
        
        for(StationView sv : stationViews)
            sv.draw((Graphics2D)g);            
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}
