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
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch(IOException e){
            Debug.log(2, "Error creating input/output stream in IThread");
            invalidate();
        }
    }

    public void invalidate(){
        Debug.log(0, "invaliding IThread");
        b.getAndSet(false);
        try{
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e){
            Debug.log(2, "Error closing IThread");
        }
        server.removeIThread(this);
    }

    public void run(){
        while (b.get()){
            Request req = null;
            try{
                req = (Request)in.readObject();
            } catch (IOException e){
                Debug.log(1, "IO Exception while reading");
                invalidate();
                return;
            } catch (ClassNotFoundException e){
                Debug.log(1, "IOException while reading");
                invalidate();
                return;
            }
            if (req == null){
                Debug.log(1, "Request is null????");
                invalidate();
                return;
            }

            boolean result = Request.parse(req, this);
            if (!result){
                Debug.log(3, "Bad request: " + req.status + ": " + req.s);
            }
        }
    }

    public void sendBoard(){

    }
}
