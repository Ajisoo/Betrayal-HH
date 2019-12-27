import com.dosse.upnp.UPnP;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server{

    public static void main(String[] arg) {
        Server server = new Server(7778);
        server.acceptClients();
    }

    //***********************************************************************************************************************

    private ServerSocket serverSocket;
    private Vector<IThread> clients;

    Server(final int port) {
        Runtime.getRuntime().addShutdownHook(new Thread(){
            public void run(){
                UPnP.closePortTCP(port);
                while (clients.size() > 0){
                    clients.get(0).invalidate();
                }
            }
        });
        UPnP.openPortTCP(port);
        clients = new Vector<IThread>();

        try {
            serverSocket = new ServerSocket(port);
        }
        catch (IOException e) {
            e.printStackTrace();
            try{
                serverSocket.close();
            } catch (IOException e1){
                e1.printStackTrace();
            }
            System.exit(1);
        }

        System.out.println("Server waiting for client on port " + serverSocket.getInetAddress().getHostAddress() + ":" +  serverSocket.getLocalPort());
    }

    void acceptClients(){
        while (true){
            try{
                clients.add(new IThread(serverSocket.accept(), this));
            }
            catch (IOException e){
                Debug.log(2, "Error Accepting Socket");
            }
        }
    }

    void removeIThread(IThread client){
        clients.remove(client);
    }
}
