package scotlandyardserver.client.state;

import scotlandyardserver.client.Client;

public abstract class ClientState {

    private final Client client;

    public ClientState(Client client) {
        this.client = client;
    }

    public abstract void logIn(String username, String password);

    public abstract void logOut();

    public abstract String username();

    public Client client() {
        return client;
    }

    public abstract void setUsername(String newUsername);
}
