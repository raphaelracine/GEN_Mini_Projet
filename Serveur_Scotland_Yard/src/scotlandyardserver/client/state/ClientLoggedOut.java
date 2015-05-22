package scotlandyardserver.client.state;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import scotlandyardserver.Server;
import scotlandyardserver.client.Client;

public class ClientLoggedOut extends ClientState {

    public ClientLoggedOut(Client client) {
        super(client);
    }

    @Override
    public void logOut() {
        // On ne fait rien, le client est déjà déconnecté
    }

    @Override
    public String username() {
        return null;
    }

    @Override
    public void logIn(String username, String password) {
        try {
            /**
             * On vérifie qu'il n'y a pas un autre client déjà connecté avec le
             * même nom d'utilisateur
             */
            for (Client c : client().server().getClientsManager().listClients()) {
                if (c.username() != null && c.username().equals(username)) {
                    client().sendMessage("AUTHENTICATEREFUSED");
                    return;
                }
            }

            ResultSet rs = client().server().getSQLSelection(
                    "SELECT * FROM user WHERE username='" + username
                    + "' AND password='" + password + "' AND blocked=false");

            if (rs.next()) {
                client().sendMessage("AUTHENTICATEACCEPTED");
                client().setState(new ClientLoggedIn(client(), username));
            } else {
                client().sendMessage("AUTHENTICATEREFUSED");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setUsername(String newUsername) {
    }
    
    @Override
    public void leaveGame() {
        
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
