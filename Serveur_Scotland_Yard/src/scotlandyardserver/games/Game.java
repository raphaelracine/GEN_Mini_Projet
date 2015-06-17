/**
 * Cette classe représente une partie
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardserver.games;

import java.util.LinkedList;
import scotlandyardserver.client.Client;
import scotlandyardserver.client.state.ClientLoggedIn;
import scotlandyardserver.client.state.ClientLoggedInAGame;
import scotlandyardserver.games.state.GameState;
import scotlandyardserver.games.state.WaitingPlayersState;

public class Game {

    private GameState state;
    private final Client host;
    private final String name;
    private final int numberOfPlayers;
    private final String map;
    private final LinkedList<Client> players = new LinkedList<>();

    /**
     * Pions des joueurs
     */
    private final LinkedList<DetectivePone> detectives = new LinkedList<DetectivePone>();
    private MisterXPone misterX;

    /**
     * Constructeur
     *
     * @param host L'hôte de la partie
     * @param name Le nom de la partie
     * @param numberOfPlayers Le nombre de joueurs
     * @param map Le nom de la carte
     */
    public Game(Client host, String name, int numberOfPlayers, String map) {
        this.host = host;
        addPlayer(host);
        this.name = name;
        this.numberOfPlayers = numberOfPlayers;
        this.map = map;

        setState(new WaitingPlayersState(this));
    }

    /**
     * Retourne le nom de la partie
     *
     * @return Le nom de la partie
     */
    public String getName() {
        return name;
    }

    /**
     * Retourne le client hôte qui a créer la partie
     *
     * @return L'hôte qui a créer la partie
     */
    public Client getHost() {
        return host;
    }

    /**
     * Retourne le nom de la carte de la partie
     *
     * @return Le nom de la carte de la partie
     */
    public String getMap() {
        return map;
    }

    /**
     * Retourne le nombre maximum de joueurs dans la partie
     *
     * @return Nombre maximum de joueurs dans la partie
     */
    public int numberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Retourne la liste des joueurs de la partie
     *
     * @return Liste des joueurs de la partie
     */
    public LinkedList<Client> players() {
        return players;
    }

    /**
     * Retourne le nombre courant de joueurs dans la partie
     *
     * @return Le nombre courant de joueurs dans la partie
     */
    public int currentNumberOfPlayers() {
        return players.size();
    }

    /**
     * Permet à un client de rejoindre cette partie
     *
     * @param client Le client qui rejoint la partie
     * @return Retourne true si le client a pu rejoindre la partie, false sinon
     */
    public boolean joinGame(Client client) {
        return state.joinGame(client);
    }

    /**
     * Permet à un joueur de quietter la partie
     *
     * @param client Le client qui quitte la partie
     */
    public void leaveGame(Client client) {
        state.leaveGame(client);
    }

    /**
     * Permet de changer l'état de la partie
     *
     * @param newState Le nouvel état de la partie
     */
    public void setState(GameState newState) {
        this.state = newState;
    }

    /**
     * Permet d'ajouter un joueur dans la partie
     *
     * @param client Le client à ajouter dans la partie
     */
    public void addPlayer(Client client) {
        players.add(client);
        client.setState(new ClientLoggedInAGame(client, client.username(), this));
    }

    /**
     * Permet de supprimer un client de la partie
     *
     * @param client Supprime le client de la partie
     */
    public void removePlayer(Client client) {
        players.remove(client);
        client.setState(new ClientLoggedIn(client, client.username()));
    }

    /**
     * Permet d'initialiselr le pion de Mister X
     *
     * @param pone Le pion de Mister X
     */
    public void setMisterXPone(MisterXPone pone) {
        misterX = pone;
    }

    /**
     * Permet d'ajoer un pion de détective dans la partie
     *
     * @param pone Le pion du détective
     */
    public void addDetectivePone(DetectivePone pone) {
        detectives.add(pone);
    }

    /**
     * Permet d'obtenir l'ensemble des pions des détectives
     *
     * @return L'ensemble des pions des détectives
     */
    public LinkedList<DetectivePone> getDetectives() {
        return detectives;
    }

    /**
     * Permet d'obtenir le pion de Mister X
     *
     * @return Le pion de Mister X
     */
    public MisterXPone getMisterX() {
        return misterX;
    }
}
