/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scotlandyardclient.clientstate;

import java.net.Socket;
import scotlandyardclient.Client;

/**
 *
 * @author Raphaël Racine
 */
public class LoggedInGameState extends LoggedInState {

    public LoggedInGameState(Socket socket, String username) {
        super(socket, username);
    }
    
    @Override
    public boolean joinGame(String name) {
        return false;
    }
    
    @Override
    public void leaveGame(String name) {
        Client.getInstance().sendCommand("PLAYERLEAVEGAME#" + name);
        Client.getInstance().setState(new LoggedInState(getSocket(), username()));
    }
    
    @Override
    public boolean createGame(String name, int nbPlayers, String map) {
        return false;
    }
    
}
