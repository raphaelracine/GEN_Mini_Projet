/**
 * Cette classe représentant l'état d'une partie en attente de joueurs (State
 * Pattern)
 *
 * Il est à noter que toutes ces méthodes sont décrites dans la classe Game mais
 * elle sont appelées par délégation
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardserver.games.state;

import scotlandyardserver.client.Client;
import scotlandyardserver.games.Game;

public class WaitingPlayersState extends GameState {

    /**
     * Constructeur
     *
     * @param game La partie concernée
     */
    public WaitingPlayersState(Game game) {
        super(game);
    }

    @Override
    public synchronized boolean joinGame(Client client) {
        if (game().players().contains(client)) {
            return false;
        }

        game().addPlayer(client);
        game().getHost().sendMessage("PLAYERJOINEDGAME#" + client.username());

        if (game().currentNumberOfPlayers() == game().numberOfPlayers()) {
            game().getHost().sendMessage("GAMEREADY");
        }

        return true;
    }

    @Override
    public void leaveGame(Client client) {
        if (client == game().getHost()) {
            client.server().getGamesManager().finishGame(game());
            for (Client c : game().players()) {
                c.sendMessage("HOSTLEFTGAME");
            }
            game().removePlayer(game().getHost());
        } else {
            client.sendMessage("PLAYERLEFTGAME#" + client.username());
            if (game().players().contains(game().getHost())) {
                game().getHost().sendMessage("PLAYERLEFTGAME#" + client.username());
            }
            game().removePlayer(client);
        }
    }
}
