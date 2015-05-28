package scotlandyardclient.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
import scotlandyardclient.Client;
import com.google.gson.*;
import java.awt.FlowLayout;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import scotlandyardclient.json.GameData;
import scotlandyardclient.json.InfoTickets;
import scotlandyardclient.json.InfoTicketsList;
import scotlandyardclient.json.InfoTicketsMisterX;

class TicketsPanel extends JPanel {
    private final JLabel lblBusTickets;
    private final JLabel lblTaxiTickets;
    private final JLabel lblSubwayTickets;
    private final JLabel busTickets;
    private final JLabel taxiTickets;
    private final JLabel subwayTickets;
    
    public TicketsPanel(int nbTaxiTickets, int nbBusTickets, int nbSubwayTickets){
        setLayout(new GridLayout(3, 2, 5, 5));
        lblBusTickets = new JLabel("Nombre de tickets de bus");
        lblSubwayTickets = new JLabel("Nombre de tickets de métro");
        lblTaxiTickets = new JLabel("Nombre de tickets de taxi");
        busTickets = new JLabel(String.valueOf(nbBusTickets));
        taxiTickets = new JLabel(String.valueOf(nbTaxiTickets));
        subwayTickets = new JLabel(String.valueOf(nbSubwayTickets));
                
        add(lblBusTickets);
        add(busTickets);
        add(lblSubwayTickets);
        add(subwayTickets);
        add(lblTaxiTickets);
        add(taxiTickets);
    }
}

class TicketsPanelMisterX extends TicketsPanel {
    private final JLabel lblBlackTickets;
    private final JLabel lblDoubleTickets;
    private final JLabel blackTickets;
    private final JLabel doubleTickets;
    
    public TicketsPanelMisterX(int nbTaxiTickets, int nbBusTickets, int nbSubwayTickets, int nbBlackTickets, int nbDoubleTickets) {
       super(nbTaxiTickets, nbBusTickets, nbSubwayTickets);
       lblBlackTickets = new JLabel("Nombre de tickets noirs");
       lblDoubleTickets = new JLabel("nombre de cartes coup double");
       
       blackTickets = new JLabel(String.valueOf(nbBlackTickets));
       doubleTickets = new JLabel(String.valueOf(nbDoubleTickets));
               
       add(lblDoubleTickets);
       add(doubleTickets);
       add(lblBlackTickets);
       add(blackTickets);
    }
}

public class GUIGame extends JFrame{
    
    private final GUIMap mapPanel;
    private JPanel southPanel = new JPanel(new GridLayout(1, 2));
    private JPanel eastPanel = new JPanel(new BorderLayout()); 
    private JTabbedPane tabbedPane = new JTabbedPane();
    
    private Vector<String> events = new Vector<>();    
    private JList eventsList = new JList(events);

    public GUIGame() {
        events.add("Début de la partie");
        
        setLayout(new BorderLayout());        
        southPanel.add(tabbedPane);        
        eastPanel.add(eventsList);
        
        InputStream in = new ByteArrayInputStream(Client.getInstance().receiveImage());
        
        BufferedImage background = null;
        
        try {
            background = ImageIO.read(in);
        } catch (IOException ex) {
            Logger.getLogger(GUIGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //GameMap gameMap = new Gson().fromJson(Client.getInstance().receiveCommand(), GameMap.class);
        
        GameData gameData = new Gson().fromJson(Client.getInstance().receiveCommand(), GameData.class);
        
        mapPanel = new GUIMap(gameData.gameMap(), background);
        JScrollPane js = new JScrollPane(mapPanel);
        getContentPane().add(js, BorderLayout.CENTER);
        
        getContentPane().add(southPanel, BorderLayout.SOUTH);
        initSouthPanel();
        getContentPane().add(eastPanel, BorderLayout.EAST);
        
        // récupérer les tickets de tous les détectives
        InfoTicketsList ticketsList = gameData.ticketsList();
        for (InfoTickets t : ticketsList.infoTickets()) {
            tabbedPane.addTab(t.getPlayerName(), new TicketsPanel(t.getTaxi(), t.getBus(), t.getSubway()));
        }
        
        // récupérer les tickets de Mister X
        InfoTicketsMisterX misterXTickets = gameData.misterXTickets();
        tabbedPane.addTab(misterXTickets.getPlayerName(), new TicketsPanelMisterX(
                misterXTickets.getTaxi(), 
                misterXTickets.getBus(), 
                misterXTickets.getSubway(),
                misterXTickets.getDoubleTurn(),
                misterXTickets.getBlack()));
        
        
        setTitle("Partie de Scotland Yard");
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }  
    
    JPanel southPanel() {
        return southPanel;
    }
    
    void initSouthPanel() {
        southPanel.add(new ControlPanel());
    }
    
    public class ControlPanel extends JPanel {
        private final JButton play = new JButton("Jouer");
        
        public ControlPanel() {            
            setLayout(new FlowLayout(FlowLayout.CENTER));
            add(play);
        }
    }
    
    void addTab(String name, TicketsPanel ticketsPanel) {
        tabbedPane.add(name, ticketsPanel);
    }
}
