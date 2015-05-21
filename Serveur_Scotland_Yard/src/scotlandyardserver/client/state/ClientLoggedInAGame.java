package scotlandyardserver.client.state;

import scotlandyardserver.client.Client;
import scotlandyardserver.games.Game;

public class ClientLoggedInAGame extends ClientLoggedIn {

    public ClientLoggedInAGame(Client client, String username) {
        super(client, username);
    }

    @Override
    public void createGame(String name, int numberOfPlayers, String map) {
        client().sendMessage("CREATEGAMEREFUSED");
    }

    @Override
    public void joinGame(String name) {
        client().sendMessage("JOINGAMEREFUSED");
    }

    @Override
    public void leaveGame(String name) {
        for (Game game : client().server().getGamesManager().games()) {
            if (game.getName().equals(name)) {
                game.leaveGame(client());
                return;
            }
        }
        
        client().setState(new ClientLoggedIn(client(), client().username()));
    }
}
