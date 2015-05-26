
package scotlandyardserver.json;

import java.util.LinkedList;

public class GameMap {
    private final LinkedList<Station> stations = new LinkedList<Station>();
    
    /** VOIR COMMENT FAIRE POUR LES LIENS ENTRE LES STATIONS **/
    
    public void addStation(Station s) {
        stations.add(s);
    }
    
    public LinkedList<Station> getStations() {
        return stations;
    }
}
