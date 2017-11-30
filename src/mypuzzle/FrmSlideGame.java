package mypuzzle;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

public class FrmSlideGame extends JFrame implements ItemListener, ActionListener, MouseListener {
    //to check the game is done
    private boolean done=false;
    //difficulty of current game
    int lvl;
    //the number of user click
    private int stepCounter = 0;
    //the number of pieces of image. at the first, it is setted as 9
    private int pieces = 9;
    //the lines of image. At the first, it is setted as 3;
    private int line = 3;
    private int random;
    //image's width, height for making pieces
    private int width, height;
    //panels for components
    private JPanel pnlGameBoard, pnlMain, pnlSub;
    //Combobox for choosing the difficulty
    private JComboBox<String> combo;
    private ImageIcon icon;
    private Image img;
    private BufferedImage[] subImage;
    private JButton[] btnImage;
    private JLabel firstImage;
    private String[] order;
    private Icon tempimage;
    private int tempbutton, tempindex;
    private JLabel lblTimer, lblStep, lblDifficulty;
    private ThreadTimer timerThread;
    private String imgFrom;
    private String userName;
    private String from;
    private JButton btnBack, btnExit;
    private int imageId;
    private int totalTime=0;
    private String imgPath, imgName;
    private BufferedImage imageFile;
    
