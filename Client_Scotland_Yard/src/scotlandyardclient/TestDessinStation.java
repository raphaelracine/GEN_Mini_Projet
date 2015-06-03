
package scotlandyardclient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import scotlandyardclient.gui.views.StationView;
import scotlandyardclient.json.Station;
import scotlandyardclient.pone.Pone;

public class TestDessinStation extends JFrame {

    
    public TestDessinStation() {       
        setPreferredSize(new Dimension(500, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        StationView s1 = new StationView(new Station(1, 128, "taxi", 50, 100));
        StationView s2 = new StationView(new Station(1, 159, "taxibus", 200, 300));
                
        s1.getStation().setPone(new Pone(Color.MAGENTA, "Connard", s1.getStation(), 0, 0, 0));
        
        s1.draw((Graphics2D)g);
        s2.draw((Graphics2D)g);
        
        try {
            Thread.sleep(5000);
            s1.getStation().getPone().moveToStation(s2.getStation());
        } catch (InterruptedException ex) {
            Logger.getLogger(TestDessinStation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        g.fillRect(0, 0, 500, 500);
        
        s1.draw((Graphics2D)g);
        s2.draw((Graphics2D)g);
    }
}
