/**
 * Classe qui permet de recevoir les données d'une partie en JSon
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardclient.json;

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
