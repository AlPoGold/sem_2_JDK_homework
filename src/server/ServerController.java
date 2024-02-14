package server;

import client.Client;
import exceptions.ClientException;
import exceptions.ServerException;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;


//Connection between server and clients
public class ServerController implements ServerListener, MessageListener, Logger{
    private final String LOG_PATH ="src/server/resources/log.txt";


    private Server server;
    private ArrayList<Client> clients = new ArrayList<>();
    private boolean isWorking = false;

    public ServerController(Server server) {
        this.server = server;
    }



    public void addNewUser(Client client) {
        if(isWorking){
                clients.add(client);
                message(client.getNameUser() + " is connected to the server!");
        }else throw new ServerException("server doesn't working!");
        }




    @Override
    public void disconnectUser(Client client) {
        deleteUser(client);
    }

    @Override
    public void connectUser(Client client) {
        addNewUser(client);
    }

    @Override
    public void serverStart() {
        if(!isWorking){
            isWorking = true;
            String history = getHistory();
            message("Server is started!");
            sendMessageFromServer(history);
        }else sendMessageFromServer("Server was started earlier!");


    }

    @Override
    public void serverStop() {
        if(isWorking){
            isWorking = false;
            message("Server is stopped!");
            for (Client client: clients
                 ) {
                client.disconnectFromServer();
            }
        }else sendMessageFromServer("Server was stopped already!");


    }

    @Override
    public void sendMessageToServer(String text) {
        answerAll(text);

    }

    @Override
    public void sendMessageFromServer(String message) {
        server.sendMessageFromServer(message);

    }

    @Override
    public void answerAll(String text) {
        if(clients!=null) {
            for (Client client : clients
            ) {
                client.sendMessageFromServer(text);
            }
        }
        sendMessageFromServer(text);

    }

    @Override
    public String readLog() {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader reader = new FileReader(LOG_PATH);){
            int c;
            while ((c = reader.read()) != -1){
                stringBuilder.append((char) c);
            }
            stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());
            return stringBuilder.toString();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String getHistory(){
        String text = readLog();
        if(text!=null) return text;
        else return "History is empty!";
    }

    @Override
    public void writeLog(String text) {
        try (FileWriter writer = new FileWriter(LOG_PATH, true)){
            writer.write(text);
            writer.write("\n");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean getStatus() {
        return isWorking;
    }

    public void setStatus(boolean status) {
        this.isWorking = status;
    }

    public void deleteUser(Client client) {
        clients.remove(client);
        message(client.getNameUser() + " disconnected from the server!");
    }

    public void message(String text){
        writeLog(text); //log
        answerAll(text); //to all authorized users// to window of server
    }

}
