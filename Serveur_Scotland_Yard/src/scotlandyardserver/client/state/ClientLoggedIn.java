package scotlandyardserver.client.state;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import scotlandyardserver.client.Client;
import scotlandyardserver.games.Game;

public class ClientLoggedIn extends ClientState {

    private String username;

    public ClientLoggedIn(Client client, String username) {
        super(client);
        this.username = username;

        try {
            // On informe la base de données que le client est connecté sur son compte
            client.server().executeCRUDrequest(
                    "UPDATE user SET connected=true WHERE username='" + username + "'");
        } catch (SQLException ex) {
            Logger.getLogger(ClientLoggedIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void logIn(String username, String password) {
        client().sendMessage("AUTHENTICATEREFUSED");
    }

    @Override
    public void logOut() {
        try {
            client().server().executeCRUDrequest(
                    "UPDATE user SET connected=false WHERE username='" + username + "'");
            client().setState(new ClientLoggedOut(client()));
        } catch (SQLException ex) {
            Logger.getLogger(ClientLoggedIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public void setUsername(String newUsername) {
        this.username = newUsername;
    }
    
    @Override
    public void createGame(String name, int numberOfPlayers, String map) {
        if(client().server().getGamesManager().createGame(client(), name, numberOfPlayers, map)) {
            client().sendMessage("CREATEGAMEACCEPTED");
        }
        else
            client().sendMessage("CREATEGAMEREFUSED");
    }
    
    @Override
    public void leaveGame() {
        // Le client ne peut pas quitter un jeu s'il n'est pas dedans
    }
    
    @Override
    public void joinGame(String name) {
        for(Game game : client().server().getGamesManager().games()) {
            if(game.getName().equals(name)) {
                if(game.joinGame(client())) {
                    client().sendMessage("JOINGAMEACCEPTED");                 
                    return;
                }
            }
        }
        
        client().sendMessage("JOINGAMEREFUSED");
    }

}
