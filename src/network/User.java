package network;

import util.Debug;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class User {

    Server server;
    Socket socket;
    final ObjectInputStream inStream;
    final ObjectOutputStream outStream;
    final LinkedList<Packet> inQueue;
    final LinkedList<Packet> outQueue;

    volatile boolean valid;

    Color color;
    String name;

    public User(Socket socket, Server server, Color c) {
        this.socket = socket;
        this.server = server;
        this.color = c;

        inQueue = new LinkedList<Packet>();
        outQueue = new LinkedList<Packet>();
        outStream = getOutStream(socket);
        inStream = getInStream(socket);
        valid = true;

        if (outStream == null || inStream == null) {
            disconnect();
            return;
        }

        runInThread();
        runOutThread();
        runProcessThread();
    }

    public synchronized void disconnect(){
        valid = false;
        try {
            if (outStream != null) outStream.close();
            if (inStream != null) inStream.close();
            if (socket != null) socket.close();
        } catch (IOException e){
            Debug.log(2, "User: Disconnect");
        }
        server.removeUser(this);
    }

    private void processPacket(Packet p){
        int status = p.status;
        Object o = p.o;
        if (status == 0){
            //TODO
        }
    }

    public void sendPacket(int status, Object o){
        synchronized(outQueue){
            outQueue.addLast(new Packet(status, o));
        }
    }

    private void runProcessThread() {
        new Thread() {
            public void run() {
                while (valid) {
                    yield();
                    Packet p;
                    synchronized(inQueue){
                        try{
                            p = inQueue.removeFirst();
                        } catch(NoSuchElementException e){
                            continue;
                        }
                    }
                    processPacket(p);
                }
            }
        }.start();
    }

    private void runInThread() {
        new Thread() {
            public void run() {
                while (valid) {
                    try {
                        Packet p = (Packet) inStream.readObject();
                        if (p == null) {
                            Debug.log(2, "User: packet is null");
                            disconnect();
                        }
                        synchronized (inQueue) {
                            inQueue.addLast(p);
                        }
                    } catch (IOException e) {
                        Debug.log(2, "User: ReadThread");
                        disconnect();
                    } catch (ClassNotFoundException e) {
                        Debug.log(2, "User: Class not found");
                        disconnect();
                    }
                }
            }
        }.start();
    }

    private void runOutThread(){
        new Thread(){
            public void run(){
                while (valid){
                    yield();
                    Packet out;
                    synchronized(outQueue){
                        try{
                            out = outQueue.removeFirst();
                        } catch(NoSuchElementException e){
                            continue;
                        }
                    }
                    try{
                        outStream.writeObject(out);
                    } catch (IOException e){
                        Debug.log(2, "User: WriteThread");
                        disconnect();
                    }
                }
            }
        }.start();

    }
    private ObjectOutputStream getOutStream(Socket socket){
        try{
            return new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e){
            Debug.log(2, "User: ObjectInputStream");
            return null;
        }
    }

    private ObjectInputStream getInStream(Socket socket){
        try{
            return new ObjectInputStream(socket.getInputStream());
        } catch (IOException e){
            Debug.log(2, "User: ObjectInputStream");
            return null;
        }
    }

}
