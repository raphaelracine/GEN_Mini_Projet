/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scotlandyardclient.pone;

import java.awt.Color;
import scotlandyardclient.json.Station;

public class PoneMisterX extends Pone {
    private int nbBlackTickets;
    private int nbDoubleTickets;
    
    public PoneMisterX(Color color, String playerName, Station currentStation, int nbBlackTickets, int nbDoubleTickets, int nbTaxiTickets, int nbBusTickets, int nbMetroTickets) {
        super(color, playerName, currentStation, nbTaxiTickets, nbBusTickets, nbMetroTickets);
        this.nbBlackTickets = nbBlackTickets;
        this.nbDoubleTickets = nbDoubleTickets;                
    }
    
    public void useBlackTicket() {
        nbBlackTickets--;
    }
    
    public void useDoubleTicket() {
        nbDoubleTickets--;
    }
    
    @Override
    public boolean isMisterX() {
        return true;
    }
}
