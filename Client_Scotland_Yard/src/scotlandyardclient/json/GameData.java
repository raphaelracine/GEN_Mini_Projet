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
    private PlayerDataList playerDataList;
    private MisterXData misterXData;
    
    public GameData(GameMap gameMap, 
            PlayerDataList playerDataList, 
            MisterXData misterXData) {
        this.gameMap = gameMap;
        this.playerDataList = playerDataList;
        this.misterXData = misterXData;
    }
    
    public GameMap gameMap() {
        return gameMap;
    }
    
    public PlayerDataList playerDataList() {
        return playerDataList;
    }
    
    public MisterXData misterXData() {
        return misterXData;
    }
}
