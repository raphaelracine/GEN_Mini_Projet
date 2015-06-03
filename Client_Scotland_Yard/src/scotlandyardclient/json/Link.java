package scotlandyardclient.json;

import java.util.LinkedList;

public class Link {
    private final Station first;
    private final Station second;
    private final LinkedList<String> locomotions = new LinkedList<>();
    
    public Link(Station first, Station second) {
        this.first = first;
        this.second = second;
    }
    
    public void addLocomotion(String locomotion) {
        locomotions.add(locomotion);
    }
    
    public LinkedList<String> getLocomotions() {
        return locomotions;
    }
    
    public Station getFirst() {
        return first;
    }
    
    public Station getSecond() {
        return second;
    }
    
    public boolean connectStations(Station s1, Station s2) {
        return (s1 == first && s2 == second) || (s1 == second && s2 == first);
    }
}
