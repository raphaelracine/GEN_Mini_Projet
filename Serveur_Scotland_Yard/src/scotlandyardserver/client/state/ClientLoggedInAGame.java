package scotlandyardserver.client.state;

import scotlandyardserver.client.Client;

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
}
