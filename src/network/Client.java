package network;

import ui.ConnectPanel;
import util.Debug;

import javax.swing.*;

public class Client extends JFrame {

    public static final String ip = "localhost";
    public static final int port = 7778;
    public static final String connectPanelBackgroundImagePath = "C:\\Users\\dave\\IdeaProjects\\Betrayal HH\\connect.jpg";

    public static void main(String[] args){
        Client c = new Client(ip, port);
        c.setVisible(true);
    }

    public Client(String ip, int port){
        super();
        setLocation(0,0);
        setSize(1600, 900);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ConnectPanel connectPanel = new ConnectPanel(this, connectPanelBackgroundImagePath);
        setContentPane(connectPanel);
        connectPanel.nameField.setVisible(true);
    }

    String name;

    public void connect(String name){
        this.name = name;
        Debug.log(0, "Connecting with name: " + name);
    }
}
