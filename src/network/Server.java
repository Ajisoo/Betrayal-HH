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

    private ServerSocket serverSocket;
    private Vector<User> users;

    private int[] usedColors;

    private Data data;

    Server(final int port) {
        Runtime.getRuntime().addShutdownHook(new Thread(){
            public void run(){
                UPnP.closePortTCP(port);
                if (users == null) return;
                while (users.size() > 0){
                    users.get(0).serverThread.invalidate();
                }
            }
        });
        UPnP.closePortTCP(port);
        users = new Vector<User>();
        usedColors = new int[COLORS.length];

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
                ServerThread newThread = new ServerThread(serverSocket.accept(), this);
                users.add(new User(newThread, generateColor()));
                newThread.start();
                System.out.println("Accepting client.");
            }
            catch (IOException e){
                Debug.log(2, "Error Accepting Socket");
            }
        }
    }

    void removeIThread(ServerThread client){
        int i;
        for (i = 0; i < users.size(); i++){
            if (users.get(i).serverThread == client) break;
        }
        for (int j = 0; j < COLORS.length; j++){
            if (users.get(i).color == COLORS[j]) usedColors[j]--;
        }
        users.remove(i);
    }

    private Color generateColor(){
        int min = 99;
        synchronized(usedColors) {
            for (int i = 0; i < COLORS.length; i++) {
                if (usedColors[i] < min) min = usedColors[i];
            }

            while (true) {
                int index = (int) (Math.random() * COLORS.length);
                if (usedColors[index] == min) {
                    usedColors[index]++;
                    return COLORS[index];
                }
            }
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
