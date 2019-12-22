package updated;

import updated.data.card.Card;
import updated.data.card.Item;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Test extends JPanel {

    Image itemImage;

    public Test(){
        try{
            itemImage = ImageIO.read(new File("C:\\Users\\dave\\IdeaProjects\\Betrayal HH\\src\\updated\\data\\images\\items.jpg"));
        } catch (IOException e){
            e.printStackTrace();
        }
        Item.initialize("C:\\Users\\dave\\IdeaProjects\\Betrayal HH\\src\\updated\\data\\images\\items.jpg");
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Item.fullList[0].paint(g, 0, 0, Card.imageWidth, Card.imageHeight, true);
        Item.fullList[0].paint(g, Card.imageWidth, 0, Card.imageWidth, Card.imageHeight, false);
        int x = Card.imageWidth;
        g.setColor(Color.red);
        g.drawLine(x, 0,x,450);
        g.drawLine(x * 2, 0, x * 2, 450);
        g.drawLine(x * 3, 0, x * 3, 450);
    }

    public static void main(String[] args){

        JFrame frame = new JFrame();
        frame.setLocation(0, 0);
        frame.setSize(1920,1080);
        //frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Test panel = new Test();
        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}
