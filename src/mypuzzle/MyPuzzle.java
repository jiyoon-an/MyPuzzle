package mypuzzle;

import java.util.ArrayList;

public class MyPuzzle {

    public static void main(String[] args) {
        CommonFunction cf = new CommonFunction();
        PuzzleDAO dao = new PuzzleDAO();
        
        String name = cf.getPCName();
        ArrayList arrUser = dao.getUser(name);
        (new Thread(new AudioThread())).start();
        
        if(arrUser == null) {
            FrmRegister awin = new FrmRegister(name,0);
            awin.setVisible(true);
        } else {
            FrmLogin awin = new FrmLogin(arrUser, name);
            awin.setVisible(true);
        }
    }
    
}
