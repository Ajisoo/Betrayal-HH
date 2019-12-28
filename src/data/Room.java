package data;

import util.Debug;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Room {

    private final int id;
    private final String name;
    private final int roomNum;

    private Image image;
    private int rotation;

    public void rotate(boolean right){
        if (right){
            rotation++;
            if (rotation == 4) rotation = 0;
        }
        else{
            rotation--;
            if (rotation == -1) rotation = 3;
        }
    }

    public Room(int id, String name, int roomNum){
        this.id = id;
        this.name = name;
        this.roomNum = roomNum;
        rotation = 0;
    }

    public void paint(Graphics g, int x, int y, int width, int height, boolean faceUp){
        if (image == null) return;
        if (faceUp) {
            int sx = (id % 10) * roomWidth;
            int sy = (id / 10) * roomHeight;
            g.drawImage(image, x, y, x + width, y + height, sx, sy, sx + roomWidth, sy + roomHeight, null);
        }
        else {
            g.drawImage(image, x, y, x + width, y + height, roomWidth * 9, roomHeight * 6, roomWidth * 10, roomHeight * 7, null);
        }
    }

    public static final int roomWidth = 400;
    public static final int roomHeight = 400;

    public static final Room[] fullList = new Room[]{
            new Room(0, "Basement Landing", 1),
            new Room(1, "Catacombs", 1),
            new Room(2, "Chasm", 1),
            new Room(3, "Crypt", 1),
            new Room(4, "Furnace Room", 1),
            new Room(5, "Larder", 1),
            new Room(6, "Pentagram Chamber", 1),
            new Room(7, "Stairs From Basement", 1),
            new Room(8, "Wine Cellar", 1),
            new Room(0, "Entrance Hall", 2),
            new Room(1, "Foyer", 2),
            new Room(2, "Grand Staircase", 2),
            new Room(3, "Ballroom", 2),
            new Room(4, "Coal Chute", 2),
            new Room(5, "Dining Room", 2),
            new Room(6, "Gardens", 2),
            new Room(7, "Graveyard", 2),
            new Room(8, "Patio", 2),
            new Room(0, "Abandoned Room", 3),
            new Room(1, "Kitchen", 3),
            new Room(0, "Upper Landing", 4),
            new Room(1, "Balcony", 4),
            new Room(2, "Bedroom", 4),
            new Room(3, "Attic", 4),
            new Room(4, "Gallery", 4),
            new Room(5, "Master Bedroom", 4),
            new Room(6, "Tower", 4),
            new Room(7, "Underground Lake", 4),
            new Room(0, "Vault", 5),
            new Room(1, "Gymnasium", 5),
            new Room(2, "Operating Laboratory", 5),
            new Room(3, "Research Laboratory", 5),
            new Room(4, "Servant Quarters", 5),
            new Room(5, "Storeroom", 5),
            new Room(0, "Library", 6),
            new Room(1, "Bloody Room", 6),
            new Room(2, "Chapel", 6),
            new Room(3, "Charred Room", 6),
            new Room(4, "Collapsed Room", 6),
            new Room(5, "Conservatory", 6),
            new Room(0, "Statuary Corridor", 7),
            new Room(1, "Creaky Hallway", 7),
            new Room(2, "Dusty Hallway", 7),
            new Room(3, "Game Room", 7),
            new Room(4, "Junk Room", 7),
            new Room(5, "Mystic Elevator", 7),
            new Room(6, "Organ Room", 7)
    };

    public static void initialize(String[] roomPaths){
        for (int i = 0; i < fullList.length; i++){
            try{
                fullList[i].image = ImageIO.read(new File(roomPaths[fullList[i].roomNum - 1]));
            } catch (IOException e){
                Debug.log(1, "Can't load images from " + roomPaths);
            }
        }
    }
}
