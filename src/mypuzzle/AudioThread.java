package mypuzzle;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class AudioThread implements Runnable {

    @Override
    public void run() {
        int time=22;
        //Audio's runtime is 22 seconds.
        while(time>0) {
            if(time==22) {
                Play("audio/bgmusic.wav");
                //play audio
            }
            time--;
            //time is checking every 1 second.
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(AudioThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(time==0) {
                time = 22;
                //if time is 0, set to 22 again and replay the audio
            }
        }
        
    }
    
    /*
    This method is for playing audio
    */
    public void Play(String fileName)
    {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(fileName));
            Clip clip = AudioSystem.getClip();
            clip.stop();
            clip.open(ais);
            clip.start();
        } catch (Exception ex){}
    }
    
}
