package scotlandyardserver.json;

public class InfoTicketsMisterX extends InfoTickets {
    private final int doubleTurn;
    private final int black;
    
    public InfoTicketsMisterX(String playerName, int taxi, int bus, int subway, int doubleTurn, int black) {
        super(playerName, taxi, bus, subway);
        this.doubleTurn = doubleTurn;
        this.black = black;
    }
}
