/**
 * Cette classe permet de transmettre une liste de nom des cartes disponibles en
 * Json
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

import java.util.ArrayList;

public class MapNames {

    private final ArrayList<String> names = new ArrayList<String>();

    public MapNames() {

    }

    public void add(String s) {
        names.add(s);
    }

    public ArrayList<String> names() {
        return names;
    }
}
