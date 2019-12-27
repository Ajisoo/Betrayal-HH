public class Request {

    /*
    Status meaning
    0   requesting entire game board
    1   Choosing username in s
    2   Receive chat message
    3   Changing floor view
    4
     */

    int status;
    String s;


    static boolean parse(Request r, IThread client){
        int status = r.status;
        String s = r.s;
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
