/**
 * Cette classe permet de transmettre une carte en JSon
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

import java.util.LinkedList;

public class GameMap {

    private final LinkedList<Station> stations = new LinkedList<>();
    private final LinkedList<Link> links = new LinkedList<>();

    public GameMap() {
    }

    public void addStation(Station station) {
        stations.add(station);
    }

    public void addLink(Link link) {
        links.add(link);
    }

    public Station getStation(int id) {
        for (Station s : stations) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    public LinkedList<Station> getStations() {
        return stations;
    }
}
