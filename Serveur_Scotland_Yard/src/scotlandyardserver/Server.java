package scotlandyardserver;

import scotlandyardserver.commands.CommandManager;
import scotlandyardserver.database.DataBaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import scotlandyardserver.client.Client;
import scotlandyardserver.client.ClientsManager;
import scotlandyardserver.games.GamesManager;
import scotlandyardserver.json.MapNames;

import com.google.gson.*;
import scotlandyardserver.games.Game;
import scotlandyardserver.json.GameList;

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

    public void editAccount(Client client, String newUsername, String newPassword) {
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

    public void requestMapNames(Client client) {
        try {
            ResultSet rs = getSQLSelection("SELECT name FROM map");
            MapNames names = new MapNames();
            
            while(rs.next())
                names.add(rs.getString("name"));
            
            client.sendMessage(new Gson().toJson(names));
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void requestGameList(Client client) {
        GameList gameList = new GameList();
        
        for(Game game : gamesManager.games()) {
            gameList.add(new scotlandyardserver.json.Game(
                    game.getName(),
                    game.getHost(),
                    game.getMap(),
                    game.currentNumberOfPlayers(),
                    game.numberOfPlayers()                    
            ));
        }
        
        client.sendMessage(new Gson().toJson(gameList));
    }
}
