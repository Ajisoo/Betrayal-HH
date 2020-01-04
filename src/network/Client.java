package network;

import ui.ConnectPanel;
import ui.MainPanel;
import util.Debug;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends JFrame {

    public static final String ip = "localhost";
    public static final int port = 7778;
    public static final String connectPanelBackgroundImagePath = "C:\\Users\\dave\\IdeaProjects\\Betrayal HH\\connect.jpg";

    public static void main(String[] args){
        Client c = new Client();
    }

    //***********************************************************************************************************************

    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;

    JPanel currentPanel;

    ConnectPanel connectPanel;
    MainPanel mainPanel;

    public Client(){
        super();
        setLocation(320,0);
        setSize(1600, 900);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        connectPanel = new ConnectPanel(this, connectPanelBackgroundImagePath);
        currentPanel = connectPanel;
        setContentPane(connectPanel);
        setVisible(true);
    }

    public void connect(final String name){
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
                    connectPanel.reset();
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

                mainPanel = new MainPanel();
                currentPanel = mainPanel;
                invalidate();
                setContentPane(mainPanel);
                validate();

                sendPacket(new Packet(0, name));

                while (true){
                    Packet req = null;
                    try{
                        req = (Packet)in.readObject();
                    } catch (IOException e){
                        Debug.log(1, "ServerThread: socket closed");
                        invalidate();
                        return;
                    } catch (ClassNotFoundException e){
                        Debug.log(1, "ServerThread: ClassNotFound");
                        invalidate();
                        return;
                    }
                    Debug.log(0, "Received packet " + req.status);
                    if (req == null){
                        Debug.log(1, "Packet is null????");
                        invalidate();
                        return;
                    }

                    boolean result = parse(req);
                    if (!result){
                        Debug.log(3, "Bad request: " + req.status + ": " + req.o);
                    }
                }

            }
        }.start();
    }

    public void sendPacket(final Packet p){
        new Thread(){
            public void run(){
                try{
                    out.writeObject(p);
                } catch (IOException e){
                    Debug.log(2, "Error Sending Packet");
                }
                Debug.log(0, "Sent packet");
            }
        }.start();
    }

    private boolean parse(Packet packet){
        int status = packet.status;
        Object o = packet.o;
        switch (status){
            case 0:
                // set board
                break;
            case 1:
                // choose username
                break;
            case 2:
                // Receive Chat msg
                break;
            case 3:
                // Change Floor View
                break;
            default:
                return false;
        }
        return true;
    }
}
