/**
 * Classe qui représente l'interface graphique permettant de se connecter à un
 * serveur et de s'authentifier.
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardclient.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import scotlandyardclient.Client;

public class GUIConnectServer extends JFrame {

    // Composants graphiques...
    private final JLabel lblIPAdress = new JLabel("Adresse IP : ");
    private JTextField txtIPAdress = new JTextField();
    private final JLabel lblPortNumber = new JLabel("Numéro de port : ");
    private JTextField txtPortNumber = new JTextField();
    private final JLabel lblUsername = new JLabel("Nom d'utilisateur : ");
    private JTextField txtUsername = new JTextField();
    private final JLabel lblPassword = new JLabel("Mot de passe : ");
    private JPasswordField txtPassword = new JPasswordField();

    private JButton createAccount = new JButton("Créer un compte");
    private final JButton close = new JButton("Fermer");
    private final JButton connect = new JButton("Se connecter");

    /**
     * Constructeur
     */
    public GUIConnectServer() {
        JPanel formArea = new JPanel(new GridLayout(4, 2));
        formArea.add(lblIPAdress);
        formArea.add(txtIPAdress);
        formArea.add(lblPortNumber);
        formArea.add(txtPortNumber);
        formArea.add(lblUsername);
        formArea.add(txtUsername);
        formArea.add(lblPassword);
        formArea.add(txtPassword);
        formArea.add(lblPassword);
        formArea.add(txtPassword);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        close.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                GUIConnectServer.this.dispose();
                Client.getInstance().sendCommand("bye");
            }
        });

        connect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Client.getInstance().connect(txtIPAdress.getText(), Integer.parseInt(txtPortNumber.getText()));

                if (Client.getInstance().logIn(txtUsername.getText(), txtPassword.getText())) {
                    JOptionPane.showMessageDialog(createAccount, "Connexion avec le serveur effectuée", "Connecté", JOptionPane.INFORMATION_MESSAGE);
                    new GUIGameRoom(txtUsername.getText()).refreshGameList();
                    GUIConnectServer.this.dispose();
                } else {
                    JOptionPane.showMessageDialog(createAccount, "Mot de passe ou nom d'utilisateur incorrects", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        createAccount.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Client.getInstance().connect(txtIPAdress.getText(), Integer.parseInt(txtPortNumber.getText()));
                new GUIUserAddForm();
            }
        });

        buttons.add(close);
        buttons.add(connect);
        buttons.add(createAccount);

        txtIPAdress.setText("localhost");
        txtPortNumber.setText("3000");
        txtPassword.setText("Goldorak%1");

        getContentPane().add(formArea, BorderLayout.CENTER);
        getContentPane().add(buttons, BorderLayout.SOUTH);
        setTitle("Connexion au serveur de jeu");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}
