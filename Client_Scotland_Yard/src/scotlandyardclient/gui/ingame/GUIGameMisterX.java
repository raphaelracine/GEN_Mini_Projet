
package scotlandyardclient.gui.ingame;

import javax.swing.JCheckBox;

public class GUIGameMisterX extends GUIGame {
    
    public class ControlPanelMisterX extends GUIGame.ControlPanel {
        
        private final JCheckBox doubleTurn = new JCheckBox("Coup double");
        
        public ControlPanelMisterX() {
            add(doubleTurn);
        }
    }
    
    @Override
    void initSouthPanel() {
        southPanel().add(new ControlPanelMisterX());
    }
}
