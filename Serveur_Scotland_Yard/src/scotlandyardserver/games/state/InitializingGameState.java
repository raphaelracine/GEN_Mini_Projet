/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scotlandyardserver.games.state;

import scotlandyardserver.client.Client;
import scotlandyardserver.games.Game;

/**
 *
 * @author Raphaël Racine
 */
public class InitializingGameState extends GameState {

    public InitializingGameState(Game game) {
        super(game);
    }

    @Override
    public boolean joinGame(Client client) {
        return false;
    }

    @Override
    public void leaveGame(Client client) {}    
}
