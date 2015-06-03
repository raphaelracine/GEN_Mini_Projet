
package scotlandyardclient.json;

import java.util.LinkedList;

public class GameMap {
    private final LinkedList<Station> stations = new LinkedList<>();
    private final LinkedList<Link> links = new LinkedList<>();
    
    public GameMap() {}
    
    public void addStation(Station station) {
        stations.add(station);
    }
    
    public void addLink(Link link) {
        links.add(link);
    }
    
    public Station getStation(int id) {
        for(Station s : stations)
            if(s.getId() == id)
                return s;
        return null;
    }
    
    public LinkedList<Station> getStations() {
        return stations;
    }
    
    public LinkedList<Link> getLinks() {
        return links;
    }
}
