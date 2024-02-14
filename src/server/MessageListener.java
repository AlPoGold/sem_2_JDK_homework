package server;

public interface MessageListener {
    void sendMessageToServer(String message);
    void sendMessageFromServer(String message);
    void answerAll(String message);


}
