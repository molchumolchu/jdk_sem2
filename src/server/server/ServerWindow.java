package server.server;
import server.client.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class ServerWindow extends JFrame implements ServerView {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;
    

    private Server server;
    private JButton btnStart, btnStop;
    private JTextArea log;
    

    public ServerWindow(){
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setLocationRelativeTo(null);

        createPanel();

        setVisible(true);
    }

    @Override
    public void setServer(Server server) {
        this.server=server;    
    }

    // public boolean connectUser(ClientGUI clientGUI){
    //     if (!work){
    //         return false;
    //     }
    //     clientGUIList.add(clientGUI);
    //     return true;
    // }

    // public String getLog(String message) {
    //     return readLog(message);
    // }

    // public void disconnectUser(ClientGUI clientGUI){
    //     clientGUIList.remove(clientGUI);
    //     if (clientGUI != null){
    //         clientGUI.disconnectFromServer();
    //     }
    // }
    // public void disconnectUser(){
    //     server.disconnectUser(null);
    // }

    // public void message(String text){
    //     if (!work){
    //         return;
    //     }
    //     text += "";
    //     appendLog(text);
    //     answerAll(text);
    //     saveInLog(text);
    // }

    // public void sendMessage(String message){
    //     server.sendMessage(message);
    // }

    // public Server getConnection(){
    //     return server;
    // }

    // private void answerAll(String text){
    //     for (ClientGUI clientGUI: clientGUIList){
    //         clientGUI.answer(text);
    //     }
    // }

    // private void saveInLog(String text){
    //     try (FileWriter writer = new FileWriter(LOG_PATH, true)){
    //         writer.write(text);
    //         writer.write("\n");
    //     } catch (Exception e){
    //         e.printStackTrace();
    //     }
    // }

    // private String readLog(){
    //     StringBuilder stringBuilder = new StringBuilder();
    //     try (FileReader reader = new FileReader(LOG_PATH);){
    //         int c;
    //         while ((c = reader.read()) != -1){
    //             stringBuilder.append((char) c);
    //         }
    //         stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());
    //         return stringBuilder.toString();
    //     } catch (Exception e){
    //         e.printStackTrace();
    //         return null;
    //     }
    // }
    // private String readLog(String massege){
    //     return server.sendMessage(massege);

    // }

    // private void appendLog(String text){
    //     log.append(text + "\n");
    // }

    private void createPanel() {
        log = new JTextArea();
        add(log);
        add(createButtons(), BorderLayout.SOUTH);
    }

    private Component createButtons() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        btnStart = new JButton("Start");
        btnStop = new JButton("Stop");

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.start();
                // if (server.work){
                //     appendLog("Сервер уже был запущен");
                // } else {
                //     server.work = true;
                //     appendLog("Сервер запущен!");
                // }
            }
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.stop();
                // if (!server.work){
                //     appendLog("Сервер уже был остановлен");
                // } else {
                //     server.work = false;
                //     while (!server.clientList.isEmpty()){
                //         disconnectUser();
                //     }
                //     appendLog("Сервер остановлен!");
                // }
            }
        });

        panel.add(btnStart);
        panel.add(btnStop);
        return panel;
    }

    @Override
    public void showMessage(String message) {
        log.append(message);
    } 
}
