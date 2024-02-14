package server;

public class Server implements MessageListener{
    //connection between info and GUI

    private ServerView window;
    private ServerController serverController;

    public Server() {

        this.window = new ServerView(this);
        this.serverController = new ServerController(this);
    }

    public ServerController getServerController() {
        return serverController;
    }

    public ServerView getWindow() {
        return window;
    }
    public boolean getStatus(){
        return serverController.getStatus();
    }

    public void start() {
        serverController.serverStart();
    }

    public void stop() {
        serverController.serverStop();
    }



    @Override
    public void sendMessageFromServer(String message) {
        window.sendMessage(message);
    }

    @Override
    public void answerAll(String text) {
    }

    @Override
    public void sendMessageToServer(String message) {

    }
}
