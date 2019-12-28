package util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TextFieldUtils {

    public static final String FONT = StringUtils.FONT;

    public static JTextField generateStandard(ActionListener e){
        JTextField textField = new JTextField();
        textField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.addActionListener(e);
        textField.setForeground(Color.black);
        return textField;
    }

    public static void setBounds(JPanel p, JTextField field, double x, double y, double width, double height){
        field.setBounds((int)(x * p.getWidth()), (int)(y * p.getHeight()), (int)(width * p.getWidth()), (int)(height * p.getHeight()));
        field.setFont(new Font(FONT, Font.PLAIN, (int)(height * 4 / 5 * p.getHeight())));
    }
}
