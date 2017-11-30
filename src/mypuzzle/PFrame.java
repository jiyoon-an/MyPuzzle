package mypuzzle;

import java.awt.BorderLayout;
import java.awt.Image;
import javax.swing.*;


public class PFrame extends JFrame{
    
    public PFrame() {
        ImageIcon icon = new ImageIcon("image/puzzle_bg.png");
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(800, 600, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);
        JLabel bgImage = new JLabel(icon);
        bgImage.setLayout(new BorderLayout());
        this.setContentPane(bgImage);
        this.setTitle("My Puzzle!");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}
