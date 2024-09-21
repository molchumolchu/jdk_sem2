package server;
import server.client.*;
import server.server.*;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(new ServerWindow(), new FileStorage());
        
        new Client(new ClientGUI(), server);
    }
}
