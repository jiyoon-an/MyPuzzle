package mypuzzle;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class FrmChooseImage extends PFrame implements ActionListener, MouseListener{
    private JButton btnDB, btnLocal, btnExit;
    private JLabel lblDB, lblLocal, lblExit;
    private String filepath;
    private String filename;
    private String userName;

    public FrmChooseImage(String userName) {
        this.userName = userName;
        JPanel pnlMain = new JPanel(new GridLayout(2,2));
        //only one part is used for buttons
        JPanel pnlChooseImage = new JPanel(new GridLayout(3,1));
        //for btnDB, btnLocal, btnExit
        JPanel pnlDB = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pnlLocal = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pnlExit = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        //setting background color trasparent for background image
        pnlMain.setOpaque(false);
        pnlChooseImage.setOpaque(false);
        pnlDB.setOpaque(false);
        pnlLocal.setOpaque(false);
        pnlExit.setOpaque(false);
        
        pnlChooseImage.setBorder(javax.swing.BorderFactory.createEmptyBorder(30, 20, 50, 0));
        
        btnDB = new JButton("Choose From Game");
        btnLocal = new JButton("Choose From My Desktop");
        btnExit = new JButton("Exit");
        
        //alignment to left
        btnDB.setHorizontalAlignment(SwingConstants.LEFT);
        btnLocal.setHorizontalAlignment(SwingConstants.LEFT);
        btnExit.setHorizontalAlignment(SwingConstants.LEFT);
        
        //set the font to buttons
        btnDB.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        btnLocal.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        btnExit.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        
        //set the text color to buttons
        btnDB.setForeground(new Color(0, 0, 102));
        btnLocal.setForeground(new Color(0, 0, 102));
        btnExit.setForeground(new Color(0, 0, 102));
        
        //set the backgrount color to transparent
        btnDB.setContentAreaFilled(false);
        btnLocal.setContentAreaFilled(false);
        btnExit.setContentAreaFilled(false);
        
        //set the backgrount color to transparent
        btnDB.setOpaque(false);
        btnLocal.setOpaque(false);
        btnExit.setOpaque(false);
        
        //remove the borders
        btnDB.setBorder(BorderFactory.createEmptyBorder());
        btnLocal.setBorder(BorderFactory.createEmptyBorder());
        btnExit.setBorder(BorderFactory.createEmptyBorder());
        
        lblDB = new JLabel(new ImageIcon("image/goto.png"));
        lblLocal = new JLabel(new ImageIcon("image/goto.png"));
        lblExit = new JLabel(new ImageIcon("image/goto.png"));
        
        pnlDB.add(lblDB);
        pnlDB.add(btnDB);
        pnlLocal.add(lblLocal);
        pnlLocal.add(btnLocal);
        pnlExit.add(lblExit);
        pnlExit.add(btnExit);        
        
        pnlChooseImage.add(pnlDB);
        pnlChooseImage.add(pnlLocal);
        pnlChooseImage.add(pnlExit);
        
        btnDB.addActionListener(this);
        btnLocal.addActionListener(this);
        btnExit.addActionListener(this);
        btnDB.addMouseListener(this);
        btnLocal.addMouseListener(this);
        btnExit.addMouseListener(this);
        
        pnlMain.add(new JLabel());
        pnlMain.add(new JLabel());
        pnlMain.add(new JLabel());
        pnlMain.add(pnlChooseImage);
        
        //PuzzleDAO awin = new PuzzleDAO();
        
        this.add(pnlMain);
        
        this.pack();
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnDB) { // choose image from db. go to thumbnail of db images
            this.setVisible(false);
            new FrmImageThumbnail(userName).setVisible(true);
        } else if(e.getSource() == btnLocal) { //choose image from local
             openImage();
            if(!filepath.equals("")) {
                 try {
                     File img = new File(filepath);
                     BufferedImage bImg = ImageIO.read(img);
                     FrmSlideGame frmGame = new FrmSlideGame(bImg, filepath, filename, "local", userName, "local",1);
                     this.setVisible(false);
                     frmGame.setVisible(true);
                     int level = frmGame.setLevel();
                     if(level == 0) {
                         frmGame.setVisible(false);
                         this.setVisible(true);
                     }
                 } catch (IOException ex) {
                     Logger.getLogger(FrmChooseImage.class.getName()).log(Level.SEVERE, null, ex);
                 }
                   
            }
        } else if(e.getSource() == btnExit){
            System.exit(0);
        }
    }
    
    private String openImage() { //choose image from local 
        JFileChooser filechooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp");
        filechooser.setFileFilter(filter);

        int choice = filechooser.showOpenDialog(this);

        if (choice == JFileChooser.APPROVE_OPTION) {
            try {
                filepath = filechooser.getSelectedFile().getPath();
                filename = filechooser.getSelectedFile().getName();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if(choice == JFileChooser.CANCEL_OPTION) {
            filepath = "";
        }
        return filepath;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        //to check whether mouse is on the buttons or not
        if(e.getSource() == btnDB) {
            btnDB.setForeground(new Color(240, 87, 30));
            lblDB.setIcon(new ImageIcon("image/goto_selected.png"));
        } else if(e.getSource() == btnLocal) {
            btnLocal.setForeground(new Color(240, 87, 30));
            lblLocal.setIcon(new ImageIcon("image/goto_selected.png"));
        } else if(e.getSource() == btnExit) {
            btnExit.setForeground(new Color(240, 87, 30));
            lblExit.setIcon(new ImageIcon("image/goto_selected.png"));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //to check whether mouse is on the buttons or not
        if(e.getSource() == btnDB) {
            btnDB.setForeground(new Color(0,0,102));
            lblDB.setIcon(new ImageIcon("image/goto.png"));
        } else if(e.getSource() == btnLocal) {
            btnLocal.setForeground(new Color(0,0,102));
            lblLocal.setIcon(new ImageIcon("image/goto.png"));
        } else if(e.getSource() == btnExit) {
            btnExit.setForeground(new Color(0,0,102));
            lblExit.setIcon(new ImageIcon("image/goto.png"));
        }
    }    
}
