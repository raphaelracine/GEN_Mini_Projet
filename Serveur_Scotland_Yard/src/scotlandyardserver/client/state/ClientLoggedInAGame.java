package scotlandyardserver.client.state;

import scotlandyardserver.client.Client;

public class ClientLoggedInAGame extends ClientLoggedIn {

    public ClientLoggedInAGame(Client client, String username) {
        super(client, username);
    }
    
    @Override
    public void createGame(String name, int numberOfPlayers, String map) {
        // Un client ne peut pas créer de jeu s'il est déjà dans un jeu
    } 
    
    @Override
    public void joinGame(String name) {
        // Un client qui se trouve dans une partie ne peut pas la rejoindre
    }
}
