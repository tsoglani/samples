/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exampleplayaudio;

import sun.audio.*;    //import the sun.audio package
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tsoglani
 */
public class SoundClipTest {

    public static void main(String[] args) {

        new SoundClipTest().playSound(new File("two.mp3"));
    }
    InputStream in = null;
    AudioStream as = null;

    public synchronized void playSound(final File file) {

        try {
            in = new FileInputStream(file);
            // Create an AudioStream object from the input stream.
            as = new AudioStream(in);
            // Use the static class member "player" from class AudioPlayer to play
// clip.
            AudioPlayer.player.start(as);
// Similarly, to stop the audio.
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private synchronized void close() {

        try {
            in.close();
            AudioPlayer.player.stop(as);

            as.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
