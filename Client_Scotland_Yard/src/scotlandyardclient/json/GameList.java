package scotlandyardclient.json;

import java.util.ArrayList;

public class GameList {
    private final ArrayList<Game> games = new ArrayList<Game>();
    
    public ArrayList<Game> games() {
        return games;
    }
}