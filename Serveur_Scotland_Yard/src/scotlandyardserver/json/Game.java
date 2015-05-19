package scotlandyardserver.json;

public class Game {
    private final String name;
    private final String host;
    private final String map;
    private final int nbCurrentPlayers;
    private final int nbNumberPlayers;
    
    public Game(String name, String host, String map, int nbCurrentPlayers, int nbNumberPlayers) {
        this.name = name;
        this.host = host;
        this.map = map;
        this.nbCurrentPlayers = nbCurrentPlayers;
        this.nbNumberPlayers = nbNumberPlayers;
    }
}
