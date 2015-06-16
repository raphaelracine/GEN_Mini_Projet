/**
 * Classe qui représente l'interface graphique du jeu principale
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import scotlandyardclient.Utils;
import scotlandyardclient.gui.GUIMap;
import scotlandyardclient.json.GameData;
import scotlandyardclient.json.PlayerData;
import scotlandyardclient.json.PlayerDataList;
import scotlandyardclient.json.MisterXData;
import scotlandyardclient.pone.Pone;
import scotlandyardclient.pone.PoneMisterX;

/**
 * Class représentant un panel d'affichage des informations d'un joueur dans le
 * JTabbedPane (cette classe est un observateur car elle observe un pion)
 */
class TicketsPanel extends JPanel implements Observer {

    // Composants graphiques...
    private final JLabel lblBusTickets;
    private final JLabel lblTaxiTickets;
    private final JLabel lblSubwayTickets;
    private final JLabel busTickets;
    private final JLabel taxiTickets;
    private final JLabel subwayTickets;

    private final Pone pone; // Le pion du joueur concerné

    /**
     * Constructeur
     *
     * @param pone Pion dont on veut afficher les informations des tickets
     */
    public TicketsPanel(Pone pone) {
        this.pone = pone;
        pone.addObserver(this);

        setBackground(pone.getColor());
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

    /**
     * Appelée à chaque fois qu'un pion a changé d'état
     *
     * @param o Le pion qui a été modifié
     * @param arg Argument pas utilisé
     */
    @Override
    public void update(Observable o, Object arg) {
        taxiTickets.setText(String.valueOf(pone.getNbTaxiTickets()));
        busTickets.setText(String.valueOf(pone.getNbBusTickets()));
        subwayTickets.setText(String.valueOf(pone.getNbMetroTickets()));
    }

    /**
     * Permet d'obtenir le pion qui concerne ce panel
     *
     * @return Le pion
     */
    protected Pone getPone() {
        return pone;
    }
}

/**
 * Sous-class de Tickets Panel qui représente le pion de Mister X
 */
class TicketsPanelMisterX extends TicketsPanel {

    // Composants graphiques...
    private final JLabel lblBlackTickets;
    private final JLabel lblDoubleTickets;
    private final JLabel blackTickets;
    private final JLabel doubleTickets;

    /**
     * Constructeur
     *
     * @param pone Le pion de Mister X
     */
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

    /**
     * Appelée à chaque mise à jour du pion de Mister X
     *
     * @param o Le pion observé
     * @param arg Argument non utilisé
     */
    @Override
    public void update(Observable o, Object arg) {
        PoneMisterX pone = (PoneMisterX) getPone();
        doubleTickets.setText(String.valueOf(pone.getNbDoubleTickets()));
        blackTickets.setText(String.valueOf(pone.getNbBlackTickets()));
    }
}

public class GUIGame extends JFrame implements Runnable {

    // Composants grpahiques...
    private final GUIMap mapPanel;
    private JPanel southPanel = new JPanel(new GridLayout(1, 2));
    private JPanel eastPanel = new JPanel(new BorderLayout());
    private JTabbedPane tabbedPane = new JTabbedPane();

    private Vector<String> events = new Vector<>();
    private JList eventsList = new JList(events);

    // HashMap pour associer les pions a des noms de joueurs
    private final HashMap<String, Pone> playersPones = new HashMap<>();

    private boolean myTurn; // Dit si c'est au tour du joueur ou pas
    private boolean gameFinished; // Dit si c'est la fin du jeu ou pas

    private Thread gamePlaying; // Thread qui permet à la partie de se dérouler du côté du client

    /**
     * Constructeur
     */
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

        mapPanel = new GUIMap(this, gameData.gameMap(), background);
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
            playersPones.put(pdata.getPlayerName(), playerPone);
            tabbedPane.addTab(pdata.getPlayerName(), new TicketsPanel(playerPone));
        }

        // récupérer les tickets de Mister X
        MisterXData misterXData = gameData.misterXData();
        PoneMisterX poneMisterX = new PoneMisterX(misterXData.getColor(), misterXData.getPlayerName(), gameData.gameMap().getStation(misterXData.getStation()), misterXData.getBlack(), misterXData.getDoubleTurn(), misterXData.getTaxi(), misterXData.getBus(), misterXData.getSubway());
        playersPones.put(misterXData.getPlayerName(), poneMisterX);
        poneMisterX.addObserver(mapPanel);
        tabbedPane.addTab(misterXData.getPlayerName(), new TicketsPanelMisterX(poneMisterX));

        setTitle("Partie de Scotland Yard");
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Permet d'obtenir le panel SUD de la fenêtre (utile pour la sous-classe)
     *
     * @return Panel sud de la fenêtre
     */
    protected JPanel southPanel() {
        return southPanel;
    }

    /**
     * Permet d'initialiser le panel sur à partic de la sous-classe
     */
    void initSouthPanel() {
        southPanel.add(new ControlPanel());
    }

    /**
     * Permet de démarrer le déroulement de la partie
     */
    public void startGame() {
        gamePlaying = new Thread(this);
        gamePlaying.start();
    }

    /**
     * Déroulement de la partie
     */
    @Override
    public void run() {
        while (!gameFinished) {
            String[] cmd = Utils.parseCommand(Client.getInstance().receiveCommand());

            switch (cmd[0]) {
                case "YOUR_TURN":
                    System.out.println("C'est mon tour");
                    myTurn = true;
                    break;
                case "NOT_YOUR_TURN":
                    System.out.println("C'est pas mon tour");
                    myTurn = false;
                    break;
            }
        }
    }

    /**
     * Dit si c'est au tour du client de jouer ou pas
     *
     * @return Retourne true si c'est au client de jouer, false sinon
     */
    public boolean isMyTurn() {
        return myTurn;
    }

    /**
     * Classe interne qui représente les boutons d'actions...
     */
    class ControlPanel extends JPanel {

        private final JButton play = new JButton("Jouer");

        /**
         * Constructeur
         */
        public ControlPanel() {

            play.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!GUIGame.this.isMyTurn()) {
                        JOptionPane.showMessageDialog(rootPane, "Ce n'est pas votre tour de jouer", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            setLayout(new FlowLayout(FlowLayout.CENTER));
            add(play);
        }
    }

    /**
     * Permet d'ajouter un onglet dans les onglets des joueurs
     *
     * @param name Le nom du joueurs
     * @param ticketsPanel Un panel affichant les informations de son pion
     * (tickets)
     */
    void addTab(String name, TicketsPanel ticketsPanel) {
        tabbedPane.add(name, ticketsPanel);
    }

    /**
     * Permet d'ajouter un événement dans la liste d'événements
     *
     * @param event Le nom de l'événement
     */
    public void addEvent(String event) {
        events.add(event);
    }
}
