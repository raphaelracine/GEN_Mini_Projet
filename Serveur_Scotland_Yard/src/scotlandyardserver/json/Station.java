package scotlandyardserver.json;

public class Station {
    private final int numero;
    private final String type;
    private final int id;
    
    public Station(int id, int numero, String type) {
        this.numero = numero;
        this.type = type;
        this.id = id;
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
}
