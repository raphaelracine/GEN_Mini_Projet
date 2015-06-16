/**
 * Cette classe représente le client (il s'agit d'un singleton).
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardclient;

import scotlandyardclient.clientstate.ClientState;
import scotlandyardclient.clientstate.DisconnectedState;
import scotlandyardclient.json.*;
import scotlandyardclient.pone.Pone;

public class Client {

    private static Client client; // L'instance du singleton
    private ClientState state; // L'état du client

    /**
     * Constructeur (privé car Design Pattern singleton...)
     */
    private Client() {
        state = new DisconnectedState();
    }

    /**
     * Permet de récupérer l'instance du singleton
     *
     * @return L'instance du client
     */
    public static Client getInstance() {
        if (client == null) {
            client = new Client();
        }
        return client;
    }

    /**
     * Permet de changer l'état du client
     *
     * @param state Le nouvel état du client
     */
    public void setState(ClientState state) {
        this.state = state;
    }

    /**
     * Permet au client de se connecter à un serveur de jeu
     *
     * @param ipAddress L'adresse IP du serveur
     * @param port Le numéro de port à utiliser
     */
    public void connect(String ipAddress, int port) {
        state.connect(ipAddress, port);
    }

    /**
     * Permet au client de se déconneter du serveur
     */
    public void disconnect() {
        state.disconnect();
    }

    /**
     * Permet de savoir si le client est connecté ou non
     *
     * @return
     */
    public boolean isConnected() {
        return state.isConnected();
    }

    /**
     * Permet au client de créer un compte sur le serveur
     *
     * @param username Le nom d'utilisateur qu'il veut pour son compte
     * @param password Le mot de passe
     * @return Retourne true si la création du compte a été effectuée, false
     * sinon
     */
    public boolean createAccount(String username, String password) {
        return state.createAccount(username, password);
    }

    /**
     * Permet au client d'éditer son compte (auquel il est connecté)
     *
     * @param newUsername Le nouveau nom d'utilisateur
     * @param newPassword Le nouveau mot de passe
     * @return Retourne true si le compte a été édité, false sinon
     */
    public boolean editAccount(String newUsername, String newPassword) {
        return state.editAccount(newUsername, newPassword);
    }

    /**
     * Permet au client de rejoindre une partie
     *
     * @param gameName Le nom de la partie qu'il veut rejoindre
     * @return Retourne true si le serveur a accepté que le client rejoigne la
     * partie, false sinon
     */
    public boolean joinGame(String gameName) {
        return state.joinGame(gameName);
    }

    /**
     * Permet à un client de créer une partie
     *
     * @param partyName Le nom de la partie
     * @param playersNb Le nombre de joueurs qu'il veut
     * @param map Le nom de la carte sur laquelle jouer la partie
     * @return Retourne true si la partie a été créee, false sinon
     */
    public boolean createGame(String partyName, int playersNb, String map) {
        return state.createGame(partyName, playersNb, map);
    }

    /**
     * Permet au client de se connecter avec un compte utilisateur sur le
     * serveur
     *
     * @param username Le nom d'utilisateur
     * @param password Le mot de passe
     * @return Retourne true si le client s'est authentifié correctement, false
     * sinon
     */
    public boolean logIn(String username, String password) {
        return state.logIn(username, password);
    }

    /**
     * Permet au client de se déconnecter de son compte utilisateur
     */
    public void logOut() {
        state.logOut();
    }

    /**
     * Permet au client de demander de recevoir la prochaine commande (auquel il
     * s'attend)
     *
     * @return La commande qu'il a reçue sous forme d'une chaîne de caractères
     */
    public String receiveCommand() {
        return state.receiveCommand();
    }

    /**
     * Permet au client de demander de recevoir une image (à laquelle il s'y
     * attend)
     *
     * @return Un tableau de bytes représentant l'image qu'il a reçu
     */
    public byte[] receiveImage() {
        return state.receiveImage();
    }

    /**
     * Permet au client d'envoyer une commande au serveur sur lequel il est
     * connecté
     *
     * @param command La commande à envoyer au serveur
     */
    public void sendCommand(String command) {
        state.sendCommand(command);
    }

    /**
     * Permet au client d'obtenir la liste des noms des cartes du jeu (stockées
     * sur le serveur)
     *
     * @return La liste des cartes du jeu
     */
    public MapNames getMapNames() {
        return state.getMapNames();
    }

    /**
     * Permet au client d'obtenir la liste des parties en cours (stockées sur le
     * serveur)
     *
     * @return La liste des parties en cours
     */
    public GameList getGameList() {
        return state.getGameList();
    }

    /**
     * Permet au client d'obtenir la liste des joueurs qui se trouvent dans une
     * partie
     *
     * @param game Le nom de la partie
     * @return La liste des joueurs qui sont dans une partie
     */
    public PlayerList getPlayerList(String game) {
        return state.getPlayerList(game);
    }

    /**
     * Permet d'obtenir le username du client lorsque celui-ci est connecté avec
     * son compte
     *
     * @return Le nom d'utilisateur du client. Null s'il n'est pas connecté.
     */
    public String username() {
        return state.username();
    }

    /**
     * Permet au client de quitter la partie dans laquelle il se trouve
     * actuellement
     */
    public void leaveGame() {
        state.leaveGame();
    }

    /**
     * Permet d'obtenir le pion que client est en train de manipuler dans une
     * partie
     *
     * @return Le pion manipulé par le client. Null si il ne joue pas.
     */
    public Pone getPone() {
        return state.getPone();
    }

    /**
     * Permet d'affecter un pion au client (lors d'une partie)
     *
     * @param p Le pion qu'on lui affecte
     */
    public void setPone(Pone p) {
        state.setPone(p);
    }
}
