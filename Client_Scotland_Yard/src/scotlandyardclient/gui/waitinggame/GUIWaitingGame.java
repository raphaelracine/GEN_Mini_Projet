package scotlandyardclient.gui.waitinggame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import scotlandyardclient.Client;
import scotlandyardclient.Utils;
import scotlandyardclient.gui.GUIGameRoom;

public abstract class GUIWaitingGame extends JFrame implements Runnable {
    
    private final GUIGameRoom room;
    private final String game;    
        
    private final Thread activity;
    
    private final JButton quit = new JButton("Quitter");
    private final JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    
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
        
        activity = new Thread(this);
        activity.start();
    }    
        
    protected JPanel southPanel() {
        return southPanel;
    }
}
