/**
 * Cette classe permet de transmettre les données d'une partie en JSon
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
package scotlandyardserver.json;

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
