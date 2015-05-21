
package scotlandyardserver.games.state;

import scotlandyardserver.client.Client;
import scotlandyardserver.client.state.ClientLoggedIn;
import scotlandyardserver.client.state.ClientLoggedInAGame;
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
        client.setState(new ClientLoggedInAGame(client, client.username()));
        
        if(game().currentNumberOfPlayers() == game().numberOfPlayers())
            game().getHost().sendMessage("GAMEREADY");
        
        return true;
    }
    
    @Override
    public void leaveGame(Client client) {
        if (client == game().getHost()) {
            client.server().getGamesManager().finishGame(game());
            for (Client c : game().players()) {                
                c.sendMessage("HOSTLEFTGAME");
                game().removePlayer(client);                
            }
        } else {            
            client.sendMessage("PLAYERLEFTGAME#" + client.username());
            game().getHost().sendMessage("PLAYERLEFTGAME#" + client.username());
            game().removePlayer(client);
        }
    }    
}
