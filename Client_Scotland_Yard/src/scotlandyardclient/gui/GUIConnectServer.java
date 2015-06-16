package scotlandyardclient.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import scotlandyardclient.Client;

public class GUIConnectServer extends JFrame {

    private JLabel lblIPAdress = new JLabel("Adresse IP : ");
    private JTextField txtIPAdress = new JTextField();
    private JLabel lblPortNumber = new JLabel("Numéro de port : ");
    private JTextField txtPortNumber = new JTextField();
    private JLabel lblUsername = new JLabel("Nom d'utilisateur : ");
    private JTextField txtUsername = new JTextField();
    private JLabel lblPassword = new JLabel("Mot de passe : ");
    private JPasswordField txtPassword = new JPasswordField();

    private JButton createAccount = new JButton("Créer un compte");
    private JButton close = new JButton("Fermer");
    private JButton connect = new JButton("Se connecter");

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

        close.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                GUIConnectServer.this.dispose();
                Client.getInstance().sendCommand("bye");
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        connect.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                Client.getInstance().connect(txtIPAdress.getText(), Integer.parseInt(txtPortNumber.getText()));

                if (Client.getInstance().logIn(txtUsername.getText(), txtPassword.getText())) {
                    JOptionPane.showMessageDialog(createAccount, "Connexion avec le serveur effectuée", "Connecté", JOptionPane.INFORMATION_MESSAGE);
                    new GUIGameRoom(txtUsername.getText());
                    GUIConnectServer.this.dispose();
                } else {
                    JOptionPane.showMessageDialog(createAccount, "Mot de passe ou nom d'utilisateur incorrects", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        createAccount.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                Client.getInstance().connect(txtIPAdress.getText(), Integer.parseInt(txtPortNumber.getText()));
                new GUIUserAddForm();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {

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
