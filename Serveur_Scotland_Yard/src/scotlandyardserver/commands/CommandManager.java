/**
 * Cette classe permet de gérer le traitement des commandes recues par le serveur
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */

package scotlandyardserver.commands;

import scotlandyardserver.Server;
import scotlandyardserver.client.Client;

public class CommandManager {

    private static final String BAD_COMMAND = "Bad Command";

    private enum CommandFromClient {

        PLAY_DETECTIVE_TURN(3),
        PLAY_MISTER_X_TURN(4),
        PLAYERLEAVEGAME(0),
        CREATEGAME(3),
        JOINGAME(1),
        STARTGAME(1),
        AUTHENTICATE(2),
        UNAUTHENTICATE(0),
        CREATEACCOUNT(2),
        REQUESTMAPLIST(0),
        REQUESTGAMELIST(0),
        REQUESTPLAYERLIST(1),
        EDITACCOUNT(2);

        private int numberOfArgs;

        private CommandFromClient(int numberOfArgs) {
            this.numberOfArgs = numberOfArgs;
        }

        public int numberOfArgs() {
            return numberOfArgs;
        }
    }

    private Server server;

    /**
     * Constructeur
     * @param server Le serveur 
     */
    public CommandManager(Server server) {
        this.server = server;
    }

    /**
     * Permet d'exécuter une commande reçue par un client
     * @param command La commande envoyée par le client
     * @param client Le client qui a envoyé la commande
     */
    public void executeCommand(String command, Client client) {
        String[] commandWithArgs = parseCommand(command);
        String nameOfCommand = commandWithArgs[0];
        int nbArgs = commandWithArgs.length - 1;

        if (nameOfCommand.equals(CommandFromClient.PLAY_DETECTIVE_TURN.name())) {
            if (CommandFromClient.PLAY_DETECTIVE_TURN.numberOfArgs() == nbArgs) {
                server.playDetectiveTurn(client,
                        Integer.parseInt(commandWithArgs[1]),
                        commandWithArgs[2],
                        Integer.parseInt(commandWithArgs[3]));
            } else {
                client.sendMessage(BAD_COMMAND);
            }

        } else if (nameOfCommand.equals(CommandFromClient.PLAY_MISTER_X_TURN.name())) {

            if (CommandFromClient.PLAY_MISTER_X_TURN.numberOfArgs() == nbArgs) {
                server.playMisterXTurn(client,
                        Integer.parseInt(commandWithArgs[1]),
                        commandWithArgs[2],
                        Integer.parseInt(commandWithArgs[3]),
                        Boolean.parseBoolean(commandWithArgs[4]));
            } else {
                client.sendMessage(BAD_COMMAND);
            }

        } else if (nameOfCommand.equals(CommandFromClient.CREATEGAME.name())) {
            if (CommandFromClient.CREATEGAME.numberOfArgs() == nbArgs) {
                client.createGame(commandWithArgs[1],
                        Integer.parseInt(commandWithArgs[2]),
                        commandWithArgs[3]
                );
            } else {
                client.sendMessage(BAD_COMMAND);
            }
        } else if (nameOfCommand.equals(CommandFromClient.AUTHENTICATE.name())) {
            if (CommandFromClient.AUTHENTICATE.numberOfArgs() == nbArgs) {
                client.logIn(
                        commandWithArgs[1],
                        commandWithArgs[2]
                );
            } else {
                client.sendMessage(BAD_COMMAND);
            }
        } else if (nameOfCommand.equals(CommandFromClient.UNAUTHENTICATE.name())) {
            if (CommandFromClient.UNAUTHENTICATE.numberOfArgs() == nbArgs) {
                client.logOut();
            } else {
                client.sendMessage(BAD_COMMAND);
            }
        } else if (nameOfCommand.equals(CommandFromClient.CREATEACCOUNT.name())) {
            if (CommandFromClient.CREATEACCOUNT.numberOfArgs() == nbArgs) {
                server.createAccount(client,
                        commandWithArgs[1],
                        commandWithArgs[2]
                );
            } else {
                client.sendMessage(BAD_COMMAND);
            }
        } else if (nameOfCommand.equals(CommandFromClient.EDITACCOUNT.name())) {
            if (CommandFromClient.EDITACCOUNT.numberOfArgs() == nbArgs) {
                server.editAccount(client,
                        commandWithArgs[1],
                        commandWithArgs[2]
                );
            } else {
                client.sendMessage(BAD_COMMAND);
            }
        } else if (nameOfCommand.equals(CommandFromClient.PLAYERLEAVEGAME.name())) {
            if (CommandFromClient.PLAYERLEAVEGAME.numberOfArgs() == nbArgs) {
                client.leaveGame();
            } else {
                client.sendMessage(BAD_COMMAND);
            }
        } else if (nameOfCommand.equals(CommandFromClient.JOINGAME.name())) {
            if (CommandFromClient.JOINGAME.numberOfArgs() == nbArgs) {
                client.joinGame(commandWithArgs[1]);
            } else {
                client.sendMessage(BAD_COMMAND);
            }
        } else if (nameOfCommand.equals(CommandFromClient.STARTGAME.name())) {
            if (CommandFromClient.STARTGAME.numberOfArgs() == nbArgs) {
                client.server().getGamesManager().startGame(commandWithArgs[1]);
            } else {
                client.sendMessage(BAD_COMMAND);
            }
        } else if (nameOfCommand.equals(CommandFromClient.EDITACCOUNT.name())) {
            if (CommandFromClient.EDITACCOUNT.numberOfArgs() == nbArgs) {
                server.editAccount(client,
                        commandWithArgs[1],
                        commandWithArgs[2]
                );
            } else {
                client.sendMessage(BAD_COMMAND);
            }
        } else if (nameOfCommand.equals(CommandFromClient.REQUESTMAPLIST.name())) {
            if (CommandFromClient.REQUESTMAPLIST.numberOfArgs() == nbArgs) {
                server.requestMapNames(client);
            } else {
                client.sendMessage(BAD_COMMAND);
            }
        } else if (nameOfCommand.equals(CommandFromClient.REQUESTGAMELIST.name())) {
            if (CommandFromClient.REQUESTGAMELIST.numberOfArgs() == nbArgs) {
                server.requestGameList(client);
            } else {
                client.sendMessage(BAD_COMMAND);
            }
            } else if (nameOfCommand.equals(CommandFromClient.REQUESTPLAYERLIST.name())) {
            if (CommandFromClient.REQUESTPLAYERLIST.numberOfArgs() == nbArgs) {
                server.requestPlayerList(client, commandWithArgs[1]);
            } else {
                client.sendMessage(BAD_COMMAND);
            }
        } else {
            client.sendMessage(BAD_COMMAND);
        }

    }

    /**
     * Permet de parser la commande selon le protocol de l'applicatif
     * @param command La commande qu'on doit parser
     * @return Un tableau de string représentant la commande parsée
     */
    private String[] parseCommand(String command) {
        return command.split("#");
    }

}
