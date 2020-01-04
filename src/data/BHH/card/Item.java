package data.BHH.card;

import util.Debug;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_4BYTE_ABGR;

public class Item implements Card {

    private int id;
    private String name;

    public Item(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public void paint(Graphics g, int x, int y, int width, int height, boolean faceUp) {
        if (image == null) return;
        if (faceUp) {
            int sx = (id % 10) * imageWidth;
            int sy = (id / 10) * imageHeight;
            g.drawImage(image, x, y, x + width, y + height, sx, sy, sx + imageWidth, sy + imageHeight, null);
        }
        else {
            g.drawImage(image, x, y, x + width, y + height, imageWidth * 9, imageHeight * 6, imageWidth * 10, imageHeight * 7, null);
        }
    }

    public static Image image;

    public static final Item[] fullList = new Item[]{
            new Item(0, "Angel Feather"),
            new Item(1, "Armor"),
            new Item(2, "Bottle"),
            new Item(3, "Adrenaline Shot"),
            new Item(4, "Sacrificial Dagger"),
            new Item(5, "Blood Dagger"),
            new Item(6, "Candle"),
            new Item(7, "Dynamite"),
            new Item(8, "Puzzle Box"),
            new Item(9, "Axe"),
            new Item(10, "Revolver"),
            new Item(11, "Dark Dice"),
            new Item(12, "Idol"),
            new Item(13, "Bell"),
            new Item(14, "Pickpocket's Gloves"),
            new Item(15, "Smelling Salts"),
            new Item(16, "Music Box"),
            new Item(17, "Healing Salve"),
            new Item(18, "Amulet of the Ages"),
            new Item(19, "Lucky Stone"),
            new Item(20, "Rabbit's Foot"),
            new Item(21, "Medical Kit")
    };

    public static void initialize(String path) {
        try{
            image = ImageIO.read(new File(path));
        } catch (IOException e){
            Debug.log(1, "Can't load images from " + path);
            return;
        }
        BufferedImage tempImage = new BufferedImage(image.getWidth(null), image.getHeight(null), TYPE_4BYTE_ABGR);
        tempImage.getGraphics().drawImage(image, 0, 0 , null);
        int cornerSize = 50;
        for (int i = 0; i < cornerSize; i++) {
            for (int j = 0; j < cornerSize; j++) {
                if ((i - cornerSize) * (i - cornerSize) + (j - cornerSize) * (j - cornerSize) > cornerSize * cornerSize) {
                    for (int k = 0; k < fullList.length; k++) {
                        tempImage.setRGB((k % 10) * imageWidth + i, (k / 10) * imageHeight + j,  0x00ffffff & tempImage.getRGB((k % 10) * imageWidth + i, (k / 10) * imageHeight + j));
                        tempImage.setRGB((k % 10) * imageWidth + imageWidth - i - 1, (k / 10) * imageHeight + j, 0x00ffffff & tempImage.getRGB((k % 10) * imageWidth + imageWidth - i - 1, (k / 10) * imageHeight + j));
                        tempImage.setRGB((k % 10) * imageWidth + i, (k / 10) * imageHeight + imageHeight - j - 1, 0x00ffffff & tempImage.getRGB((k % 10) * imageWidth + i, (k / 10) * imageHeight + imageHeight - j - 1));
                        tempImage.setRGB((k % 10) * imageWidth + imageWidth - i - 1, (k / 10) * imageHeight + imageHeight - j - 1, 0x00ffffff & tempImage.getRGB((k % 10) * imageWidth + imageWidth - i - 1, (k / 10) * imageHeight + imageHeight - j - 1));
                    }
                    tempImage.setRGB(9 * imageWidth + i, 6 * imageHeight + j,  0x00ffffff & tempImage.getRGB(9 * imageWidth + i, 6 * imageHeight + j));
                    tempImage.setRGB(9 * imageWidth + imageWidth - i - 1, 6 * imageHeight + j, 0x00ffffff & tempImage.getRGB(9 * imageWidth + imageWidth - i - 1, 6 * imageHeight + j));
                    tempImage.setRGB(9 * imageWidth + i, 6 * imageHeight + imageHeight - j - 1, 0x00ffffff & tempImage.getRGB(9 * imageWidth + i, 6 * imageHeight + imageHeight - j - 1));
                    tempImage.setRGB(9 * imageWidth + imageWidth - i - 1, 6 * imageHeight + imageHeight - j - 1, 0x00ffffff & tempImage.getRGB(9 * imageWidth + imageWidth - i - 1, 6 * imageHeight + imageHeight - j - 1));
                }
            }
        }
        image = tempImage;
    }
}
