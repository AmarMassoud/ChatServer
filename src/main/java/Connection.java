import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.stream.Collectors;

public class Connection {

    public Thread thread;
    private int ClientID;
    private String clientName;
    private PrintWriter out;
    private BufferedReader in;
    private Socket clientSocket;

    public void sendOut(String line) {
        out.println(line);
    }

    public int getClientID() {
        return ClientID;
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
                    String str[] = text.split(" ");
                    List<String> al = new ArrayList<String>();
                    al = Arrays.asList(str);
                    if (al.get(0).equalsIgnoreCase("/setname")) {
                        if (!ChatServer.connectionNames.contains(text.replace("/setname ", "").replace("", ""))) {
                           ChatServer.connectionNames.remove(this.clientName);
                            this.clientName = text.replace("/setname ", "").replace("", "");
                            ChatServer.IDNames.put(this.getClientID(), this.clientName);
                            out.println("Your name is now: " + text.replace("/setname", "").replace("", ""));
                            System.out.println(this.ClientID + " Changed their name to " + this.clientName);
                            ChatServer.connectionNames.add(this.clientName);
                        } else {
                            out.println("name is wrong");
                        }
                    } else if (al.get(0).equalsIgnoreCase("/list")) {
                        ChatServer.IDNames.forEach((key, value) -> {
                            out.println(key + ": " + value);
                            System.out.println(key + ": " + value);

                        });
                        System.out.println(ChatServer.IDNames.values());
                        System.out.println(ChatServer.IDNames.keySet());
                    } else {
                        ChatServer.connections.forEach(connection -> {
                            connection.sendOut(clientName == null ? id + ": " + text : clientName + "(" + id + ")" + ": " + text);
                        });
                        System.out.println(clientName == null ? id + ": " + text : clientName + ": " + text);
                    }

                }


            } catch (SocketException | NullPointerException socketException) {
                System.out.println(this.clientName + " disocnnected and has been rmeoved");
                ChatServer.connectionNames.remove(this.clientName);
            } catch (IOException ignored) {
                System.out.println(ignored);
                ChatServer.connections.remove(this);
            }
        });
        thread.start();
    }

    public void interrupt() {
        thread.interrupt();
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public PrintWriter getOut() {
        return out;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }


}
