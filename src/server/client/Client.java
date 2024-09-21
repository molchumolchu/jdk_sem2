package server.client;

import server.server.*;

//логика работы, то что не относится к графической части.
    // но в любом случае сначала появляется графическое окно
public class Client
{
    private Server server; // (8) нам его пришлют в момент инициализации // поменяли с ServerWindow
    private boolean connected; // изначально false
    private String name; // конда появится метод логин

    private ClientView view; //(6.4)
    
    public Client (ClientView view, Server server){ // создаем конструктор (3) (7) принимаем объект интерфейса, не связаны напрямую с ClientGUI, только через интерфейс
        this.view = view;
        this.server = server;
        view.setClient(this);
    }
    //все проинициализировали в момент создания (8)

    // дальше добавляем методы (9)

    //(10) подключиться к GUI когда нажмут кнопку логин
    boolean connectToServer(String name) {//+
        this.name = name;
        if (server.connectUser(this)){
            printText("Вы успешно подключились!\n"); // а в ClintView это SendMessage
            // headerPanel.setVisible(false);
            connected = true;
            // name = tfLogin.getText();
            String log = server.getHistory();
            if (log != null){
                printText(log);
            }
            return true;
        } else {
            printText("Подключение не удалось");
            return false;
        }
    }

    //(11)
    public void printText(String text) {
       view.sendMessage(text);
    }

    // мы посылаем сообщение (12)
    public void sendMessage(String message){
        if (connected){
            if (!message.isEmpty()){
                server.sendMessage(name + ": " + message);
            }
        } else {
            printText("Нет подключения к серверу");
        }
    }

    // (13) Сервер нам посылает сообщение
    public void answerFromServer(String answer){//+
        printText(answer);
    }

    //(14) отключиться
    public void disconnect() {
        if (connected) {
            connected = false;
            view.disconnectFromServer();// (15) делает плашечу видимой
            server.disconnectUser(this);
            printText("Вы были отключены от сервера!");
        }
    }

    public String getName() {
        return name;
    }


}
