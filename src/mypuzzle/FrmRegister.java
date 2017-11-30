package mypuzzle;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.*;


public class FrmRegister extends JFrame implements ActionListener, DocumentListener, FocusListener, KeyListener{
    private JTextField txtName, txtNameCom, txtPWFirstCom, txtPWSecondCom;
    //name, and comments for limitaion of name, password
    private JPasswordField txtPWFirst, txtPWSecond;
    //password and confirm password
    private JButton btnSave, btnCancel;
    //store the information to db or cancel
    private String firstPW = "";
    //at the first, the password is ""
    private String pcName;
    //pc name which user is playing
    private CommonFunction cf;
    //for calling function in CommonFunction
    boolean chkName=false, chkPWFirst=false, chkPWSecond=false;
    //if name, password satisfy the conditions.
    int flag = 0; // 0:first page, 1:from login
    ArrayList arrUser;
    public FrmRegister(ArrayList arrUser, String pcName, int flag) {
        this(pcName, flag);
        this.arrUser = arrUser;
        
    }
    public FrmRegister(String pcName, int flag) {
        this.flag =  flag;
        this.pcName = pcName;
        this.setTitle("Regist Your Accont!");
        JPanel pnlMain = new JPanel(new BorderLayout());
        JPanel pnlTitle = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 30));
        JPanel pnlInformation = new JPanel(new GridBagLayout());
        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        
        pnlMain.setOpaque(false);
        pnlTitle.setOpaque(false);
        pnlInformation.setOpaque(false);
        pnlBtn.setOpaque(false);
        
        JLabel lblTitle = new JLabel("Regist New Account", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
        
        pnlTitle.add(lblTitle);
        pnlMain.add(pnlTitle, BorderLayout.NORTH);
        
        JLabel lblName = new JLabel("Name");
        JLabel lblPWFirst = new JLabel("Password (4 Digit Numebrs)");
        JLabel lblPWSecond = new JLabel("Password Confirm");
        
        lblName.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        lblPWFirst.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        lblPWSecond.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        
        txtName = new JTextField(10);
        txtPWFirst = new JPasswordField(10);
        txtPWSecond = new JPasswordField(10);
        
        txtName.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        txtPWFirst.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        txtPWSecond.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        
        txtName.addFocusListener(this);
        txtPWFirst.addFocusListener(this);
        txtPWSecond.addFocusListener(this);
        
        txtName.getDocument().putProperty("name", txtName);
        txtPWFirst.getDocument().putProperty("pwFirst", txtPWFirst);
        txtPWSecond.getDocument().putProperty("pwSecond", txtPWSecond);
        
        txtName.getDocument().addDocumentListener(this);
        txtPWFirst.getDocument().addDocumentListener(this);
        txtPWSecond.getDocument().addDocumentListener(this);
        
        txtNameCom = new JTextField(21);
        txtPWFirstCom = new JTextField(21);
        txtPWSecondCom = new JTextField(21);
        
        txtNameCom.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        txtPWFirstCom.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        txtPWSecondCom.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        
        txtNameCom.setEditable(false);
        txtPWFirstCom.setEditable(false);
        txtPWSecondCom.setEditable(false);
        
        txtNameCom.setOpaque(false);
        txtPWFirstCom.setOpaque(false);
        txtPWSecondCom.setOpaque(false);
        
        txtNameCom.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        txtPWFirstCom.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        txtPWSecondCom.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        
        txtName.setHorizontalAlignment(JTextField.RIGHT);
        txtPWFirst.setHorizontalAlignment(JTextField.RIGHT);
        txtPWSecond.setHorizontalAlignment(JTextField.RIGHT);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 3, 0, 10);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        pnlInformation.add(lblName, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        pnlInformation.add(txtName, gbc);
        
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        pnlInformation.add(txtNameCom, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        pnlInformation.add(lblPWFirst, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        pnlInformation.add(txtPWFirst, gbc);
        
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        pnlInformation.add(txtPWFirstCom, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        pnlInformation.add(lblPWSecond, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        pnlInformation.add(txtPWSecond, gbc);
        
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        pnlInformation.add(txtPWSecondCom, gbc);
        
        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");
        
        btnSave.addActionListener(this);
        btnCancel.addActionListener(this);
        
        btnSave.setPreferredSize(new Dimension(150, 40));
        btnCancel.setPreferredSize(new Dimension(150, 40));
        
        btnSave.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        btnCancel.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        
        pnlBtn.add(btnSave);
        pnlBtn.add(btnCancel);
        
        pnlMain.add(pnlInformation, BorderLayout.CENTER);
        pnlMain.add(pnlBtn, BorderLayout.SOUTH);
        
        //pnlRegist.add(pnlMain, BorderLayout.CENTER);
        ImageIcon icon = new ImageIcon("image/register_bg.png");
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(650, 350, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);
        JLabel bgImage = new JLabel(icon);
        bgImage.setLayout(new BorderLayout());
        this.setContentPane(bgImage);
        this.add(pnlMain);
        txtName.addKeyListener(this);
        this.setSize(650, 350);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnSave) {
            cf = new CommonFunction();
            java.sql.Timestamp today = cf.getDate();
            PuzzleDAO dao = new PuzzleDAO();
            //if name, password, confirm password don't satisfy the condition, information cannot be stored.
            if(chkName && chkPWFirst && chkPWSecond) {
                dao.setUser(txtName.getText(), txtPWFirst.getPassword(), pcName, today);
                this.dispose();
                FrmChooseImage awin = new FrmChooseImage(txtName.getText());
                awin.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Check name and password again.");
            }
        } else if(e.getSource() == btnCancel) {
            if(flag == 0) {
                System.exit(0);
            } else if(flag == 1) {
                this.dispose();
                FrmLogin awin = new FrmLogin(arrUser, pcName);
                awin.setVisible(true);
            }
        }
    }

    //checking the textfield for name and password. and check it's available or not
    @Override
    public void insertUpdate(DocumentEvent e) {
        String curName = "", curPWFirst = "", curPWSecond = "";
        JTextField txtTemp = (JTextField) e.getDocument().getProperty("name");
        if(txtTemp!=null) {
            if(txtTemp.getText()!=null) {
                curName = txtTemp.getText();
                try {
                    RegisterException.isValidName(curName);
                    txtNameCom.setForeground(Color.green);
                    txtNameCom.setText("'" + curName + "' is available!");
                    chkName = true;
                } catch(RegisterException ex) {
                    chkName = false;
                    txtNameCom.setForeground(Color.red);
                    txtNameCom.setText(ex.getMessage());
                }                
            } 
        }
               
        txtTemp = (JTextField)e.getDocument().getProperty("pwFirst");
        if(txtTemp!=null) {
            if(txtTemp.getText()!=null) {
                curPWFirst = txtTemp.getText();
                try {
                    RegisterException.isValidPW(curPWFirst);
                    txtPWFirstCom.setForeground(Color.green);
                    txtPWFirstCom.setText("Perfect!");
                    firstPW = curPWFirst;
                    chkPWFirst = true;
                } catch(RegisterException ex) {
                    chkPWFirst = false;
                    txtPWFirstCom.setForeground(Color.red);
                    txtPWFirstCom.setText(ex.getMessage());
                }
            } 
        }
        
        txtTemp = (JTextField)e.getDocument().getProperty("pwSecond");
        if(txtTemp!=null) {
            if(txtTemp.getText()!=null) {
                curPWSecond = txtTemp.getText();
                try {
                    RegisterException.isValidPW(curPWSecond);
                    RegisterException.isMatchedPW(firstPW, curPWSecond);
                    txtPWSecondCom.setForeground(Color.green);
                    txtPWSecondCom.setText("Perfect!");
                    chkPWSecond = true;
                } catch(RegisterException ex) {
                    chkPWSecond = false;
                    txtPWSecondCom.setForeground(Color.red);
                    txtPWSecondCom.setText(ex.getMessage());
                }
            } 
        }
    }

    //when user pressed backspace, it works same with insertUpdate
    @Override
    public void removeUpdate(DocumentEvent e) {
        String curName = "", curPWFirst = "", curPWSecond = "";
        JTextField txtTemp = (JTextField) e.getDocument().getProperty("name");
        if(txtTemp!=null) {
            if(txtTemp.getText()!=null) {
                curName = txtTemp.getText();
                try {
                    RegisterException.isValidName(curName);
                    txtNameCom.setForeground(Color.green);
                    txtNameCom.setText("'" + curName + "' is available!");
                    chkName = true;
                } catch(RegisterException ex) {
                    chkName = false;
                    txtNameCom.setForeground(Color.red);
                    txtNameCom.setText(ex.getMessage());
                }                
            } 
        }
               
        txtTemp = (JTextField)e.getDocument().getProperty("pwFirst");
        if(txtTemp!=null) {
            if(txtTemp.getText()!=null) {
                curPWFirst = txtTemp.getText();
                try {
                    RegisterException.isValidPW(curPWFirst);
                    txtPWFirstCom.setForeground(Color.green);
                    txtPWFirstCom.setText("Perfect!");
                    firstPW = curPWFirst;
                    chkPWFirst = true;
                } catch(RegisterException ex) {
                    chkPWFirst = false;
                    txtPWFirstCom.setForeground(Color.red);
                    txtPWFirstCom.setText(ex.getMessage());
                }
            } 
        }
        
        txtTemp = (JTextField)e.getDocument().getProperty("pwSecond");
        if(txtTemp!=null) {
            if(txtTemp.getText()!=null) {
                curPWSecond = txtTemp.getText();
                try {
                    RegisterException.isValidPW(curPWSecond);
                    RegisterException.isMatchedPW(curPWFirst, curPWSecond);
                    txtPWSecondCom.setForeground(Color.green);
                    txtPWSecondCom.setText("Perfect!");
                    chkPWSecond = true;
                } catch(RegisterException ex) {
                    chkPWSecond = false;
                    txtPWSecondCom.setForeground(Color.red);
                    txtPWSecondCom.setText(ex.getMessage());
                }
            } 
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }

    //when passwordfield gain focus without validated name, popup dialog is shown.
    @Override
    public void focusGained(FocusEvent e) {
        if(e.getSource()==txtName){
            
        } else if(e.getSource() == txtPWFirst) {
            String tempName = "";
            if(!(txtName.getText()).equals("")) {
                tempName = txtName.getText().toString();
                tempName = tempName.toUpperCase();
                txtName.setText(tempName);
            } else {
                txtName.requestFocus();
                JOptionPane.showMessageDialog(this, "Please Enter the Name First.");
            }
        } else if(e.getSource() == txtPWSecond) {
            if(txtPWFirst.getPassword().length<4) {
               txtPWFirst.requestFocus();
               JOptionPane.showMessageDialog(this, "Password must have 4 characters.");
            }
        }
        
    }

    @Override
    public void focusLost(FocusEvent e) {
        if(e.getSource()==txtName) {
            String tempName = "";
            tempName = txtName.getText().toString();
            tempName = tempName.toUpperCase();
            txtName.setText(tempName);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    //only capital, lower case, number is permitted.
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()<8 || (e.getKeyCode()>8 && e.getKeyCode()<48) || (e.getKeyCode()>57 && e.getKeyCode()<65) || (e.getKeyCode()>90 && e.getKeyCode()<97) || e.getKeyCode()>122) {
            JOptionPane.showMessageDialog(this, "Only Capital, lower character and number is allowed");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
