
package scotlandyardserver.games;

import scotlandyardserver.client.Client;

public class Game implements Runnable {
    private final Thread thread;
    
    private final Client host;
    private final String name;
    private final int numberOfPlayers;
    private final String map;
    
    /**
     * Constructeur
     * @param host L'hôte de la partie
     * @param name Le nom de la partie
     * @param numberOfPlayers Le nombre de joueurs
     * @param map Le nom de la carte
     */
    public Game(Client host, String name, int numberOfPlayers, String map) {
        this.host = host;
        this.name = name;
        this.numberOfPlayers = numberOfPlayers;
        this.map = map;
        
        thread = new Thread(this);
        thread.start();
    }
    
    @Override
    public void run() {
        System.out.println("Nouvelle partie créee...");
    }

    public String getName() {
        return name;
    }
}
