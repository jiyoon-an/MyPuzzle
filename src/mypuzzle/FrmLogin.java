package mypuzzle;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;


public class FrmLogin extends JFrame implements ActionListener, ChangeListener, WindowListener {
    private JPanel[] pnlUser;
    private JButton btnRegister, btnExit;
    private JPanel pnlMain, pnlBtn;
    private PWField lblPW[];
    private String[] strPW;
    private JTabbedPane pane;
    private boolean openFlag = true;
    private ArrayList arrUser;
    private String pcName;
    public FrmLogin(ArrayList arrUser, String pcName) {
        this.arrUser = arrUser;
        this.pcName = pcName;
        this.setTitle("My Puzzle! - Login");
        pnlMain = new JPanel(new BorderLayout());
        pnlMain.setOpaque(false);
        pnlBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        pnlBtn.setOpaque(false);
        pnlMain.setBorder(javax.swing.BorderFactory.createEmptyBorder(100, 0, 10, 0));
        
        pnlUser = new JPanel[arrUser.size()];
        strPW = new String[arrUser.size()];
        
        //to make transparent tab
        Color transparent = new Color(0, 0, 0, 0);
        UIManager.put("TabbedPane.shadow",                transparent);
        UIManager.put("TabbedPane.darkShadow",            transparent);
        UIManager.put("TabbedPane.light",                 transparent);
        UIManager.put("TabbedPane.highlight",             transparent);
        UIManager.put("TabbedPane.tabAreaBackground",     transparent);
        UIManager.put("TabbedPane.unselectedBackground",  transparent);
        UIManager.put("TabbedPane.background",            transparent);
        UIManager.put("TabbedPane.foreground",            transparent);
        UIManager.put("TabbedPane.focus",                 transparent);
        UIManager.put("TabbedPane.contentAreaColor",      transparent);
        UIManager.put("TabbedPane.selected",              transparent);
        UIManager.put("TabbedPane.selectHighlight",       transparent);
        UIManager.put("TabbedPane.borderHightlightColor", transparent);
        
        pane = new JTabbedPane();
        pane.setOpaque(false);
        pane.addChangeListener(this);
        
        for(int i=0; i<arrUser.size(); i++) {
            strPW[i] = "";
            makeLoginForm(i);
            HashMap mapUser = (HashMap)arrUser.get(i);
            pane.add(pnlUser[i], mapUser.get("userName"));
            //add the users to each tab
            JLabel lblTab = new JLabel();
            if(i==0){
                lblTab = new JLabel(mapUser.get("userName").toString(), new ImageIcon("image/user_selected.png"), SwingConstants.CENTER);
                lblTab.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            } else {
                lblTab = new JLabel(mapUser.get("userName").toString(), new ImageIcon("image/user_unselected.png"), SwingConstants.CENTER);
                lblTab.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
            }
            
            lblTab.setVerticalTextPosition(SwingConstants.BOTTOM);
            lblTab.setHorizontalTextPosition(SwingConstants.CENTER);
            pane.setTabComponentAt(i, lblTab);
        }
        createBtn();
        
        pnlMain.add(pane, BorderLayout.CENTER);
        pnlMain.add(pnlBtn, BorderLayout.SOUTH);
        
        ImageIcon icon = new ImageIcon("image/login_bg.png");
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(480, 600, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);
        JLabel bgImage = new JLabel(icon);
        bgImage.setLayout(new BorderLayout());
        this.setContentPane(bgImage);
        this.add(pnlMain);
        this.setSize(480, 600);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    /**
     * In this method, each tab GUI is designed.
     * @param num 
     */
    private void makeLoginForm(int num) {
        pnlUser[num] = new JPanel(new GridBagLayout());
        pnlUser[num].setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3,3,3,3);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 3;
        JPanel pnlPW = new JPanel(new GridLayout(1,4,5,1));
        pnlPW.setOpaque(false);
        lblPW = new PWField[4];
        for(int i=0; i<lblPW.length; i++) {
            lblPW[i] = new PWField();
            pnlPW.add(lblPW[i]);
        }
        pnlUser[num].add(pnlPW, gbc);
        
        PWButton btnPW[] = new PWButton[10];
        for(int i=0; i<btnPW.length; i++) {
            btnPW[i] = new PWButton(String.valueOf(i));
            btnPW[i].addActionListener(this);
        }
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        pnlUser[num].add(btnPW[1], gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        pnlUser[num].add(btnPW[2], gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        pnlUser[num].add(btnPW[3], gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        pnlUser[num].add(btnPW[4], gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        pnlUser[num].add(btnPW[5], gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        pnlUser[num].add(btnPW[6], gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        pnlUser[num].add(btnPW[7], gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        pnlUser[num].add(btnPW[8], gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        pnlUser[num].add(btnPW[9], gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        PWButton btnClear = new PWButton("C");
        btnClear.addActionListener(this);
        pnlUser[num].add(btnClear, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        pnlUser[num].add(btnPW[0], gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        PWButton btnBack = new PWButton("←");
        btnBack.setFont(new Font("Calibri", Font.BOLD, 20));
        btnBack.addActionListener(this);
        pnlUser[num].add(btnBack, gbc);
    }
    
    /**
     * create the buttons about register and exit
     */
    private void createBtn() {
        btnRegister = new JButton("Register");
        btnExit = new JButton("Exit");
        
        btnRegister.setPreferredSize(new Dimension(122, 40));
        btnExit.setPreferredSize(new Dimension(122, 40));
        
        btnRegister.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        btnExit.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        
        btnRegister.setForeground(Color.DARK_GRAY);
        btnExit.setForeground(Color.DARK_GRAY);
        
        btnRegister.addActionListener(this);
        btnExit.addActionListener(this);
        
        pnlBtn.add(btnRegister);
        pnlBtn.add(btnExit);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnRegister) {
            //go to register form
            FrmRegister awin = new FrmRegister(arrUser, pcName,1);
            this.dispose();
            awin.setVisible(true);
        } else if(e.getSource() == btnExit) {
            //quit the program
            System.exit(0);
        } else {
            //enter the password
            String strBtn = ((PWButton)e.getSource()).getText();
            int currentUser = pane.getSelectedIndex();
            switch(strBtn) {
                case "0" :
                    inputPW(strBtn, currentUser);
                    break;
                case "1" :
                    inputPW(strBtn, currentUser);
                    break;
                case "2" :
                    inputPW(strBtn, currentUser);
                    break;
                case "3" :
                    inputPW(strBtn, currentUser);
                    break;
                case "4" :
                    inputPW(strBtn, currentUser);
                    break;
                case "5" :
                    inputPW(strBtn, currentUser);
                    break;
                case "6" :
                    inputPW(strBtn, currentUser);
                    break;
                case "7" :
                    inputPW(strBtn, currentUser);
                    break;
                case "8" :
                    inputPW(strBtn, currentUser);
                    break;
                case "9" :
                    inputPW(strBtn, currentUser);
                    break;
                case "C" :
                    clearPW(currentUser);
                    break;
                case "←" :
                    deletePW(currentUser);
                    break;
                default:
                    break;
            }
            checkPW(currentUser);
        }
    }
    
    //when button is clicked, make the password by using entered number
    private void inputPW(String btnText, int currentUser) {
        if(strPW[currentUser].length()<4) {
            strPW[currentUser] += btnText;
        }
        Component comp = pnlUser[currentUser].getComponent(0);
        JPanel pnlTemp = (JPanel)comp;
        Component[] lbl = pnlTemp.getComponents();
        for(int i=0; i<lbl.length; i++) {
            if(lbl[i] instanceof JPasswordField) {
                JPasswordField pwField = (JPasswordField)lbl[i];
                if(i<strPW[currentUser].length()){
                    pwField.setText(String.valueOf(strPW[currentUser].charAt(i)));
                }
            }
        }
    }
    
    /**
     * clear the password
     * @param currentUser 
     */
    private void clearPW(int currentUser){
        strPW[currentUser] = "";
        Component comp = pnlUser[currentUser].getComponent(0);
        JPanel pnlTemp = (JPanel)comp;
        Component[] lbl = pnlTemp.getComponents();
        for(int i=0; i<lbl.length; i++) {
            if(lbl[i] instanceof JPasswordField) {
                JPasswordField pwField = (JPasswordField)lbl[i];
                pwField.setText("");
            }
        }
    }
    
    /**
     * delete the last letters of password
     * @param currentUser 
     */
    private void deletePW(int currentUser) {
        if(strPW[currentUser].length()>0) {
            strPW[currentUser] = strPW[currentUser].substring(0, strPW[currentUser].length()-1);
        }
        Component comp = pnlUser[currentUser].getComponent(0);
        JPanel pnlTemp = (JPanel)comp;
        Component[] lbl = pnlTemp.getComponents();
        for(int i=0; i<lbl.length; i++) {
            if(lbl[i] instanceof JPasswordField) {
                JPasswordField pwField = (JPasswordField)lbl[i];
                if(i<strPW[currentUser].length()){
                    pwField.setText(String.valueOf(strPW[currentUser].charAt(i)));
                }else {
                    pwField.setText("");
                }
            }
        }
    }
    
    /**
     * if user entered 4 digits password, check whether it's correct or not
     * @param currentUser 
     */
    private void checkPW(int currentUser) {
        if(strPW[currentUser].length()==4) {
            HashMap mapUser = (HashMap)arrUser.get(currentUser);
            String dbPW = mapUser.get("userPassword").toString();
            if(strPW[currentUser].equals(dbPW)) {
                CommonFunction cf = new CommonFunction();
                java.sql.Timestamp today = cf.getDate();
                PuzzleDAO dao = new PuzzleDAO();
                
                String userName = mapUser.get("userName").toString();
                this.setVisible(false);
                FrmChooseImage awin = new FrmChooseImage(userName);
                awin.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Login Failed. Please Check Your Password Again.");
                clearPW(currentUser);
            }
        }
    }
    

    @Override
    public void stateChanged(ChangeEvent e) {
        //when tab is changed, replace the panel to other user's panel
        if(!openFlag) {
            int currentUser = pane.getSelectedIndex();
            for(int i=0; i<strPW.length; i++) {
                if(i!=currentUser) {
                    clearPW(i);
                }
            }
            JLabel lblIcon = new JLabel();
            for(int i=0; i<pane.getTabCount(); i++) {
                if(i==currentUser) {
                    lblIcon = new JLabel(pane.getTitleAt(i), new ImageIcon("image/user_selected.png"), SwingConstants.CENTER);
                    lblIcon.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
                } else {
                    lblIcon = new JLabel(pane.getTitleAt(i), new ImageIcon("image/user_unselected.png"), SwingConstants.CENTER);
                    lblIcon.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
                }
                lblIcon.setVerticalTextPosition(SwingConstants.BOTTOM);
                lblIcon.setHorizontalTextPosition(SwingConstants.CENTER);
                pane.setTabComponentAt(i, lblIcon);
            }
            
        }else {
            openFlag = false;
        }
        
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