    public FrmSlideGame(BufferedImage imageFile, String imgPath, String imgName, String imgFrom, String userName, String from, int imageId) {
        this.imageFile = imageFile;
        this.imgPath = imgPath;
        this.imgName = imgName;
        this.from = from;
        this.userName = userName;
        this.imgFrom = imgFrom;
        this.imageId = imageId;
        JPanel pnlGame = new JPanel(new BorderLayout());
        JPanel pnlBack = new JPanel(new BorderLayout());
        pnlMain = new JPanel(new BorderLayout());
        pnlGameBoard = new JPanel();
        pnlSub = new JPanel(new BorderLayout());
        
        pnlGame.setOpaque(false);
        pnlBack.setOpaque(false);
        pnlMain.setOpaque(false);
        pnlGameBoard.setOpaque(false);
        pnlSub.setOpaque(false);
                
        firstImage = new JLabel();
        
        readImage();
        //read image which is carried from previous page
        makePnlBoard();
        //set the image to panel
        makePnlSub();
        //make pieces 
        btnBack = new JButton("BACK");
        btnBack.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        btnBack.setForeground(new Color(0, 0, 102));
        btnBack.setOpaque(false);
        btnBack.setContentAreaFilled(false);
        btnBack.setFocusPainted(false);
        btnBack.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnBack.addActionListener(this);
        btnBack.addMouseListener(this);
        JLabel lblTitle = new JLabel("My Puzzle! - Play Game", SwingConstants.CENTER);
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
        
        pnlGame.add(pnlBack, BorderLayout.NORTH);
        pnlGame.add(pnlMain, BorderLayout.CENTER);
        
        pnlMain.add(pnlGameBoard, BorderLayout.CENTER);
        pnlMain.add(pnlSub, BorderLayout.EAST);
        this.add(pnlGame);
        makePieces();
        this.setSize(1000, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * 
     * @return  lvl
     */
    public int getLevel() {
        return lvl;
    }
    
    /**
     * before playing the game, the step to choose the difficulty
     * @return level;
     */
    public int setLevel() {
        String[] difficulties = new String[10];
        for(int i=0; i<difficulties.length; i++){
            difficulties[i] = (i + 3) + " x " + (i + 3);
        }
        combo = new JComboBox<>(difficulties);
        combo.addItemListener(this);
        
        String[] options = { "OK", "Cancel" };
        String title = "Choose Difficulty";
        
        int selection = JOptionPane.showOptionDialog(null, combo, title,
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
            options, options[0]);
        
        int level = 3;
        if(selection == 0) {
            level = combo.getSelectedIndex()+3;
            lblDifficulty.setText(level + " x " + level);
            sufflePieces();
        } else if(selection == 1){
            level = 0;
        }
        return level;
    }
    
    /**
     * check where image is from.
     * if it is from db, load the image data which is selected from previous page
     * if it is from local, convert to imageIcon type directly
     */
    private void readImage() {
        try{
            if(imgFrom.equals("db")) {
                PuzzleDAO dao = new PuzzleDAO();
                HashMap map = dao.getSelectedImage(imageId);
                InputStream is =  (InputStream)(map.get("imageFile"));
                imageFile = ImageIO.read(is);
            }
            width = imageFile.getWidth();
            height = imageFile.getHeight();
        } catch(Exception e){
            e.printStackTrace();
        }
        icon = new ImageIcon(imgPath);
        img = icon.getImage();
    }
    
    /**
     * Set image to specific size
     * and store the images is not divided into pieces yet.
     */
    private void makePnlBoard() {
        Image newImg = img.getScaledInstance(800, 550, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);
        JLabel lblImage = new JLabel(icon);
        firstImage = new JLabel(icon);
        pnlGameBoard.add(lblImage);
    }
    
    /**
     * In this method, submenus are displayed.
     * preview of puzzle, level, time and step
     */
    private void makePnlSub() {
        JPanel pnlPreview = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel pnlInformation = new JPanel(new GridLayout(3,1));
        JPanel pnlDifficulty = new JPanel(new BorderLayout());
        JPanel pnlTime = new JPanel(new BorderLayout());
        JPanel pnlStep = new JPanel(new BorderLayout());
        
        pnlPreview.setOpaque(false);
        pnlInformation.setOpaque(false);
        pnlDifficulty.setOpaque(false);
        pnlTime.setOpaque(false);
        pnlStep.setOpaque(false);
        
        pnlPreview.setBorder(javax.swing.BorderFactory.createLineBorder(Color.black));
        pnlDifficulty.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 0, 10, 0));
        pnlTime.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 0, 0, 0));
        pnlStep.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        JLabel lblDifficultyTitle = new JLabel("Difficulty", SwingConstants.CENTER);
        lblDifficulty = new JLabel(line + " x " + line, SwingConstants.CENTER);
        JLabel lblTimeTitle = new JLabel("Time", SwingConstants.CENTER);
        lblTimer = new JLabel("00 : 00", SwingConstants.CENTER);
        JLabel lblStepTitle = new JLabel("Step", SwingConstants.CENTER);
        lblStep = new JLabel("0", SwingConstants.CENTER);
        
        lblDifficultyTitle.setFont(new Font("Courier New", Font.BOLD, 25));
        lblTimeTitle.setFont(new Font("Courier New", Font.BOLD, 25));
        lblStepTitle.setFont(new Font("Courier New", Font.BOLD, 25));
        
        lblDifficulty.setFont(new Font("Courier New", Font.BOLD, 35));
        lblTimer.setFont(new Font("Courier New", Font.BOLD, 35));
        lblStep.setFont(new Font("Courier New", Font.BOLD, 35));
        
        
        Image preImg = img.getScaledInstance(200, 150, java.awt.Image.SCALE_SMOOTH);
        ImageIcon preIcon = new ImageIcon(preImg);
        JLabel lblPreview = new JLabel(preIcon);
        pnlPreview.add(lblPreview);
        
        pnlDifficulty.add(lblDifficultyTitle, BorderLayout.NORTH);
        pnlDifficulty.add(lblDifficulty, BorderLayout.CENTER);
        pnlTime.add(lblTimeTitle, BorderLayout.NORTH);
        pnlTime.add(lblTimer, BorderLayout.CENTER);
        pnlStep.add(lblStepTitle, BorderLayout.NORTH);
        pnlStep.add(lblStep, BorderLayout.CENTER);
        pnlInformation.add(pnlDifficulty);
        pnlInformation.add(pnlTime);
        pnlInformation.add(pnlStep);
        
        pnlSub.add(pnlPreview, BorderLayout.NORTH);
        pnlSub.add(pnlInformation, BorderLayout.CENTER);
    }

    /**
     * this is for Choosing difficulty
     * @param e 
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            String selectedItem = e.getItem().toString();
            selectedItem = selectedItem.substring(0, selectedItem.indexOf(" x "));
            line = Integer.parseInt(selectedItem);
            makePieces();
       }
    }
    
    /**
     * Divide the images to pieces by using difficulty user chose.
     * line means level(start from 3)
     * remove the perfect image and set the subimages (pieces) to the panel
     */
    private void makePieces() {
        pnlGameBoard.removeAll();
        pieces = line * line;
        if(line > 0) {
            pnlGameBoard.setLayout(new GridLayout(line, line, 2, 2));
            subImage = new BufferedImage[pieces];
            btnImage = new JButton[pieces];
            order = new String[pieces];
            
            imagePiece();
            
            for(int i=0; i<pieces; i++) {
                btnImage[i] = new JButton();
                ImageIcon icoImg = new ImageIcon(subImage[i]);
                Image subImgs = icoImg.getImage();
                Image newImg = subImgs.getScaledInstance(800/line, 550/line, java.awt.Image.SCALE_SMOOTH);
                icoImg = new ImageIcon(newImg);
                btnImage[i].setIcon(icoImg);
                btnImage[i].setName(Integer.toString(i));
                pnlGameBoard.add(btnImage[i]);
                btnImage[i].addActionListener(this);
            }
        }
        pnlGameBoard.updateUI();
    }
    
    /**
     * make pieces
     */
    private void imagePiece() {
        for(int i=0; i<line; i++){
            for(int j=0; j<line; j++) {
                subImage[(i*line)+j] = imageFile.getSubimage(width / line * j, height / line * i, width / line, height / line);
            }
        }
    }
    
    /**
     * suffle pieces to random place
     */
    private void sufflePieces() {
        
        pnlGameBoard.removeAll();
        Random rnd = new Random();
        random = rnd.nextInt(pieces);
        order = new String[pieces];
        
        for (int i = 0; i < pieces; i++) {
            pnlGameBoard.add(btnImage[(i + random) % pieces]);
            order[i] = btnImage[(i + random) % pieces].getName();
            
            if (order[i].equals(Integer.toString(pieces - 1)))
                tempindex = i;
        }
        tempbutton = pieces - 1;
        tempimage = btnImage[pieces - 1].getIcon();
        btnImage[pieces - 1].setVisible(false);

        pnlGameBoard.updateUI();
        
        //TimeThreae starts
        timerThread = new ThreadTimer(lblTimer);
        timerThread.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < pieces; i++) {
            if (e.getSource() == btnImage[i]) {
                int j = (i - random + pieces) % pieces;

                if (tempindex % line == 0) {
                    if (j == (tempindex + 1) || j == (tempindex + line) || j == (tempindex - line)) {
                        click(i);
                        break;
                    }
                } else if (tempindex % line == line - 1) {
                    if (j == (tempindex - 1) || j == (tempindex + line) || j == (tempindex - line)) {
                        click(i);
                        break;
                    }
                } else {
                    if (j == (tempindex + 1) || j == (tempindex - 1) || j == (tempindex + line) || j == (tempindex - line)) {
                        click(i);
                        break;
                    }
                }
            }
        }
        if(e.getSource()==btnBack) {
            if(from.equals("local")) {
                this.dispose();
                FrmChooseImage awin = new FrmChooseImage(userName);
                awin.setVisible(true);
            }else if(from.equals("db")) {
                this.dispose();
                new FrmImageThumbnail(userName).setVisible(true);
            }
        } else if(e.getSource() == btnExit) {
            System.exit(0);
        }
    }
    
    private void click(int i) {
        stepCounter++;
        lblStep.setText(String.valueOf(stepCounter));
        btnImage[tempbutton].setIcon(btnImage[i].getIcon());
        btnImage[tempbutton].setName(btnImage[i].getName());
        btnImage[tempbutton].setVisible(true);

        tempbutton = i;
        int j;
        for (j = 0; j < pieces; j++) {
            if (order[j].equals(btnImage[i].getName())) {
                break;
            }
        }
        String tempstr = order[tempindex];
        order[tempindex] = btnImage[i].getName();
        order[j] = tempstr;
        tempindex = j;
        btnImage[i].setIcon(tempimage);
        btnImage[i].setName(Integer.toString(tempbutton));
        btnImage[i].setVisible(false);

        ordercheck();
    }
    
    private void ordercheck() {
        int ordercheck = 0;
        for (int i = 0; i < pieces; i++) {
            if (order[i].equals(Integer.toString(i))) {
                ordercheck++;
            }
            if (ordercheck == pieces - 1) {
                pnlGameBoard.removeAll();
                pnlGameBoard.setLayout(new GridLayout(1, 1, 2, 2));
                pnlGameBoard.add(firstImage);
                pnlGameBoard.updateUI();
                
                timerThread.stop();
                done = true;
                
            }
        }
        if(done) {
            String time = lblTimer.getText();
            int score = calculateScore(time, stepCounter);
            String name = "";
            if(imgFrom.equals("local")) {
                PuzzleDAO dao = new PuzzleDAO();
                int userId = dao.selectUser(userName);
                int count = dao.getExistingImage(userId, imgName);
                if(count==0) {
                    dao.insertImage(imgPath, imgName, imgFrom, userId);
                }
                imageId = dao.getImageId(imgPath, imgName);
            }
            FrmScore awin = new FrmScore(this, imageFile, userName, line, totalTime, stepCounter, score, imageId, imgPath, imgName, imgFrom, from);
            awin.setVisible(true);
        }
    }
    
    /**
     * calculate the score by using time and steps.
     * @param time
     * @param step
     * @return 
     */
    private int calculateScore(String time, int step) {
        String strMin="";
        String strSec="";
        int index = time.indexOf(":");
        strMin = time.substring(0, index);
        strSec = time.substring(index+1, time.length());
        strMin = strMin.trim();
        strSec = strSec.trim();
        int min=0;
        int sec=0;
        min = Integer.parseInt(strMin);
        sec = Integer.parseInt(strSec);
        
        totalTime = min*60 + sec;
        int score = 1000 - totalTime - stepCounter;
        return score;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource() == btnBack) {
            btnBack.setForeground(new Color(247, 80, 30));
        } else if(e.getSource() == btnExit) {
            btnExit.setForeground(new Color(247, 80, 30));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource() == btnBack) {
            btnBack.setForeground(new Color(0, 0, 102));
        } else if(e.getSource() == btnExit) {
            btnExit.setForeground(new Color(0, 0, 102));
        }
    }
}
