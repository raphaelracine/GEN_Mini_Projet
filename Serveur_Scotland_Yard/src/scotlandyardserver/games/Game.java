
package scotlandyardserver.games;

import java.util.LinkedList;
import scotlandyardserver.client.Client;

public class Game implements Runnable {
    private final Thread thread;
    
    private final Client host;
    private final String name;
    private final int numberOfPlayers;
    private final String map;
    private final LinkedList<Client> players = new LinkedList<>();
    
    /**
     * Constructeur
     * @param host L'hôte de la partie
     * @param name Le nom de la partie
     * @param numberOfPlayers Le nombre de joueurs
     * @param map Le nom de la carte
     */
    public Game(Client host, String name, int numberOfPlayers, String map) {
        this.host = host;
        players.add(host);
        this.name = name;
        this.numberOfPlayers = numberOfPlayers;
        this.map = map;
        
        thread = new Thread(this);
        thread.start();
    }
    
    @Override
    public void run() {
        // TODO
    }

    public String getName() {
        return name;
    }
    
    public String getHost() {
        return host.username();
    }
    
    public String getMap() {
        return map;
    }
    
    public int numberOfPlayers() {
        return numberOfPlayers;
    }
    
    public int currentNumberOfPlayers() {
        return players.size();
    }
    
    public void addPlayer(Client client) {
        players.add(client);
    }
}
