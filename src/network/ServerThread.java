package network;

import network.Packet;
import network.Server;
import util.Debug;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;

public class ServerThread extends Thread{

    Server server;

    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;

    LinkedList<Packet> pendingIn;
    LinkedList<Packet> pendingOut;

    volatile boolean b;

    public ServerThread(Socket socket, Server s){
        this.socket = socket;
        this.server = s;
        b = true;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch(IOException e){
            Debug.log(2, "ServerThread: creating in/out");
            invalidate();
        }
    }

    public void invalidate(){
        Debug.log(0, "invalidating ServerThread");
        b = false;
        try{
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e){
            Debug.log(2, "ServerThread: closing socket");
        }
        server.removeIThread(this);
    }

    public void run(){
        while (b){
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

    public void sendPacket(final Packet p){

        new Thread(){
            public void run(){
                try{
                    out.writeObject(p);
                } catch (IOException e){
                    Debug.log(2, "Error Sending Packet");
                }
                Debug.log(0, "Sent Packet");
            }
        }.start();
    }

    private boolean parse(Packet packet){
        int status = packet.status;
        Object o = packet.o;
        switch (status){
            case 0:
                sendPacket(new Packet(0, null));//server.getBoard()));
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
