package scotlandyardclient.json;

public class Station {
    private final int numero;
    private final String type;
    
    public Station(int numero, String type) {
        this.numero = numero;
        this.type = type;
    }
    
    public int getNumero() {
        return numero;
    }
    
    public String getType() {
        return type;
    }
}
