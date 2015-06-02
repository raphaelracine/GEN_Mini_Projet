package scotlandyardclient.json;

import java.awt.Color;

public class MisterXData extends PlayerData {
    private final int doubleTurn;
    private final int black;
    
    public MisterXData(String playerName, int taxi, int bus, int subway, int station, Color color, int doubleTurn, int black) {
        super(playerName, taxi, bus, subway, station, color);
        this.doubleTurn = doubleTurn;
        this.black = black;
    }
    
    public int getDoubleTurn() {
        return doubleTurn;
    }
    
    public int getBlack() {
        return black;
    }
}
