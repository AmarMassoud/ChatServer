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

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        int id = 0;
        while(true){
            id++;
            connections.add(new Connection(serverSocket.accept(), id));
        }
//        out = new PrintWriter(clientSocket.getOutputStream(), true);
//        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//        while (true){
//            int text = in.read();
//
//            System.out.println(text);
//            out.println(text);
//        }


    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    public static void main(String[] args) throws IOException {
        ChatServer server = new ChatServer();
        System.out.println("server officially started");
        server.start(8089);
    }

}
