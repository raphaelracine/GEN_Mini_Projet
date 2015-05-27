package scotlandyardclient.json;

public class Station {
    private final int numero;
    private final String type;
    private final int id;
    private final int x;
    private final int y;
    
    public Station(int id, int numero, String type, int x, int y) {
        this.numero = numero;
        this.type = type;
        this.id = id;
        this.y = y;
        this.x = x;
    }
    
    public int getId() {
        return id;
    }
    
    public int getNumero() {
        return numero;
    }
    
    public String getType() {
        return type;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
}
