/**
 * Cette classe représente le serveur (il s'agit d'un singleton).
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
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
import scotlandyardserver.json.PlayerList;
import scotlandyardserver.games.Game;
import scotlandyardserver.json.GameList;

public class Server {

    private static Server instance;
    private final int portNumber;

    private final ClientsManager clientsManager;
    private final GamesManager gamesManager;

    private final CommandManager commandManager;

    private final DataBaseConnection databaseConnection;

    /**
     * Constructeur (privé car singleton)
     *
     * @param portNumber Numéro du port d'écoute
     */
    private Server(int portNumber) {
        this.portNumber = portNumber;
        clientsManager = new ClientsManager(this);
        commandManager = new CommandManager(this);
        databaseConnection = new DataBaseConnection();
        gamesManager = new GamesManager();
    }

    /**
     * Permet d'obtenir une instance du serveur
     *
     * @param portNumber Numéro de port d'écoute
     * @return L'instance du singleton
     */
    public static Server getInstance(int portNumber) {
        if (instance == null) {
            instance = new Server(portNumber);

        }
        return instance;

    }

    /**
     * Permet d'obtenir le manager des clients
     *
     * @return Manager des clients
     */
    public ClientsManager getClientsManager() {
        return clientsManager;

    }

    /**
     * Permet d'obtenir le manager des parties
     *
     * @return Manager des parties
     */
    public GamesManager getGamesManager() {
        return gamesManager;
    }

    /**
     * Permet d'obtenir le numéro de port d'écoute du serveur
     *
     * @return Port d'écoute du serveur
     */
    public int getPort() {
        return portNumber;

    }

    /**
     * Permet de traiter la réception d'une commande d'un client
     *
     * @param command Commande reçue
     * @param client Client qui a envoyé la commance
     */
    public void interpreteReceivedCommand(String command, Client client) {
        commandManager.executeCommand(command, client);
    }

    /**
     * Permet d'obtenir un ResultSet en faisant une requête SQL sur la base de
     * données
     *
     * @param sql Requête SQL
     * @return ResultSet contenant le résultat
     * @throws SQLException
     */
    public ResultSet getSQLSelection(String sql) throws SQLException {
        return databaseConnection.getSQLSelection(sql);

    }

    /**
     * Permet d'éxécuter une requete SQL (create update ou delete)
     *
     * @param CRUDrequest Requête à exécuter
     * @return Retourne true si ça c'est bien passé, false sinon
     * @throws SQLException
     */
    public boolean executeCRUDrequest(String CRUDrequest) throws SQLException {
        return databaseConnection.executeCRUDrequest(CRUDrequest);

    }

    public void playDetectiveTurn(Client client, int stationNumber, String transportUsed, int gameId) {
    }

    public void playMisterXTurn(Client client, int stationNumber, String transportUsed, int gameId, boolean doubleTicket) {
    }

    public void launchGame(Client client, int numberOfPlayer, String map) {
    }

    /**
     * Permet de créer à un client de créer un compte utilisateur
     *
     * @param client Le client qui veut créer le compte utilisateur
     * @param username Le nom d'utilisateur
     * @param password Le mot de passe
     */
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

    /**
     * Permet d'éditer le compte d'un client
     *
     * @param client Le client qui veut éditer son compte
     * @param newUsername Le nouveau nom d'utilisateur
     * @param newPassword Le nouveau mot de passe
     */
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

    /**
     * Permet à un client de demander la liste des noms des cartes
     *
     * @param client Le client qui demande la liste des cartes
     */
    public void requestMapNames(Client client) {
        try {
            ResultSet rs = getSQLSelection("SELECT name FROM map");
            MapNames names = new MapNames();

            while (rs.next()) {
                names.add(rs.getString("name"));
            }

            client.sendMessage(new Gson().toJson(names));
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Permet à un client de demander la liste des parties
     *
     * @param client Le client qui demande la liste des parties
     */
    public void requestGameList(Client client) {
        GameList gameList = new GameList();

        for (Game game : gamesManager.games()) {
            gameList.add(new scotlandyardserver.json.Game(
                    game.getName(),
                    game.getHost().username(),
                    game.getMap(),
                    game.currentNumberOfPlayers(),
                    game.numberOfPlayers()
            ));
        }

        client.sendMessage(new Gson().toJson(gameList));
    }

    /**
     * Permet à un client de demander la liste des joueurs dans une partie
     *
     * @param client Le client qui demande la liste des cartes
     * @param game Le nom de la partie
     */
    public void requestPlayerList(Client client, String game) {
        for (Game g : gamesManager.games()) {
            if (g.getName().equals(game)) {
                PlayerList playerList = new PlayerList();
                for (Client cl : g.players()) {
                    playerList.add(cl.username());
                }
                client.sendMessage(new Gson().toJson(playerList));
                return;
            }
        }

        client.sendMessage(new Gson().toJson(new PlayerList()));
    }
}
