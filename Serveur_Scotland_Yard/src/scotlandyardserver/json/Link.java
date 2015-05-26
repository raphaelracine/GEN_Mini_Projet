package scotlandyardserver.json;

class Link {
    private final String type;
    private final Station first;
    private final Station second;
    
    public Link(String type, Station first, Station second) {
        this.type = type;
        this.first = first;
        this.second = second;
    }
}
