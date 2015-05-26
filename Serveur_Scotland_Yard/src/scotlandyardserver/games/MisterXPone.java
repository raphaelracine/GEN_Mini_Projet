
package scotlandyardserver.games;

import scotlandyardserver.client.Client;
import scotlandyardserver.json.Station;

public class MisterXPone extends Pone {

    private int ticketsDouble;
    private int ticketsBlack;
    
    public MisterXPone(Client player, Station start, int ticketsTaxi, int ticketsBus, int ticketsSubway
    , int ticketsBlack, int ticketsDouble) {
        super(player, start, ticketsTaxi, ticketsBus, ticketsSubway);
        this.ticketsBlack = ticketsBlack;
        this.ticketsDouble = ticketsDouble;
    }  
    
    public int ticketsDouble() {
        return ticketsDouble;
    }
    
    public int ticketsBlack() {
        return ticketsBlack;
    }
}
