package server;

import client.Client;

public interface ServerListener {
    void disconnectUser(Client client);
    void connectUser(Client client);

    void serverStart();
    void serverStop();
}
