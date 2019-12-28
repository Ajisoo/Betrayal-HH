package ui;

import network.Client;
import util.Debug;
import util.StringUtils;
import util.TextFieldUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;

public class ConnectPanel extends JPanel implements ComponentListener {

    final Client c;
    Image image;

    JTextField nameField;
    boolean connecting;
    Timer t;
    int dots;

    public ConnectPanel(final Client c, String path){
        this.c = c;
        try{
            image = ImageIO.read(new File(path));
        } catch (IOException e){
            Debug.log(1, "Can't load image");
        }
        setLayout(null);
        addComponentListener(this);

        nameField = TextFieldUtils.generateStandard(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                String text = ((JTextField)(e.getSource())).getText();
                if (text == null) return;
                if (text.equals("")) return;
                if (text.indexOf(' ') != -1) return;
                if (text.length() > 12) return;
                dots = 1;
                t.start();
                nameField.setVisible(false);
                connecting = true;
                repaint();
                c.connect(text);
            }
        });
        TextFieldUtils.setBounds(this, nameField, 0.4, 0.5 - 0.025, 0.2, 0.05);
        add(nameField);
        connecting = false;
        t = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dots++;
                if (dots == 4) dots = 1;
                repaint();
                Debug.log(1, "" + dots);
            }
        });
    }

    public void reset(){
        nameField.setText("");
        nameField.setVisible(true);
        connecting = false;
        t.stop();
        repaint();
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if (image != null) g.drawImage(image,0,0,getWidth(),getHeight(), null);
        if (connecting){
            String s = "Connecting.";
            if (dots == 2) s = "Connecting..";
            if (dots == 3) s = "Connecting...";
            StringUtils.drawCenteredBorderedString(this, g, Client.ip + ":" + Client.port, Color.white, 0.5, 0.3, 0.1);
            StringUtils.drawCenteredBorderedString(this, g, s, Color.white, 0.5, 0.35, 0.05);
        }
        else {
            StringUtils.drawCenteredBorderedString(this, g, "Enter Username", Color.white, 0.5, 0.3, 0.1);
            StringUtils.drawCenteredBorderedString(this, g, "(max 12 chars, no spaces)", Color.white, 0.5, 0.35, 0.05);
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        invalidate();
        TextFieldUtils.setBounds(this, nameField, 0.4, 0.5 - 0.025, 0.2, 0.05);
        validate();
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
