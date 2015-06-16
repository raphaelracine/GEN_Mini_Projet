/**
 * Classe qui représente l'interface graphique du jeu principale de Mister X
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardclient.gui.ingame;

import javax.swing.JCheckBox;

public class GUIGameMisterX extends GUIGame {

    public class ControlPanelMisterX extends GUIGame.ControlPanel {

        private final JCheckBox doubleTurn = new JCheckBox("Coup double");

        /**
         * Constructeur
         */
        public ControlPanelMisterX() {
            add(doubleTurn);
        }
    }

    @Override
    void initSouthPanel() {
        southPanel().add(new ControlPanelMisterX());
    }
}
