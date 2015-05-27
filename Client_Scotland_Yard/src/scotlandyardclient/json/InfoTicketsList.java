package scotlandyardclient.json;

import java.util.LinkedList;

public class InfoTicketsList {
    private LinkedList<InfoTickets> infosTickets = new LinkedList<>();
    
    public LinkedList<InfoTickets> infoTickets() {
        return infosTickets;
    }
    
    public void add(InfoTickets infoTickets) {
        infosTickets.add(infoTickets);
    }
}
