
package scotlandyardserver.games.state;

import scotlandyardserver.client.Client;
import scotlandyardserver.games.Game;

public class WaitingPlayersState extends GameState {

    public WaitingPlayersState(Game game) {
        super(game);
    }

    @Override
    public synchronized boolean joinGame(Client client) {        
        if(game().players().contains(client)) {
            client.sendMessage("JOINGAMEREFUSED");
            return false;
        }
        
        game().addPlayer(client);
        client.sendMessage("JOINGAMEACCEPTED");
        
        for(Client cl : game().players())
            if(cl != client)
                cl.sendMessage("PLAYERJOINEDGAME#" + cl.username());
        
        if(game().currentNumberOfPlayers() == game().numberOfPlayers())
            game().setState(new InitializingGameState(game()));
        
        return true;
    }
    
}
