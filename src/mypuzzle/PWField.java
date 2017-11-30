package mypuzzle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;


public class PWField extends JPasswordField{
    
    public PWField() {
        this.setOpaque(false);
        this.setText("");
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setForeground(Color.DARK_GRAY);
        this.setFont(new Font("Calibri", Font.BOLD, 45));
        this.setBorder(javax.swing.BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
    }
    
}
