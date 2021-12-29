import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {

    private ServerSocket serverSocket;
    public static ArrayList<Connection> connections = new ArrayList<>();
    public static ArrayList<String> connectionNames = new ArrayList<>();

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        int id = 0;
        while(true){
            id++;
            connections.add(new Connection(serverSocket.accept(), id));
        }

    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("server officiallly started");
        ChatServer server = new ChatServer();
        server.start(8089);
    }

}
