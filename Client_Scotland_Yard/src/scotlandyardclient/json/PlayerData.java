package scotlandyardclient.json;

import java.awt.Color;

public class PlayerData {
    private final String playerName;
    private final int taxi;
    private final int bus;
    private final int subway;
    private final int station;
    private final Color color;
    
    public PlayerData(String playerName, int taxi, int bus, int subway, int station, Color color) {
        this.taxi = taxi;
        this.bus = bus;
        this.subway = subway;
        this.playerName = playerName;
        this.station = station;
        this.color = color;
    }
    
    public String getPlayerName() {
        return playerName;
    }
    
    public int getTaxi() {
        return taxi;
    }
    
    public int getBus() {
        return bus;
    }
    
    public int getSubway() {
        return subway;
    }
    
    public int getStation() {
        return station;
    }
    
    public Color getColor() {
        return color;
    }
}
