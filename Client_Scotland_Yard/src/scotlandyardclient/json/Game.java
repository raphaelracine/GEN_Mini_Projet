package scotlandyardclient.json;

public class Game {
    private String name;
    private String host;
    private String map;
    private int nbCurrentPlayers;
    private int nbNumberPlayers;
    
    public Game(String name, String host, String map, int nbCurrentPlayers, int nbNumberPlayers) {
        this.name = name;
        this.host = host;
        this.map = map;
        this.nbCurrentPlayers = nbCurrentPlayers;
        this.nbNumberPlayers = nbNumberPlayers;
    }
    
    public String name() {
        return name;
    }
    
    public String host() {
        return host;
    }
    
    public String map() {
        return map;
    }   
    
    public int getCurrentPlayers() {
        return nbCurrentPlayers;
    }
    
    public int getNumberPlayers() {
        return nbNumberPlayers;
    }
}
