import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection {

    public Thread thread;
    public int ClientID;
    private PrintWriter out;
    private BufferedReader in;
    private Socket clientSocket;

    public void sendOut(String line) {
        out.println(line);
    }

    Connection(Socket clientSocket, int id) {
        this.ClientID = id;
        this.clientSocket = clientSocket;
        thread = new Thread(() -> {
            try {
               out = new PrintWriter(clientSocket.getOutputStream(), true);
               in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                while (true) {
                    String text = in.readLine();
                    ChatServer.connections.forEach(connection -> {
                        connection.sendOut(id + " said: " + text);
                    });
                        System.out.println(id + " said: " + text);

                }
            } catch (IOException ignored){
                System.out.println(ignored);
                ChatServer.connections.remove(this);
            }
        });
        thread.start();
    }

    public void interrupt(){
        thread.interrupt();
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public PrintWriter getOut() {
        return out;
    }
}
