/**
 * Classe qui permet de recevoir un ensemble de données de joueurs en JSon
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardclient.json;

import java.util.LinkedList;

public class PlayerDataList {

    private LinkedList<PlayerData> playersData = new LinkedList<>();

    public LinkedList<PlayerData> playersData() {
        return playersData;
    }

    public void add(PlayerData playerData) {
        playersData.add(playerData);
    }
}
