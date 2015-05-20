
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
        if(game().players().contains(client)) {
            client.sendMessage("JOINGAMEREFUSED");
            return false;
        }
        
        client.sendMessage("JOINGAMEACCEPTED");
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
        // si le client correspond à l'hôte
        // destruction de la partie
        //      message départ hôte aux joueurs
        //      vidage de la liste
        //      suppression de la partie
        // si le client est un simple joueur
        //      suppression du client de la liste
        //      message à l'hôte signalant le départ du client.
        if (client == game().getHost()) {
            game().removePlayer(client);
            for (Client c : game().players()) {
                
                c.sendMessage("HOSTLEFTGAME");
                c.setState(new ClientLoggedIn(client, client.username()));
            }
            game().players().clear();
        } else {
            game().removePlayer(client);
            
            game().getHost().sendMessage("PLAYERLEFTGAME#" + client.username());
            client.setState(new ClientLoggedIn(client, client.username()));
        }
    }    
}
