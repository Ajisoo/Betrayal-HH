package network;

import network.ServerThread;

import java.awt.*;

public class User {

    ServerThread serverThread;
    Color color;
    String name;

    public User(ServerThread thread, Color c){
        this.serverThread = thread;
        this.color = c;
    }

}
