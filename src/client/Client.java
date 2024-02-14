package client;

import server.MessageListener;
import server.Server;

public class Client implements ClientController, MessageListener {

    private Server server;



    private String nameUser;
    private ClientView clientWindow;

    public boolean getStatus() {
        return isRegistered;
    }

    private boolean isRegistered;


    public Client(Server server, String name) {
        this.server = server;
        this.nameUser = name;
        this.clientWindow = new ClientView(this);


    }


    public String getNameUser() {
        return nameUser;
    }
    public Server getServer() {
        return server;
    }
    @Override
    public void connectToServer() {
        isRegistered = true;
        server.getServerController().connectUser(this);
        clientWindow.sendMessage(server.getServerController().getHistory());
    }

    @Override
    public void disconnectFromServer() {
        isRegistered = false;
        server.getServerController().disconnectUser(this);

    }



    @Override
    public void sendMessageToServer(String text) {
        server.getServerController().answerAll(text);

    }

    @Override
    public void sendMessageFromServer(String message) {
        clientWindow.sendMessage(message);
    }

    @Override
    public void answerAll(String text) {
//

    }
}
