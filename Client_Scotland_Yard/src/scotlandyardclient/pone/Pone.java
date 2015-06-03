package scotlandyardclient.pone;

import java.awt.Color;
import java.util.Observable;
import scotlandyardclient.Client;
import scotlandyardclient.json.Station;

public class Pone extends Observable {

    private int nbTaxiTickets;
    private int nbBusTickets;
    private int nbMetroTickets;

    private final Color color;
    private final String playerName;
    private Station currentStation;

    public Pone(Color color, String playerName, Station currentStation, int nbTaxiTickets, int nbBusTickets, int nbMetroTickets) {
        Client c = Client.getInstance();
        
        if(playerName.equals(c.username())) {
            //System.out.print("Je suis Mister X connard");
            c.setPone(this);
        }
        
        this.color = color;
        this.playerName = playerName;
        this.currentStation = currentStation;
        currentStation.setPone(this);
        this.nbTaxiTickets = nbTaxiTickets;
        this.nbBusTickets = nbBusTickets;
        this.nbMetroTickets = nbMetroTickets;
        setChanged();
        notifyObservers();
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getNbTaxiTickets() {
        return nbTaxiTickets;
    }

    public int getNbBusTickets() {
        return nbBusTickets;
    }

    public int getNbMetroTickets() {
        return nbMetroTickets;
    }
    
    public Station getCurrentStation() {
        return currentStation;
    }
    
    public Color getColor() {
        return color;
    }
    
    public void moveToStation(Station destination) {
        currentStation.setPone(null);        
        currentStation = destination;
        currentStation.setPone(this);
        
        setChanged();
        notifyObservers();
    }
    
    public void useTaxiTicket() {
        // tester si le nombre n'est pas égale à 0, sinon lever une exception
        nbTaxiTickets--;
    }
    
    public void useBusTicket() {
        nbBusTickets--;
    }
    
    public void useMetroTicket() {
        nbMetroTickets--;
    }
    
    public boolean isMisterX() {
        return false;
    }
}
