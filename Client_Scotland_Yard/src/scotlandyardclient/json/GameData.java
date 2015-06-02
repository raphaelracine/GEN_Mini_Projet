/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scotlandyardclient.json;

/**
 *
 * @author Yassin
 */
public class GameData {
    private GameMap gameMap;
    private InfoTicketsList ticketsList; //changer avec liste de pions des détectives
    private InfoTicketsMisterX misterXTickets; // changer avec LE pion de mister X
    
    public GameData(GameMap gameMap, 
            InfoTicketsList ticketsList, 
            InfoTicketsMisterX misterXTickets) {
        this.gameMap = gameMap;
        this.ticketsList = ticketsList;
        this.misterXTickets = misterXTickets;
    }
    
    public GameMap gameMap() {
        return gameMap;
    }
    
    public InfoTicketsList ticketsList() {
        return ticketsList;
    }
    
    public InfoTicketsMisterX misterXTickets() {
        return misterXTickets;
    }
}
