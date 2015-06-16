/**
 * Classe qui permet de recevoir les données d'une partie en JSon
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardclient.json;

public class GameData {

    private final GameMap gameMap;
    private final PlayerDataList playerDataList;
    private final MisterXData misterXData;

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
