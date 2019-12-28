package network;

import network.Server;
import util.Debug;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class IThread extends Thread{

    Server server;
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;

    AtomicBoolean b;

    public IThread(Socket socket, Server s){
        this.socket = socket;
        this.server = s;
        b = new AtomicBoolean();
        b.getAndSet(true);
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch(IOException e){
            Debug.log(2, "IThread: creating in/out");
            invalidate();
        }
    }

    public void invalidate(){
        Debug.log(0, "invalidating IThread");
        b.getAndSet(false);
        try{
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e){
            Debug.log(2, "IThread: closing socket");
        }
        server.removeIThread(this);
    }

    public void run(){
        while (b.get()){
            Packet req = null;
            try{
                req = (Packet)in.readObject();
            } catch (IOException e){
                Debug.log(1, "IThread: socket closed");
                invalidate();
                return;
            } catch (ClassNotFoundException e){
                Debug.log(1, "IThread: ClassNotFound");
                invalidate();
                return;
            }
            if (req == null){
                Debug.log(1, "Packet is null????");
                invalidate();
                return;
            }

            boolean result = Packet.parse(req, this);
            if (!result){
                Debug.log(3, "Bad request: " + req.status + ": " + req.o);
            }
        }
    }

    public void sendBoard(){
            try{
                out.writeObject(new Packet());
            } catch (IOException e){
                Debug.log(1, "IThread: sendBoard");
            }
    }
}
