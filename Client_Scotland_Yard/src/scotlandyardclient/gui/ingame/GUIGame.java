package scotlandyardclient.gui.ingame;

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
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import scotlandyardclient.gui.GUIMap;
import scotlandyardclient.json.GameData;
import scotlandyardclient.json.PlayerData;
import scotlandyardclient.json.PlayerDataList;
import scotlandyardclient.json.MisterXData;
import scotlandyardclient.pone.Pone;
import scotlandyardclient.pone.PoneMisterX;

class TicketsPanel extends JPanel implements Observer {

    private final JLabel lblBusTickets;
    private final JLabel lblTaxiTickets;
    private final JLabel lblSubwayTickets;
    private final JLabel busTickets;
    private final JLabel taxiTickets;
    private final JLabel subwayTickets;

    private final Pone pone;

    public TicketsPanel(Pone pone) {
        this.pone = pone;
        pone.addObserver(this);

        setLayout(new GridLayout(3, 2, 5, 5));
        lblBusTickets = new JLabel("Nombre de tickets de bus");
        lblSubwayTickets = new JLabel("Nombre de tickets de métro");
        lblTaxiTickets = new JLabel("Nombre de tickets de taxi");
        busTickets = new JLabel(String.valueOf(pone.getNbBusTickets()));
        taxiTickets = new JLabel(String.valueOf(pone.getNbTaxiTickets()));
        subwayTickets = new JLabel(String.valueOf(pone.getNbMetroTickets()));

        add(lblBusTickets);
        add(busTickets);
        add(lblSubwayTickets);
        add(subwayTickets);
        add(lblTaxiTickets);
        add(taxiTickets);
    }

    @Override
    public void update(Observable o, Object arg) {
        taxiTickets.setText(String.valueOf(pone.getNbTaxiTickets()));
        busTickets.setText(String.valueOf(pone.getNbBusTickets()));
        subwayTickets.setText(String.valueOf(pone.getNbMetroTickets()));
    }
    
    protected Pone getPone() {
        return pone;
    }
}

class TicketsPanelMisterX extends TicketsPanel {

    private final JLabel lblBlackTickets;
    private final JLabel lblDoubleTickets;
    private final JLabel blackTickets;
    private final JLabel doubleTickets;

    public TicketsPanelMisterX(PoneMisterX pone) {
        super(pone);
        lblBlackTickets = new JLabel("Nombre de tickets noirs");
        lblDoubleTickets = new JLabel("nombre de cartes coup double");

        blackTickets = new JLabel(String.valueOf(pone.getNbBlackTickets()));
        doubleTickets = new JLabel(String.valueOf(pone.getNbDoubleTickets()));

        add(lblDoubleTickets);
        add(doubleTickets);
        add(lblBlackTickets);
        add(blackTickets);
    }

    @Override
    public void update(Observable o, Object arg) {
        PoneMisterX pone = (PoneMisterX) getPone();
        doubleTickets.setText(String.valueOf(pone.getNbDoubleTickets()));
        blackTickets.setText(String.valueOf(pone.getNbBlackTickets()));
    }
}

public class GUIGame extends JFrame {

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

        GameData gameData = new Gson().fromJson(Client.getInstance().receiveCommand(), GameData.class);

        mapPanel = new GUIMap(gameData.gameMap(), background);
        JScrollPane js = new JScrollPane(mapPanel);
        getContentPane().add(js, BorderLayout.CENTER);

        getContentPane().add(southPanel, BorderLayout.SOUTH);
        initSouthPanel();
        getContentPane().add(eastPanel, BorderLayout.EAST);

        // récupérer les informations de début de partie de tous les détectives
        PlayerDataList playerDataList = gameData.playerDataList();
        for (PlayerData pdata : playerDataList.playersData()) {
            Pone playerPone = new Pone(pdata.getColor(), pdata.getPlayerName(), gameData.gameMap().getStation(pdata.getStation()), pdata.getTaxi(), pdata.getBus(), pdata.getSubway());
            playerPone.addObserver(mapPanel);
            tabbedPane.addTab(pdata.getPlayerName(), new TicketsPanel(playerPone));
        }

        // récupérer les tickets de Mister X
        MisterXData misterXData = gameData.misterXData();
        PoneMisterX poneMisterX = new PoneMisterX(misterXData.getColor(), misterXData.getPlayerName(), gameData.gameMap().getStation(misterXData.getStation()), misterXData.getBlack(), misterXData.getDoubleTurn(), misterXData.getTaxi(), misterXData.getBus(), misterXData.getSubway());
        poneMisterX.addObserver(mapPanel);
        tabbedPane.addTab(misterXData.getPlayerName(), new TicketsPanelMisterX(poneMisterX));

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
    
    public void addEvent(String event) {
        events.add(event);
    }
}
