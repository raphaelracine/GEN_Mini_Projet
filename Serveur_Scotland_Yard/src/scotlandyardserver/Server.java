package scotlandyardserver;

import java.sql.ResultSet;
import java.sql.SQLException;
import scotlandyardserver.client.Client;
import scotlandyardserver.client.ClientsManager;
import scotlandyardserver.games.GamesManager;

public class Server {

    private static Server instance;
    private final int portNumber;

    private final ClientsManager clientsManager;
    private final GamesManager gamesManager;

    private final CommandManager commandManager;

    private final DataBaseConnection databaseConnection;

    private Server(int portNumber) {
        this.portNumber = portNumber;
        clientsManager = new ClientsManager(this);
        commandManager = new CommandManager(this);
        databaseConnection = new DataBaseConnection();
        gamesManager = new GamesManager();
    }

    public static Server getInstance(int portNumber) {
        if (instance == null) {
            instance = new Server(portNumber);

        }
        return instance;

    }

    public ClientsManager getClientsManager() {
        return clientsManager;

    }
    
    public GamesManager getGamesManager() {
        return gamesManager;
    }

    public int getPort() {
        return portNumber;

    }

    public void interpreteReceivedCommand(String command, Client client) {
        commandManager.executeCommand(command, client);

    }

    public ResultSet getSQLSelection(String sql) throws SQLException {
        return databaseConnection.getSQLSelection(sql);

    }

    public boolean executeCRUDrequest(String CRUDrequest) throws SQLException {
        return databaseConnection.executeCRUDrequest(CRUDrequest);

    }

    /** METHODES A DEPLACER AILLEUR AU MOMENT VENU **/
    public void playDetectiveTurn(Client client, int stationNumber, String transportUsed, int gameId) {
    }

    public void playMisterXTurn(Client client, int stationNumber, String transportUsed, int gameId, boolean doubleTicket) {
    }

    public void launchGame(Client client, int numberOfPlayer, String map) {
    }
    
    /**/

    public void createAccount(Client client, String username, String password) {
        try {
            executeCRUDrequest(
                    "INSERT INTO user(username, password) "
                    + "VALUES('" + username + "','"
                    + password + "')");
            client.sendMessage("CREATEACCOUNTACCEPTED");
        } catch (SQLException ex) {
            client.sendMessage("CREATEACCOUNTREFUSED");
        }
    }

    void editAccount(Client client, String newUsername, String newPassword) {
        try {
            String request = "UPDATE user SET username='" + newUsername
                    + "', password='" + newPassword + "' WHERE "
                    + "username='" + client.username() + "'";
            executeCRUDrequest(request);
            client.setUsername(newUsername);
            client.sendMessage("EDITACCOUNTACCEPTED");
        } catch (SQLException ex) {
            client.sendMessage("EDITACCOUNTREFUSED");
        }
    }
}
