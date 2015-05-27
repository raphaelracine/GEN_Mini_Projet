package scotlandyardclient;

import scotlandyardclient.gui.GUIConnectServer;
import scotlandyardclient.gui.GUIGame;
import scotlandyardclient.gui.GUIGameMisterX;
import com.google.gson.*;
import scotlandyardclient.json.InfoTickets;
import scotlandyardclient.json.InfoTicketsMisterX;

public class Client_Scotland_Yard {

    public static void main(String... args) {
        new GUIConnectServer();
        //InfoTicketsMisterX i = new InfoTicketsMisterX("Salut", 50, 50, 50, 10, 20);        
        //System.out.println(new Gson().fromJson(new Gson().toJson(i), InfoTickets.class).getClass().getSimpleName());
    }
}
