package mypuzzle;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

public class FrmImageThumbnail extends PFrame implements ActionListener, MouseListener {
    private int imageCount;
    private JButton[] btnImage;
    private ArrayList arrImages;
    private JButton btnBack, btnExit;
    private String userName;
    public FrmImageThumbnail(String userName) {
        this.userName = userName;
        JPanel pnlChooseImage = new JPanel(new BorderLayout());
        JPanel pnlBack = new JPanel(new BorderLayout());
        JPanel pnlMain = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel pnlThumbnailMain = new JPanel(new GridLayout(countImage(), 1));
        pnlChooseImage.setOpaque(false);
        pnlBack.setOpaque(false);
        pnlMain.setOpaque(false);
        pnlThumbnailMain.setSize(685, 600);
        pnlThumbnailMain.setOpaque(false);
        JPanel pnlLine = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        //display 3 images in one row
        for(int i=0; i<arrImages.size();i++) {
            if((i%3)==0) {
                pnlLine = new JPanel(new FlowLayout(FlowLayout.LEFT));
                pnlLine.setOpaque(false);
                pnlThumbnailMain.add(pnlLine);
            }
            HashMap mapImg = (HashMap)arrImages.get(i);
            try {
                InputStream is =  (InputStream)mapImg.get("imageFile");
                BufferedImage bImg = ImageIO.read(is);
                ImageIcon icon = new ImageIcon(bImg);
                Image img = icon.getImage();
                Image newImg = img.getScaledInstance(210, 160, java.awt.Image.SCALE_SMOOTH);
                icon = new ImageIcon(newImg);
                
                btnImage[i] = new JButton(icon);
                btnImage[i].setPreferredSize(new Dimension(210,160));
                btnImage[i].setBorder(javax.swing.BorderFactory.createEmptyBorder());
                btnImage[i].setOpaque(false);
                btnImage[i].setContentAreaFilled(false);
                pnlLine.add(btnImage[i]);
                btnImage[i].addActionListener(this);
                btnImage[i].addMouseListener(this);
            } catch(IOException e) {
                System.out.println("Fail to convert image data to InputStream type");
                e.printStackTrace();
            }
            
        }
        btnBack = new JButton("BACK");
        btnBack.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        btnBack.setForeground(new Color(0, 0, 102));
        btnBack.setContentAreaFilled(false);
        btnBack.setFocusPainted(false);
        btnBack.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnBack.addActionListener(this);
        btnBack.addMouseListener(this);
        JLabel lblTitle = new JLabel("My Puzzle! - Choose Image", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        lblTitle.setForeground(new Color(0, 0, 102));
        btnExit = new JButton("Exit");
        btnExit.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        btnExit.setForeground(new Color(0, 0, 102));
        btnExit.setContentAreaFilled(false);
        btnExit.setFocusPainted(false);
        btnExit.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnExit.addActionListener(this);
        btnExit.addMouseListener(this);
        pnlBack.add(btnBack, BorderLayout.WEST);
        pnlBack.add(lblTitle , BorderLayout.CENTER);
        pnlBack.add(btnExit, BorderLayout.EAST);
        pnlChooseImage.add(pnlBack, BorderLayout.NORTH);
        
        pnlMain.add(pnlThumbnailMain);
        JScrollPane scrPane = new JScrollPane(pnlMain);
        scrPane.setOpaque(false);
        scrPane.getViewport().setOpaque(false);
        scrPane.setBorder(BorderFactory.createLineBorder(Color.white, 0));
        pnlChooseImage.add(scrPane, BorderLayout.CENTER);
        this.add(pnlChooseImage);
        this.setSize(700, 600);
        this.setLocationRelativeTo(null);
    }
    
    /**
     * This method calls the number of images from db
     * @return imageCount
     */
    private int countImage() {
        PuzzleDAO dao = new PuzzleDAO();
        arrImages = dao.getImages("db", 0);
        
        imageCount = arrImages.size();
        btnImage = new JButton[imageCount];
        imageCount = (int)Math.ceil((double)imageCount/3);
        return imageCount;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=0; i<btnImage.length; i++) {
            //when one image is clicked, the information about user and image will be carry to next frame
            if(e.getSource() == btnImage[i]) {
                try {
                    HashMap map = (HashMap)arrImages.get(i);
                    int imageId = Integer.parseInt(map.get("imageId").toString());
                    InputStream is =  (InputStream)(map.get("imageFile"));
                    BufferedImage bImg = ImageIO.read(is);
                    
                    FrmSlideGame frmGame = new FrmSlideGame(bImg, map.get("imagePath").toString(), map.get("imageName").toString(), "db", userName, "db", imageId);
                    this.setVisible(false);
                    frmGame.setVisible(true);

                    int level = frmGame.setLevel();
                    if(level == 0) {
                        frmGame.setVisible(false);
                        this.setVisible(true);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(FrmImageThumbnail.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
        if(e.getSource()==btnBack) {
            this.dispose();
            FrmChooseImage awin = new FrmChooseImage(userName);
            awin.setVisible(true);
        } else if (e.getSource() == btnExit) {
            System.exit(0);
        }
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
        if(e.getSource() == btnBack) {
            btnBack.setForeground(new Color(247, 80, 30));
        } else if(e.getSource() == btnExit) {
            btnExit.setForeground(new Color(247, 80, 30));
        }
        for(int i=0; i<btnImage.length; i++) {
            if(e.getSource() == btnImage[i]) {
                btnImage[i].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 200), 3));
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //to check whether mouse is on the buttons or not
        if(e.getSource() == btnBack) {
            btnBack.setForeground(new Color(0, 0, 102));
        } else if(e.getSource() == btnExit) {
            btnExit.setForeground(new Color(0, 0, 102));
        }
        for(int i=0; i<btnImage.length; i++) {
            if(e.getSource() == btnImage[i]) {
                btnImage[i].setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 0));
            }
        }
    }
}
