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
public class PlayingGameState extends GameState {

    // ICI IL Y AURA LA GESTION DES TOURS !!!    
    public PlayingGameState(Game game) {
        super(game);
    }

    @Override
    public boolean joinGame(Client client) {
        /* TODO */
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void leaveGame(Client client) {
        /* TODO */
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
