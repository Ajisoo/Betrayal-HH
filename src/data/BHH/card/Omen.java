package data.BHH.card;

import util.Debug;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_4BYTE_ABGR;

public class Omen implements Card{

    private int id;
    private String name;

    public Omen(int id, String name) {
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

    public static final Omen[] fullList = new Omen[]{
            new Omen(0, "Bite"),
            new Omen(1, "Skull"),
            new Omen(2, "Mask"),
            new Omen(3, "Spirit Board"),
            new Omen(4, "Medallion"),
            new Omen(5, "Holy Symbol"),
            new Omen(6, "Crystal Ball"),
            new Omen(7, "Madman"),
            new Omen(8, "Spear"),
            new Omen(9, "Girl"),
            new Omen(10, "Ring"),
            new Omen(11, "Dog"),
            new Omen(12, "Book")
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
                        System.out.println(i + " " + j + " " + k);
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
