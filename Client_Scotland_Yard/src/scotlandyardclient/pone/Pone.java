package scotlandyardclient.pone;

import java.awt.Color;
import java.util.Observable;
import scotlandyardclient.json.Station;

public class Pone extends Observable {

    private int nbTaxiTickets;
    private int nbBusTickets;
    private int nbMetroTickets;

    private final Color color;
    private final String playerName;
    private Station currentStation;

    public Pone(Color color, String playerName, Station currentStation, int nbTaxiTickets, int nbBusTickets, int nbMetroTickets) {
        this.color = color;
        this.playerName = playerName;
        this.currentStation = currentStation;
        this.nbTaxiTickets = nbTaxiTickets;
        this.nbBusTickets = nbBusTickets;
        this.nbMetroTickets = nbMetroTickets;
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
    
    public void moveToStation(Station s) {
        currentStation.setPone(null);        
        currentStation = s;
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
