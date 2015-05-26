package scotlandyardclient.json;

import java.util.LinkedList;

class Link {
    private final Station first;
    private final Station second;
    private final LinkedList<String> locomotions;
    
    public Link(Station first, Station second, LinkedList<String> locomotions) {
        this.first = first;
        this.second = second;
        this.locomotions = locomotions;
    }
    
    public Link(Station first, Station second) {
        this(first, second, new LinkedList<String>());
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
}
