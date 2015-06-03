
package scotlandyardclient.gui.ingame;

import javax.swing.JCheckBox;

public class GUIGameMisterX extends GUIGame {
    
    public class ControlPanelMisterX extends GUIGame.ControlPanel {
        
        private final JCheckBox doubleTurn = new JCheckBox("Coup double");
        private final JCheckBox hideLocomotion = new JCheckBox("Utiliser un ticket noir");
        
        public ControlPanelMisterX() {
            add(doubleTurn);
            add(hideLocomotion);
        }
    }
    
    @Override
    void initSouthPanel() {
        southPanel().add(new ControlPanelMisterX());
    }
}
