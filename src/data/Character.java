package data;

import util.Debug;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_4BYTE_ABGR;

public class Character {

    private int index;
    private String[] characteristics; // name, age, height, hobbies, birthday

    private int str_i;
    private int spd_i;
    private int san_i;
    private int knw_i;

    private int[] str;
    private int[] spd;
    private int[] san;
    private int[] knw;

    private static Image image;

    public static final Character[] fullList = {
       // new Character()
    };

    public static void initialize(String path){
        try{
            image = ImageIO.read(new File(path));
        } catch (IOException e){
            Debug.log(1, "Can't load images from " + path);
            return;
        }
        int width = 0;
        int height = 0;
        BufferedImage tempImage = new BufferedImage(image.getWidth(null), image.getHeight(null), TYPE_4BYTE_ABGR);
        tempImage.getGraphics().drawImage(image, 0, 0 , null);
        int cornerSize = 50;
        //tempImage.setRGB(9 * imageWidth + imageWidth - i - 1, 6 * imageHeight + imageHeight - j - 1, 0x00ffffff & tempImage.getRGB(9 * imageWidth + imageWidth - i - 1, 6 * imageHeight + imageHeight - j - 1));
        image = tempImage;
    }

}
