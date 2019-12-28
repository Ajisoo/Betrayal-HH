package network;

import java.io.Serializable;

public class Packet implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;

    /*
    Status meaning
    0   requesting entire game board
    1   Choosing username in s
    2   Receive chat message
    3   Changing floor view
    4
     */

    int status;
    Object o;


    static boolean parse(Packet r, IThread client){
        int status = r.status;
        Object o = r.o;
        switch (status){
            case 0:
                client.sendBoard();
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
