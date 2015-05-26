package scotlandyardclient.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;


class TicketsPanel extends JPanel{
    private final JLabel lblBusesNumber;
    private final JLabel lblTaxiNumber;
    private final JLabel lblSubwayNumber;
    private final JLabel BusesNumber = new JLabel();
    private final JLabel TaxiNumber = new JLabel();
    private final JLabel SubwayNumber = new JLabel();
    
    public TicketsPanel(){
        setLayout(new GridLayout(3, 2, 5, 5));
        lblBusesNumber = new JLabel("Nombre de tickets de bus");
        lblSubwayNumber = new JLabel("Nombre de tickets de métro");
        lblTaxiNumber = new JLabel("nombre de tickets de taxi");
        // en attende de la reponse du serveur avec le Json
        BusesNumber.setText("0");
        TaxiNumber.setText("0");
        SubwayNumber.setText("0");
        add(lblBusesNumber);
        add(BusesNumber);
        add(lblSubwayNumber);
        add(SubwayNumber);
        add(lblTaxiNumber);
        add(TaxiNumber);
    }
}
class MixterXTicketsPanel extends TicketsPanel{
    private final JLabel lblTicketNoirNumber;
    private final JLabel lblCartesCoupDoubleNumber;
    private final JLabel TicketNoirNumber;
    private final JLabel CartesCoupDoubleNumber;
    
    public MixterXTicketsPanel(){
       super();
       lblCartesCoupDoubleNumber = new JLabel("Nombre de tickets noirs");
       lblTicketNoirNumber = new JLabel("nombre de cartes coup double");
       CartesCoupDoubleNumber = new JLabel();
       CartesCoupDoubleNumber.setText("0");
       TicketNoirNumber = new JLabel();
       TicketNoirNumber.setText("0");
       add(lblCartesCoupDoubleNumber);
       add(CartesCoupDoubleNumber);
       add(lblTicketNoirNumber);
       add(TicketNoirNumber);
    }
}
public class GUIGamePlateform extends JFrame{
    
    private JPanel centerPanel = new JPanel();
    private JPanel southPanel = new JPanel();
    private JPanel eastPanel = new JPanel(); 
    private JTabbedPane tabbedPane = new JTabbedPane();
    private JList eventsList;

    public GUIGamePlateform() {
        setLayout(new BorderLayout());
        Vector events = new Vector();
        String[] players = {"michelle", "yassin","raphael", "MIXTERX"};
        for( int i = 0; i < players.length; i++){
            tabbedPane.addTab(players[i], createPanel(players[i]));
        }
        southPanel.add(tabbedPane);
        eventsList = new JList(events);
        eastPanel.add(eventsList);
        centerPanel.add(new JButton("hello"));
        
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
        add(eastPanel, BorderLayout.EAST);
        
        setPreferredSize(new Dimension(800, 800));
        setTitle("GUIGamePlateform");
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    private JPanel createPanel(String playerName){
        if(playerName == "MIXTERX"){
            JCheckBox moveTwoTimesInSuccession = new JCheckBox("se déplacer deux fois de suite");
            JCheckBox hideMeansOfTransport = new JCheckBox("cacher son moyen de transport");
            JPanel mixterXPanel = new JPanel();
            mixterXPanel.setLayout(new BoxLayout(mixterXPanel, BoxLayout.Y_AXIS));    
            mixterXPanel.add(new MixterXTicketsPanel());
            mixterXPanel.add(moveTwoTimesInSuccession);
            mixterXPanel.add(hideMeansOfTransport);
            return mixterXPanel;
        }
        else{
            JPanel detectivePanel = new JPanel();
            detectivePanel.add(new TicketsPanel());
            return detectivePanel;
        }
    }
    
}
