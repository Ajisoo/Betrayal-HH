package network;

import com.dosse.upnp.UPnP;
import data.Data;
import util.Debug;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server{

    public static void main(String[] arg) {
        Server server = new Server(7778);
        server.acceptClients();
    }

    //***********************************************************************************************************************

    private final ServerSocket serverSocket;
    private final ArrayList<User> users;

    private final int[] usedColors;

    private Data data;

    Server(final int port) {
        Runtime.getRuntime().addShutdownHook(new Thread(){
            public void run(){
                UPnP.closePortTCP(port);
                if (users == null) return;
                while (users.size() > 0){
                    users.get(0).disconnect();
                }
            }
        });

        UPnP.closePortTCP(port);

        users = new ArrayList<User>();
        usedColors = new int[COLORS.length];

        serverSocket = getServerSocket(port);
        System.out.println("Server waiting for client on port " + serverSocket.getInetAddress().getHostAddress() + ":" +  serverSocket.getLocalPort());
    }

    private ServerSocket getServerSocket(int port){
        try {
            return new ServerSocket(port);
        }
        catch (IOException e) {
            System.exit(1);
        }
        return null;
    }

    private void acceptClients(){
        while (true){
            try{
                User newUser = new User(serverSocket.accept(), this, generateColor());
                synchronized(users){
                    users.add(newUser);
                }
                System.out.println("Accepting client.");
            }
            catch (IOException e){
                Debug.log(2, "Error Accepting Socket");
            }
        }
    }

    void removeUser(User user){
        synchronized(users) {
            users.remove(user);
        }

        for (int i = 0; i < COLORS.length; i++) {
            if (user.color == COLORS[i]) {
                synchronized(usedColors){
                    usedColors[i]--;
                }
            }
        }
    }

    private Color generateColor(){
        int min = 99;
        int index = -1;
        synchronized(usedColors) {
            for (int i = 0; i < COLORS.length; i++) {
                if (usedColors[i] < min) {
                    index = i;
                    min = usedColors[i];
                }
            }
            return COLORS[index];
        }
    }

    public static final Color[] COLORS = {
            new Color(255, 255, 255),
            new Color(255, 0, 0),
            new Color(153, 90, 20),
            new Color(255, 153, 51),
            new Color(240, 240, 0),
            new Color(51, 255, 51),
            new Color(51, 153, 51),
            new Color(102, 255, 255),
            new Color(51, 153, 255),
            new Color(153, 53, 255),
            new Color(255, 0, 255),
            new Color(255, 51, 153),
            new Color (160, 160, 160)
    };

}
