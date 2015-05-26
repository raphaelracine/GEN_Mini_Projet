package scotlandyardserver.games;

import scotlandyardserver.client.Client;
import scotlandyardserver.json.Station;

public class DetectivePone extends Pone {

    public DetectivePone(Client player, Station start, int ticketsTaxi, int ticketsBus, int ticketsSubway) {
        super(player, start, ticketsTaxi, ticketsBus, ticketsSubway);
    }
    
}
