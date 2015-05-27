package scotlandyardserver.json;

public class InfoTickets {
    private final int taxi;
    private final int bus;
    private final int subway;
    private String playerName;
    
    public InfoTickets(String playerName, int taxi, int bus, int subway) {
        this.taxi = taxi;
        this.bus = bus;
        this.subway = subway;
        this.playerName = playerName;
    }
}
