package network;

import ui.ConnectPanel;
import util.Debug;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends JFrame {

    public static final String ip = "70.95.161.24";
    public static final int port = 7778;
    public static final String connectPanelBackgroundImagePath = "C:\\Users\\dave\\IdeaProjects\\Betrayal HH\\connect.jpg";

    public static void main(String[] args){
        Client c = new Client();
        c.setVisible(true);
    }

    //***********************************************************************************************************************

    String name;

    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;

    JPanel currentPanel;

    public Client(){
        super();
        setLocation(320,0);
        setSize(1600, 900);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ConnectPanel connectPanel = new ConnectPanel(this, connectPanelBackgroundImagePath);
        currentPanel = connectPanel;
        setContentPane(connectPanel);
    }

    public void connect(String name){
        Debug.log(0, "Connecting with name: " + name);
        this.name = name;

        new Thread(){
            public void run(){
                try{
                    socket = new Socket(ip, port);
                    out = new ObjectOutputStream(socket.getOutputStream());
                    in = new ObjectInputStream(socket.getInputStream());
                } catch(IOException e) {
                    Debug.log(2, "IOException creating socket");
                    try {
                        if (socket != null) socket.close();
                        if (in != null) in.close();
                        if (out != null) out.close();
                    } catch (IOException e1) {
                        Debug.log(2, "IOException closing socket");
                    }
                    ((ConnectPanel) currentPanel).reset();
                }

                Runtime.getRuntime().addShutdownHook(new Thread(){
                    public void run(){
                        try {
                            if (socket != null) socket.close();
                            if (in != null) in.close();
                            if (out != null) out.close();
                        } catch (IOException e){
                            Debug.log(2, "Error closing socket");
                        }
                    }
                });
                finishConnect();
            }
        }.start();
    }

    public void finishConnect(){

    }
}
