package util;

import javax.swing.*;
import java.awt.*;

public class StringUtils {

    public static final String FONT = "BT BARNUM";

    public static void drawBorderedString(JPanel p, Graphics g, String s, Color c, double x, double y, double size){
        g.setFont(new Font(FONT, Font.PLAIN, (int)(size * p.getHeight())));
        g.setColor(Color.black);
        g.drawString(s, (int)(x * p.getWidth()) + 1, (int)(y * p.getHeight()));
        g.drawString(s, (int)(x * p.getWidth()) - 1, (int)(y * p.getHeight()));
        g.drawString(s, (int)(x * p.getWidth()), (int)(y * p.getHeight()) + 1);
        g.drawString(s, (int)(x * p.getWidth()), (int)(y * p.getHeight()) - 1);
        g.setColor(c);
        g.drawString(s, (int)(x * p.getWidth()), (int)(y * p.getHeight()));
    }

    public static void drawScaledString(JPanel p, Graphics g, String s, Color c, double x, double y, double size){
        g.setColor(c);
        g.setFont(new Font(FONT, Font.PLAIN, (int)(size * p.getHeight())));
        g.drawString(s, (int)(x * p.getWidth()), (int)(y * p.getHeight()));
    }

    public static void drawCenteredBorderedString(JPanel p, Graphics g, String s, Color c, double x, double y, double size){
        Font f = new Font(FONT, Font.PLAIN, (int)(size * p.getHeight()));
        g.setFont(f);
        double length = g.getFontMetrics(f).getStringBounds(s, g).getWidth();
        x =  x - length/2 / p.getWidth();
        System.out.println(x);
        g.setColor(Color.black);
        g.drawString(s, (int)(x * p.getWidth()) + 1, (int)(y * p.getHeight()));
        g.drawString(s, (int)(x * p.getWidth()) - 1, (int)(y * p.getHeight()));
        g.drawString(s, (int)(x * p.getWidth()), (int)(y * p.getHeight()) + 1);
        g.drawString(s, (int)(x * p.getWidth()), (int)(y * p.getHeight()) - 1);
        g.setColor(c);
        g.drawString(s, (int)(x * p.getWidth()), (int)(y * p.getHeight()));
    }

    public static void drawCenteredScaledString(JPanel p, Graphics g, String s, Color c, double x, double y, double size){
        Font f = new Font(FONT, Font.PLAIN, (int)(size * p.getHeight()));
        g.setFont(f);
        double length = g.getFontMetrics(f).getStringBounds(s, g).getWidth();
        x =  x - length/2 / p.getWidth();
        g.setColor(c);
        g.drawString(s, (int)(x * p.getWidth()), (int)(y * p.getHeight()));
    }
}
