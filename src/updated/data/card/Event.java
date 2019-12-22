package updated.data.card;

import updated.Debug;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_4BYTE_ABGR;

public class Event implements Card{

    private int id;
    private String name;

    public Event(int id, String name) {
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

    public static final Event[] fullList = new Event[]{
            new Event(0, "Smoke"),
            new Event(1, "Closet Door"),
            new Event(2, "Skeletons"),
            new Event(3, "rorriM eht ni egamI"),
            new Event(4, "Night View"),
            new Event(5, "Revolving Wall"),
            new Event(6, "Something Slimy"),
            new Event(7, "Silence"),
            new Event(8, "Secret Passage"),
            new Event(9, "Mists from the Wall"),
            new Event(10, "Possession"),
            new Event(11, "Funeral"),
            new Event(12, "Creepy Crawlies"),
            new Event(13, "Locked Safe"),
            new Event(14, "The Voice"),
            new Event(15, "Image in the Mirror"),
            new Event(16, "A Moment of Hope"),
            new Event(17, "Hideous Shriek"),
            new Event(18, "Hanged Men"),
            new Event(19, "The Beckoning"),
            new Event(20, "Whoops!"),
            new Event(21, "Mystic Slide"),
            new Event(22, "What the...?"),
            new Event(23, "Footsteps"),
            new Event(24, "Disquieting Sounds"),
            new Event(25, "Webs"),
            new Event(26, "Drip... Drip... Drip..."),
            new Event(27, "Phone Call"),
            new Event(28, "Debris"),
            new Event(29, "It is Meant to be"),
            new Event(30, "Groundskeeper"),
            new Event(31, "Jonah's Turn"),
            new Event(32, "Lights out"),
            new Event(33, "Burning Man"),
            new Event(34, "The Lost One"),
            new Event(35, "Bloody Vision"),
            new Event(36, "The Walls"),
            new Event(37, "Rotten"),
            new Event(38, "Spider"),
            new Event(39, "Angry Being"),
            new Event(40, "Shrieking Wind"),
            new Event(41, "Secret Stairs"),
            new Event(42, "Creepy Puppet"),
            new Event(43, "Something Hidden"),
            new Event(44, "Grave Dirt")
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
