/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exampleplayaudio;

import java.io.File;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.SceneBuilder;
import javafx.scene.control.LabelBuilder;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageBuilder;
import javafx.stage.WindowEvent;
import javax.swing.SwingUtilities;

/**
 *
 * @author tsoglani
 */
public class MediaPlayerExample {

    public static void main(String[] args) {
        playSound(new File("two.mp3"));
    }
    static File file;

    public static void playSound(File fileName) {

        try {
            file = fileName;
            Media media = new Media(file.toURI().toURL().toExternalForm());
            MediaPlayer player = new MediaPlayer(media);
            player.play();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }

    }

}
