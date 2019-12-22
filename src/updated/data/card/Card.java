package updated.data.card;

import java.awt.*;

public interface Card {

    void paint(Graphics g, int x, int y, int width, int height, boolean faceUp);

    int imageWidth = 450;
    int imageHeight = 850;
}
