/**
 * Classe qui représente l'interface graphique qui permet à un joueur d'attendre
 * le début de la partie
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardclient.gui.waitinggame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import scotlandyardclient.Client;
import scotlandyardclient.gui.GUIGameRoom;

public abstract class GUIWaitingGame extends JFrame implements Runnable {
    
    private final GUIGameRoom room;
    private final String game;    
        
    private Thread activity;
    
    private final JButton quit = new JButton("Quitter");
    private final JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    
    /**
     * Constructeur
     * @param room Interface graphique de la salle de jeu parente
     * @param game Nom de la partie
     */
    public GUIWaitingGame(GUIGameRoom room, String game) {
        this.game = game;
        this.room = room;       
        
        setTitle("Attente de joueurs");
        pack();
        setVisible(true);
        
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client.getInstance().leaveGame();
            }
        });

        southPanel.add(quit);
        getContentPane().add(southPanel, BorderLayout.SOUTH);
    }  
    
    /**
     * Permet de démarrer l'attente du début de la partie
     */
    public void startWaiting() {
         activity = new Thread(this);
         activity.start();
    }
        
    /**
     * Permet d'obtenir le panel SUD dans les sous-classes
     * @return Panel sud
     */
    protected JPanel southPanel() {
        return southPanel;
    }
}
