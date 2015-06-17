/**
 * Cette classe permet de transmettre les données de mister X de départ en JSon
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

public class MisterXData extends PlayerData {

    private final int doubleTurn;
    private final int black;

    public MisterXData(String playerName, int taxi, int bus, int subway, int station, Color color, int doubleTurn, int black) {
        super(playerName, taxi, bus, subway, station, color);
        this.doubleTurn = doubleTurn;
        this.black = black;
    }
}
