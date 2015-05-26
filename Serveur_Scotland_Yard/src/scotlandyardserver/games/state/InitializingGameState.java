/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scotlandyardserver.games.state;

import scotlandyardserver.json.Station;
import scotlandyardserver.json.GameMap;
import com.google.gson.Gson;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import scotlandyardserver.Server;
import scotlandyardserver.client.Client;
import scotlandyardserver.games.Game;
import scotlandyardserver.games.MisterXPone;

/**
 *
 * @author Raphaël Racine
 */
public class InitializingGameState extends GameState {
    
    public InitializingGameState(Game game) {
        super(game);
        initGame();
    }

    @Override
    public boolean joinGame(Client client) {
        return false;
    }

    @Override
    public void leaveGame(Client client) {} 
    
    /**
     * Cette méthode initialise la partie et envoi à tout les joueurs
     * les informations de départ de la partie (carte, tickets, stations de départ)
     */
    private void initGame() {
        // Récupération des informations de la carte dans la base de données
        Server s = Server.getInstance(0);
        
        try {            
            ResultSet rs = s.getSQLSelection(
                    "SELECT * FROM station WHERE map_fk='" + game().getMap() + "'"
            );
            
            GameMap gameMap = new GameMap();
            
            while(rs.next())
                gameMap.addStation(new Station(rs.getInt("number"), rs.getString("type")));
            
            /** VOIR POUR LES LIENS ENTRE LES STATIONS **/
            /****************/
            
            /** VOIR POUR L'ENVOI DE L'iMAGE */
            
            /** Envoi des informations de la carte en JSon */
            for(Client c : game().players()) {
                c.sendMessage(new Gson().toJson(gameMap));
                
                if(c == game().getHost())
                    /*game().setMisterXPone(new MisterXPone);*/;
                else
                    /*game().addDetectivePone(new DetectivePone())*/;
                
                /** Envoi des informations des pions **/
                c.sendMessage(new Gson().toJson(game().getDetectives()));
                c.sendMessage(new Gson().toJson(game().getMisterX()));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(InitializingGameState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
