
package scotlandyardserver.games;

import java.util.LinkedList;
import scotlandyardserver.client.Client;
import scotlandyardserver.games.state.GameState;
import scotlandyardserver.games.state.WaitingPlayersState;

public class Game {
    
    private GameState state;
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
        
        setState(new WaitingPlayersState(this));
    }
    
    public String getName() {
        return name;
    }
    
    public Client getHost() {
        return host;
    }
    
    public String getMap() {
        return map;
    }
    
    public int numberOfPlayers() {
        return numberOfPlayers;
    }
    
    public LinkedList<Client> players() {
        return players;
    }
    
    public int currentNumberOfPlayers() {
        return players.size();
    }
    
    public boolean joinGame(Client client) {        
        return state.joinGame(client);
    }
    
    public void leaveGame(Client client) {
        state.leaveGame(client);
    }

    public void setState(GameState newState) {
        this.state = newState;
    }

    public void addPlayer(Client client) {
        players.add(client);
    }
    
    public void removePlayer(Client client) {
        players.remove(client);
    }
}
