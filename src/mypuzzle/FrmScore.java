
package mypuzzle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;


public class FrmScore extends JFrame implements ActionListener, MouseListener{
    //variables for grigbaglayout
    private int x=0;
    private int y=0;
    private int width=1;
    private int height=1;
    //ranking start from 1
    private int ranking = 1;
    //buttons for next move
    private JButton btnReplay, btnChooseImage, btnExit;
    //information about user, image
    private String userName, imgPath, imgFrom, from;
    private JFrame frm;
    private String imgName;
    private int imageId;
    private BufferedImage bImg;
    public FrmScore(JFrame frm, BufferedImage bImg, String userName, int level, int time, int stop, int score, int imageId, String imgPath, String imgName, String imgFrom, String from) {
        this.bImg = bImg;
        this.imageId = imageId;
        this.imgPath = imgPath;
        this.imgName = imgName;
        this.imgFrom = imgFrom;
        this.from = from;
        this.userName = userName;
        this.frm = frm;
        PuzzleDAO dao = new PuzzleDAO();
        
        //load the userid by using userName
        int userId = dao.selectUser(userName);
        //insert the ranking of this game
        dao.setRanking(userId, level, time, stop, score, imageId);
        //get top 5 row ranking data for this image
        ArrayList arrRanking = dao.getRanking(imageId);
        
        JPanel pnlMain = new JPanel(new BorderLayout());
        JPanel pnlTitle = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 30));
        JPanel pnlRanking = new JPanel(new GridBagLayout());
        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 40));
        JLabel lblTitle = new JLabel("Score");
        lblTitle.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        lblTitle.setForeground(new Color(0, 0, 102));
        pnlTitle.add(lblTitle);
        
        pnlMain.setOpaque(false);
        pnlTitle.setOpaque(false);
        pnlRanking.setOpaque(false);
        pnlBtn.setOpaque(false);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 35, 5, 35);
        
        gbc.gridx = x++;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        
        JLabel lblRanking = new JLabel("No", SwingConstants.CENTER);
        lblRanking.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        pnlRanking.add(lblRanking, gbc);
        
        gbc.gridx = x++;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        
        JLabel lblName = new JLabel("Name", SwingConstants.CENTER);
        lblName.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        pnlRanking.add(lblName, gbc);
        
        gbc.gridx = x;
        gbc.gridy = y++;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        
        JLabel lblScore = new JLabel("Score", SwingConstants.CENTER);
        lblScore.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        pnlRanking.add(lblScore, gbc);
        
        
        for(int i=0; i<arrRanking.size(); i++) {
            HashMap map = (HashMap)arrRanking.get(i);
            x=0;
            gbc.gridx = x++;
            gbc.gridy = y;
            gbc.gridwidth = width;
            gbc.gridheight = height;
            
            JLabel lbl1 = new JLabel(""+ranking++, SwingConstants.CENTER);
            lbl1.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
            pnlRanking.add(lbl1, gbc);
            
            gbc.gridx = x++;
            gbc.gridy = y;
            gbc.gridwidth = width;
            gbc.gridheight = height;
            String userName2 = dao.getUserName(Integer.parseInt(map.get("userId").toString()));
            JLabel lbl2 = new JLabel(userName2, SwingConstants.CENTER);
            lbl2.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
            pnlRanking.add(lbl2, gbc);
            
            gbc.gridx = x++;
            gbc.gridy = y++;
            gbc.gridwidth = width;
            gbc.gridheight = height;
            JLabel lbl3 = new JLabel(map.get("score").toString(), SwingConstants.CENTER);
            lbl3.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
            pnlRanking.add(lbl3, gbc);
        }
        btnReplay = new JButton("Replay");
        btnChooseImage = new JButton("Choose Image");
        btnExit = new JButton("Exit");
        
        btnReplay.setContentAreaFilled(false);
        btnChooseImage.setContentAreaFilled(false);
        btnExit.setContentAreaFilled(false);
        
        btnReplay.setForeground(new Color(0, 0, 102));
        btnChooseImage.setForeground(new Color(0, 0, 102));
        btnExit.setForeground(new Color(0, 0, 102));
        
        btnReplay.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        btnChooseImage.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        btnExit.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        
        btnReplay.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnChooseImage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnExit.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        btnReplay.setFocusPainted(false);
        
        btnReplay.addActionListener(this);
        btnChooseImage.addActionListener(this);
        btnExit.addActionListener(this);
        
        btnReplay.addMouseListener(this);
        btnChooseImage.addMouseListener(this);
        btnExit.addMouseListener(this);
        
        pnlBtn.add(btnReplay);
        pnlBtn.add(btnChooseImage);
        pnlBtn.add(btnExit);
        
        pnlMain.add(pnlTitle, BorderLayout.NORTH);
        pnlMain.add(pnlRanking, BorderLayout.CENTER);
        pnlMain.add(pnlBtn, BorderLayout.SOUTH);
        JLabel bgImage = new JLabel(new ImageIcon("image/register_bg.png"));
        bgImage.setLayout(new BorderLayout());
        
        this.setContentPane(bgImage);
        this.add(pnlMain);
        this.setSize(400, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnReplay) {
            //replay with this image
            this.dispose();
            frm.dispose();
            FrmSlideGame frmGame = new FrmSlideGame(bImg, imgPath, imgName, imgFrom , userName, from, imageId);
                this.setVisible(false);
                frmGame.setVisible(true);
                
                int level = frmGame.setLevel();
                if(level == 0) {
                    frmGame.setVisible(false);
                    this.setVisible(true);
                }
        } else if(e.getSource() == btnChooseImage) {
            //play with another image
            this.dispose();
            frm.dispose();
            FrmChooseImage awin = new FrmChooseImage(userName);
            awin.setVisible(true);
        } else if(e.getSource() == btnExit) {
            System.exit(0);
        }
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
        JButton btn = (JButton)e.getSource();
        btn.setForeground(new Color(247, 80, 30));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JButton btn = (JButton)e.getSource();
        btn.setForeground(new Color(0, 0, 102));
    }
    
    
}
