package scotlandyardserver.commands;

import scotlandyardserver.Server;
import scotlandyardserver.client.Client;

public class CommandManager {

    private static final String BAD_COMMAND = "Bad Command";

    private enum CommandFromClient {

        PLAY_DETECTIVE_TURN(3),
        PLAY_MISTER_X_TURN(4),
        CREATEGAME(3),
        JOINGAME(1),
        AUTHENTICATE(2),
        UNAUTHENTICATE(0),
        CREATEACCOUNT(2),
        REQUESTMAPLIST(0),
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

    public CommandManager(Server server) {
        this.server = server;
    }

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
        } else if (nameOfCommand.equals(CommandFromClient.JOINGAME.name())) {
            if (CommandFromClient.JOINGAME.numberOfArgs() == nbArgs) {
                client.joinGame(commandWithArgs[1]);
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
        } else {
            client.sendMessage(BAD_COMMAND);
        }

    }

    private String[] parseCommand(String command) {
        return command.split("#");
    }

}
