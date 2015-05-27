
package scotlandyardclient;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import scotlandyardclient.gui.stationviews.StationView;
import scotlandyardclient.json.Station;

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
        new StationView(new Station(1, 128, "taxibus", 50, 50)).draw(g);
    }
}
