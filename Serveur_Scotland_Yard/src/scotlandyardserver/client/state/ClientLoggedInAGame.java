package scotlandyardserver.client.state;

import scotlandyardserver.client.Client;
import scotlandyardserver.games.Game;

public class ClientLoggedInAGame extends ClientLoggedIn {

    private final Game game;
    
    public ClientLoggedInAGame(Client client, String username, Game game) {
        super(client, username);
        this.game = game;
    }

    @Override
    public void createGame(String name, int numberOfPlayers, String map) {
    }

    @Override
    public void joinGame(String name) {
    }

    public void leaveGame() {
        game.leaveGame(client());
    }
}
