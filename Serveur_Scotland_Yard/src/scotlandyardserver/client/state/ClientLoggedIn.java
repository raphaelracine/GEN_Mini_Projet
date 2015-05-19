package scotlandyardserver.client.state;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import scotlandyardserver.client.Client;

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
        // On ne fait rien, le client est déjà authentifié
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
            client().setState(new ClientLoggedInAGame(client(), username));
        }
        else
            client().sendMessage("CREATEGAMEREFUSED");
    }

    @Override
    public void joinGame(String name) {
        // Code pour rejoindre une partie à faire ici
    }

}
