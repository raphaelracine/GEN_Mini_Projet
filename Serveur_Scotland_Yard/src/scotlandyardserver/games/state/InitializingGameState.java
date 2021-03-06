/**
 * Cette classe représentant l'état d'une partie en cours d'initialisation (State Pattern)
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

import scotlandyardserver.json.Station;
import scotlandyardserver.json.GameMap;
import com.google.gson.Gson;
import java.awt.Color;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import scotlandyardserver.Server;
import scotlandyardserver.client.Client;
import scotlandyardserver.games.DetectivePone;
import scotlandyardserver.games.Game;
import scotlandyardserver.games.MisterXPone;
import scotlandyardserver.json.GameData;
import scotlandyardserver.json.PlayerData;
import scotlandyardserver.json.PlayerDataList;
import scotlandyardserver.json.MisterXData;
import scotlandyardserver.json.Link;

/**
 *
 * @author Raphaël Racine
 */
public class InitializingGameState extends GameState {
    
    private static final Random random = new Random();
    
    /**
     * Constructeur
     * @param game La partie concernée 
     */
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
            
            PlayerDataList playerDataList = new PlayerDataList();
            MisterXData misterXData = null;
            
            LinkedList<Station> stations = (LinkedList<Station>)gameMap.getStations().clone();
            Color[] colors = {Color.BLACK, Color.CYAN, Color.GREEN, Color.YELLOW, Color.RED};
            int counterColor = 0;
                    
            for(Client c : game().players()) {
                int posStation = random.nextInt(stations.size());
                int idStation = stations.get(posStation).getId();
                
                if(c == game().getHost()) {
                    // transmettre au constructeur de MisterXPone la station de départ peut s'avérer inutile
                    // ne pas hésiter à supprimer si l'attribut n'est pas utilisé par la suite.
                    game().setMisterXPone(new MisterXPone(c, stations.get(posStation), 4, 3, 3, game().numberOfPlayers() - 1, 2));  
                    misterXData = new MisterXData(c.username(), 4, 3, 3, idStation, Color.MAGENTA, 2, game().numberOfPlayers() - 1);
                } else {
                    // transmettre au constructeur de MisterXPone la station de départ peut s'avérer inutile
                    // ne pas hésiter à supprimer si l'attribut n'est pas utilisé par la suite.
                    game().addDetectivePone(new DetectivePone(c, stations.get(posStation), 10, 8, 4));
                    playerDataList.add(new PlayerData(c.username(), 10, 8, 4, idStation, colors[counterColor]));
                }
                counterColor++;
                stations.remove(posStation);
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
                c.sendMessage(new Gson().toJson(new GameData(gameMap, playerDataList, misterXData)));
            }
            
            // On passe à l'état de jeu
            game().setState(new PlayingGameState(game()));
        } catch (SQLException ex) {
            Logger.getLogger(InitializingGameState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
