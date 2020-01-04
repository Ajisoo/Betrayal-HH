package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class MainPanel extends JPanel implements ComponentListener {

    public MainPanel(){

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        invalidate();
        validate();
    }

    public void componentMoved(ComponentEvent e) {}
    public void componentShown(ComponentEvent e) {}
    public void componentHidden(ComponentEvent e) {}
}
