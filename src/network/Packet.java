package network;

import java.io.Serializable;

public class Packet implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;

    /*
    Status meaning
    0   Received username (null -> no change), send data
     */

    int status;
    Object o;

    public Packet(int status, Object o){
        this.status = status;
        this.o = o;
    }
}
