import client.Client;
import server.Server;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Server server = new Server();
                new Client(server, "first");
                new Client(server, "second");

            }
        });
    }
}