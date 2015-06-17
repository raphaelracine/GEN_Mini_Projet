/**
 * Cette classe permet de transmettre les données d'un détective au début de la
 * partie en JSon
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

import java.awt.Color;

public class PlayerData {

    private final String playerName;
    private final int taxi;
    private final int bus;
    private final int subway;
    private final int station;
    private final Color color;

    public PlayerData(String playerName, int taxi, int bus, int subway, int station, Color color) {
        this.taxi = taxi;
        this.bus = bus;
        this.subway = subway;
        this.playerName = playerName;
        this.station = station;
        this.color = color;
    }
}
