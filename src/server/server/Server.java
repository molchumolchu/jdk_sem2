package server.server;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import server.client.Client;
import server.client.ClientGUI;

public class Server {

    private Client client;
    private ServerView serverView;//+
    private List<Client> clientList;//+
    private boolean work; //+
    private Repository<String> repository;//+

    // public static final String LOG_PATH = "src/server/log.txt";

    public Server(ServerView serverView, Repository<String> repository) {
        this.serverView = serverView;
        this.repository = repository;
        clientList = new ArrayList<>();
        serverView.setServer(this);
    }

    public void start() {
        if (work){
            appendLog("Сервер уже был запущен");
        } else {
            work = true;
            appendLog("Сервер запущен!");
        }
    }

    public void stop() {
        if (!work){
            appendLog("Сервер уже был остановлен");
        } else {
            work = false;
            while (!clientList.isEmpty()){
                disconnectUser(clientList.get(clientList.size() - 1));
            }
            appendLog("Сервер остановлен!");
        }
    }

    public void disconnectUser(Client client){
        clientList.remove(client);
        if (client != null){
            client.disconnect();
            appendLog(client.getName() + " отключился от беседы");
        }
    }

    public boolean connectUser(Client client) {
        if (!work){
            return false;
        }
        clientList.add(client);
        appendLog(client.getName() + " подключился к беседе");      
        return true;
    }


    public void sendMessage(String meassage){
        if (!work){
            return;
        }
        meassage += "";
        appendLog(meassage);
        answerAll(meassage);
        saveInLog(meassage);
    }

    public String getHistory() {
        return repository.load();    
    }

    private void answerAll(String meassage) {
        for (Client client: clientList){
            client.printText(meassage);
            // client.answerFromServer(meassage);
        }
    }

    
    // private String readLog() {
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


    private void saveInLog(String meassage) {
        repository.save(meassage);   
    }

    
    private void appendLog(String meassage) {
        serverView.showMessage(meassage + "\n");    
    }

    

    

    
}
