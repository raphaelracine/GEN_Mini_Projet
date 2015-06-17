
package scotlandyardserver.games;

import scotlandyardserver.client.Client;
import scotlandyardserver.json.Station;

public class MisterXPone extends Pone {

    private final int ticketsDouble;
    private final int ticketsBlack;
    
    public MisterXPone(Client player, Station start, int ticketsTaxi, int ticketsBus, int ticketsSubway
    , int ticketsBlack, int ticketsDouble) {
        super(player, start, ticketsTaxi, ticketsBus, ticketsSubway);
        this.ticketsBlack = ticketsBlack;
        this.ticketsDouble = ticketsDouble;
    }
}
