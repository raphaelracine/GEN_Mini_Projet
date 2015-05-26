package scotlandyardserver.games;

import scotlandyardserver.client.Client;
import scotlandyardserver.json.Station;

public abstract class Pone {
    private Station currentStation;
    private Client player;
    
    private final int ticketsTaxi;
    private final int ticketsBus;
    private final int ticketsSubway;
    
    public Pone(Client player, Station start, int ticketsTaxi, int ticketsBus, int ticketsSubway) {
        this.player = player;
        this.currentStation = start;
        this.ticketsBus = ticketsBus;
        this.ticketsSubway = ticketsSubway;
        this.ticketsTaxi = ticketsTaxi;
    }
    
    public void moveInStation(Station dest) {
        this.currentStation = dest;
    }
    
    public Client getPlayer() {
        return player;
    }
    
    public Station getCurrentStation() {
        return currentStation;
    }
    
    public int getTicketsTaxi() {
        return ticketsTaxi;
    }
    
    public int getTicketsSubway() {
        return ticketsSubway;
    }
    
    public int getTicketsBus() {
        return ticketsBus;
    }
}
