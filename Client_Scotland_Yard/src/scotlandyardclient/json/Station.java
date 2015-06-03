package scotlandyardclient.json;

import java.util.LinkedList;
import scotlandyardclient.pone.Pone;

public class Station {
    private final int numero;
    private final String type;
    private final int id;
    private final int x;
    private final int y;
    
    private Pone pone; // Pion qui se trouve sur la station, s'il y en a un
    
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
    
    public void setPone(Pone p) {
        this.pone = p;
    }
    
    public Pone getPone() {
        return pone;
    }
}
