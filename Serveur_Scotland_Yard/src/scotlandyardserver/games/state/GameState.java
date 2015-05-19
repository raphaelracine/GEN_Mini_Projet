
package scotlandyardserver.games.state;

import scotlandyardserver.client.Client;
import scotlandyardserver.games.Game;

public abstract class GameState {
    
    private final Game game;
    
    public GameState(Game game) {
        this.game = game;
    }
    
    public Game game() {
        return game;
    }
    
    public abstract boolean joinGame(Client client);
}
