package updated;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Debug {

    private static FileWriter fileWriter;
    private static BufferedWriter bufferedWriter;

    public static boolean initialize(String fileLocation){
        try{
            fileWriter = new FileWriter(fileLocation, true);
            bufferedWriter = new BufferedWriter(fileWriter);

            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                public void run() {
                    try{
                        bufferedWriter.close();
                        fileWriter.close();
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }));
        } catch(IOException e){
            return false;
        }
        return true;
    }

    public static boolean log(int status, String message){
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();
        try{
            bufferedWriter.write(formatter.format(date) + " \t" + status + " \t" + message + System.getProperty( "line.separator" ));
        } catch (IOException e){
            return false;
        }
        return true;
    }

}
