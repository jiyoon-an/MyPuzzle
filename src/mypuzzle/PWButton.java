/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mypuzzle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;


public class PWButton extends JButton{
    public PWButton(String message) {
        this.setText(message);
        this.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        this.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        
        
    }
}
