package server.client;
import server.server.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public class ClientGUI extends JFrame implements ClientView { //подписались на Интерфейс (6)
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;



    private JTextArea log;
    private JTextField tfIPAddress, tfPort, tfLogin, tfMessage;
    private JPasswordField password;
    private JButton btnLogin, btnSend;
    private JPanel headerPanel;

    private Client client; //добавили (1)

    public ClientGUI(){
        // this.server = server; вместо этого создаем нового клиента (2)
        // client = new Client(this, server.getSever()); //создать метод 

        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat client");
        // setLocation(server.getX() - 500, server.getY());

        createPanel();

        setVisible(true);
    }

    // @Override
    // public void setClient(Client client) {
    //     this.client = client;
    // }

    // public void answer(String text){
    //     appendLog(text);
    // }


    // перенесли метод в Клиента и переписали

    // private void connectToServer() {
    //     if (server.connectUser(this)){
    //         appendLog("Вы успешно подключились!\n");
    //         headerPanel.setVisible(false);
    //         connected = true;
    //         name = tfLogin.getText();
    //         String log = server.getLog();
    //         if (log != null){
    //             appendLog(log);
    //         }
    //     } else {
    //         appendLog("Подключение не удалось");
    //     }
    // }
    // если зарегистрирован то скрываем верхнюю панель
    private void connectToServer() {
        if (client.connectToServer(tfLogin.getText())){
            hideHeaderPanel(false);
        }
    }
    // перенесли метод в Клиента

    // public void disconnectFromServer() {
    //     if (connected) {
    //         headerPanel.setVisible(true);
    //         connected = false;
    //         server.disconnectUser(this);
    //         appendLog("Вы были отключены от сервера!");
    //     }
    // }

    
    // (12 )забрали действие в Клиента и переписали

    // public void message(){
    //     if (connected){
    //         String text = tfMessage.getText();
    //         if (!text.equals("")){
    //             server.message(name + ": " + text);
    //             tfMessage.setText("");
    //         }
    //     } else {
    //         appendLog("Нет подключения к серверу");
    //     }

    // }
    
    //!
    //(6.3) Добавляем метод 
    @Override//+
    public void sendMessage(String message) {
        appendLog(message);

    }
    
    public void sendMessage(){
        client.sendMessage(tfMessage.getText());// посылаем клиенту сигналы о том, что произошло событие
        tfMessage.setText("");
    }


    public void disconnectFromServer(){//+
        hideHeaderPanel(true);
        // client.disconnect();
    }


    // метод изменения видимости панели
    private void hideHeaderPanel(boolean visible) {
        headerPanel.setVisible(visible);
        
    }

    private void appendLog(String text){
        log.append(text + "\n");
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
                connectToServer();
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
        tfMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n'){
                    sendMessage();
                }
            }
        });
        btnSend = new JButton("send");
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
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
            disconnectFromServer();
        }
        super.processWindowEvent(e);
    }

    @Override
    public void setClient(Client client) {
        this.client = client;    
    }

    

    
}
