package mypuzzle;

import static java.lang.Thread.sleep;
import javax.swing.JLabel;


public class ThreadTimer extends Thread{
    //the label show the time
    JLabel lblTimer;
    int min=0, sec=0;
    String strMin="", strSec="";
    public ThreadTimer(JLabel lblTimer) {
        this.lblTimer = lblTimer;
    }
    public void run() {
        while (true) {  
            sec++;
            if(sec==60){ // if second is 60, reset second to 0 and add 1 for min
                sec=0;
                min++;
            }
            if(sec<10) {
                strSec = "0"+String.valueOf(sec);
            } else {
                strSec = String.valueOf(sec);
            }
            
            if(min<10) {
                strMin = "0" + String.valueOf(min);
            } else {
                strMin = String.valueOf(min);
            }
            lblTimer.setText(strMin + " : " + strSec);
            
            try {
                sleep(1000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}