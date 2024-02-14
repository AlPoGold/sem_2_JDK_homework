package client;

import server.Server;
import server.ServerController;
import server.ServerView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientView extends JFrame{
    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;

    private Client client;

    JTextArea log;
    JTextField tfIPAddress, tfPort, tfLogin, tfMessage;
    JPasswordField password;
    JButton btnLogin, btnSend;
    JPanel headerPanel;

    public ClientView(Client client){
        this.client = client;
        Server server = client.getServer();
        setting(server.getWindow());
        createPanel();

        setVisible(true);
    }

    private void setting(ServerView server) {
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat client");
        setLocation(server.getX() - 500, server.getY());
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        //TODO: здесь мы получаем контроллер сервера, нужно чтобы связь была слабая

//        client = new Client(this, server.getController());
    }

    private void hideHeaderPanel(boolean visible){
        headerPanel.setVisible(visible);
    }


    private void createPanel() {
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createLog());
        add(createFooter(), BorderLayout.SOUTH);
    }

    private Component createHeaderPanel(){
        headerPanel = new JPanel(new GridLayout(2, 3));
        tfIPAddress = new JTextField("127.0.0.1");
        tfPort = new JTextField("8189");
        tfLogin = new JTextField("Ivan Ivanovich");
        password = new JPasswordField("123456");
        btnLogin = new JButton("login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO login to the server
                client.connectToServer();
                tfMessage.setEditable(true);
                hideHeaderPanel(false);
            }
        });

        headerPanel.add(tfIPAddress);
        headerPanel.add(tfPort);
        headerPanel.add(new JPanel());
        headerPanel.add(tfLogin);
        headerPanel.add(password);
        headerPanel.add(btnLogin);

        return headerPanel;
    }

    private Component createLog(){
        log = new JTextArea();
        log.setEditable(false);
        return new JScrollPane(log);
    }

    private Component createFooter() {
        JPanel panel = new JPanel(new BorderLayout());
        tfMessage = new JTextField();
        tfMessage.setEditable(false);
        tfMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n'){
                    //TODO send message
                    if(client.getStatus()){
                        client.sendMessageToServer(tfMessage.getText());
                        tfMessage.setText("");
                    }
                }
            }
        });
        btnSend = new JButton("send");
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO send message
                if(client.getStatus()){
                    client.sendMessageToServer(tfMessage.getText());
                    tfMessage.setText("");
                }
            }
        });
        panel.add(tfMessage);
        panel.add(btnSend, BorderLayout.EAST);
        return panel;
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING){
            ////TODO disconnect to the server
            client.disconnectFromServer();
        }
    }


    public void sendMessage(String text) {
        if(client.getStatus()){
            log.append(text+"\n");
        }

    }
}
