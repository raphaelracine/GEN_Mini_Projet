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
