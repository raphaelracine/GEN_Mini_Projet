/**
 * Cette classe permet de transmettre une partie en JSon
 *
 * Il est à noter que toutes ces méthodes sont décrites dans la classe Game mais
 * elle sont appelées par délégation
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
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
