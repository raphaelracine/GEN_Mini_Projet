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
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import scotlandyardserver.Server;
import scotlandyardserver.client.Client;
import scotlandyardserver.games.DetectivePone;
import scotlandyardserver.games.Game;
import scotlandyardserver.games.MisterXPone;
import scotlandyardserver.json.GameData;
import scotlandyardserver.json.InfoTickets;
import scotlandyardserver.json.InfoTicketsList;
import scotlandyardserver.json.InfoTicketsMisterX;
import scotlandyardserver.json.Link;

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
            ResultSet rs = s.getSQLSelection("SELECT picture FROM map WHERE name='" + game().getMap() + "'");
            
            rs.next();
            String filePicture = rs.getString("picture");            
            
            GameMap gameMap = new GameMap();
            
            /** Récupération des stations de la carte **/
            rs = s.getSQLSelection(
                    "SELECT * FROM station WHERE map_fk='" + game().getMap() + "'"
            );
            
            while(rs.next())
                gameMap.addStation(new Station(rs.getInt("station_id"), 
                        rs.getInt("number"), 
                        rs.getString("type"),
                        rs.getInt("x"),
                        rs.getInt("y")
                ));
            
            rs = s.getSQLSelection("SELECT * FROM link WHERE first_station_fk IN"
                    + "(SELECT station_id FROM station WHERE map_fk='" + game().getMap() + "')");
            
            HashMap<Pair<Station, Station>, Link> map = new HashMap<>();
            
            while(rs.next()) {
                Station s1 = gameMap.getStation(rs.getInt("first_station_fk"));
                Station s2 = gameMap.getStation(rs.getInt("second_station_fk"));
                
                if(s1.getNumero() > s2.getNumero()) {
                    Station tmp = s1;
                    s1 = s2;
                    s2 = tmp;
                }     
                
                Pair<Station, Station> pair = new Pair(s1, s2);
                
                if (!map.containsKey(pair)) {
                    Link link = new Link(s1, s2);
                    map.put(pair, link);
                    gameMap.addLink(link);
                }
                map.get(pair).addLocomotion(rs.getString("type"));      
            }
            
            InfoTicketsList ticketsList = new InfoTicketsList();
            InfoTicketsMisterX ticketsMisterX = null;
            for(Client c : game().players()) {
                if(c == game().getHost()) {
                    game().setMisterXPone(new MisterXPone(c, null, 4, 3, 3, game().numberOfPlayers() - 1, 2));  
                    ticketsMisterX = new InfoTicketsMisterX(c.username(), 4, 3, 3, game().numberOfPlayers() - 1, 2);
                } else {
                    game().addDetectivePone(new DetectivePone(c, null, 10, 8, 4));
                    ticketsList.add(new InfoTickets(c.username(), 10, 8, 4));
                }
            }

            // Envoi des données de jeu
            for (Client c : game().players()) {
                // Envoi de l'image
                try {
                    c.sendImageFile(filePicture);
                } catch (IOException ex) {
                    Logger.getLogger(InitializingGameState.class.getName()).log(Level.SEVERE, null, ex);
                }
                // Envoi des tickets et des données de la carte
                c.sendMessage(new Gson().toJson(new GameData(gameMap, ticketsList, ticketsMisterX)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(InitializingGameState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
