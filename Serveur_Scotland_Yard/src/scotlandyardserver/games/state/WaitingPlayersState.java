
package scotlandyardserver.games.state;

import scotlandyardserver.client.Client;
import scotlandyardserver.client.state.ClientLoggedIn;
import scotlandyardserver.games.Game;

public class WaitingPlayersState extends GameState {

    public WaitingPlayersState(Game game) {
        super(game);
    }

    @Override
    public synchronized boolean joinGame(Client client) {        
        if(game().players().contains(client))
            return false;

        game().addPlayer(client);
        game().getHost().sendMessage("PLAYERJOINEDGAME#" + client.username());
        
        if(game().currentNumberOfPlayers() == game().numberOfPlayers()) {
            game().getHost().sendMessage("GAMEREADY");
            game().setState(new InitializingGameState(game()));
        }
        
        return true;
    }
    
    @Override
    public void leaveGame(Client client) {
        if (client == game().getHost()) {
            client.server().getGamesManager().finishGame(game());
            for (Client c : game().players()) {                
                c.sendMessage("HOSTLEFTGAME");
                c.setState(new ClientLoggedIn(client, client.username()));
            }
            game().players().clear();
        } else {
            game().removePlayer(client);
            client.sendMessage("PLAYERLEFTGAME#" + client.username());
            game().getHost().sendMessage("PLAYERLEFTGAME#" + client.username());
        }
    }    
}
