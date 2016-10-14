/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exampleplayaudio;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mp3transform.Decoder;

/**
 *
 * @author tsoglani
 */
public class ExamplePlayAudio {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        playSound();
    }
    static Decoder decoder;

    public static void playSound() {

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    decoder = new Decoder();
                    File file = new File("one.mp3");
                    FileInputStream in = new FileInputStream(file);
                    BufferedInputStream bin = new BufferedInputStream(in, 128 * 1024);
                    decoder.play(file.getName(), bin);
                    in.close();

                    decoder.stop();
                } catch (Exception exc) {
                    exc.printStackTrace();
                    System.out.println("Failed to play the file.");
                }
            }
        });
        t1.start();

        new Thread(new Runnable() {
            public void run(){
                try {
                    Thread.sleep(4000);
                    
                                        decoder.stop();

                } catch (InterruptedException ex) {
                    Logger.getLogger(ExamplePlayAudio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }

}
