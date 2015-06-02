package scotlandyardserver.json;

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
