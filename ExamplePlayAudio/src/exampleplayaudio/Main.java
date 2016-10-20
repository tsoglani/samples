/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author tsoglani
 */
/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  com.darkprograms.speech.microphone.Microphone
 *  com.darkprograms.speech.recognizer.GSpeechDuplex
 *  com.darkprograms.speech.recognizer.GSpeechResponseListener
 *  com.darkprograms.speech.recognizer.GoogleResponse
 *  net.sourceforge.javaflacencoder.FLACFileWriter
 */
import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.recognizer.GSpeechDuplex;
import com.darkprograms.speech.recognizer.GSpeechResponseListener;
import com.darkprograms.speech.recognizer.GoogleResponse;
import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.TargetDataLine;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import net.sourceforge.javaflacencoder.FLACFileWriter;

public class Main {
    public static void main(String[] args) throws IOException {
        final Microphone mic = new Microphone(FLACFileWriter.FLAC);
        GSpeechDuplex duplex = new GSpeechDuplex("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
        duplex.setLanguage("en");
        JFrame frame = new JFrame("Jarvis Speech API DEMO");
        frame.setDefaultCloseOperation(3);
        JTextArea response = new JTextArea();
        response.setEditable(false);
        response.setWrapStyleWord(true);
        response.setLineWrap(true);
        final JButton record = new JButton("Record");
        final JButton stop = new JButton("Stop");
        stop.setEnabled(false);
        record.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    duplex.recognize(mic.getTargetDataLine(), mic.getAudioFormat());
                    record.setEnabled(false);
                    stop.setEnabled(true);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        stop.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                mic.close();
                record.setEnabled(true);
                stop.setEnabled(false);
            }
        });
        JLabel infoText = new JLabel("<html><div style=\"text-align: center;\">Just hit record and watch your voice be translated into text.\n<br>Only English is supported by this demo, but the full API supports dozens of languages.<center></html>", 0);
        frame.getContentPane().add(infoText);
        infoText.setAlignmentX(0.5f);
        JScrollPane scroll = new JScrollPane(response);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), 1));
        frame.getContentPane().add(scroll);
        JPanel recordBar = new JPanel();
        frame.getContentPane().add(recordBar);
        recordBar.setLayout(new BoxLayout(recordBar, 0));
        recordBar.add(record);
        recordBar.add(stop);
        frame.setVisible(true);
        frame.pack();
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        duplex.addResponseListener(new GSpeechResponseListener(){
            String old_text;

            public void onResponse(GoogleResponse gr) {
                String output = "";
                output = gr.getResponse();
                if (gr.getResponse() == null) {
                    old_text = response.getText();
                    if (this.old_text.contains("(")) {
                        this.old_text = this.old_text.substring(0, this.old_text.indexOf(40));
                    }
                    System.out.println("Paragraph Line Added");
                    this.old_text = String.valueOf(response.getText()) + "\n";
                    this.old_text = this.old_text.replace(")", "").replace("( ", "");
                   response.setText(this.old_text);
                    return;
                }
                if (output.contains("(")) {
                    output = output.substring(0, output.indexOf(40));
                }
                if (!gr.getOtherPossibleResponses().isEmpty()) {
                    output = String.valueOf(output) + " (" + (String)gr.getOtherPossibleResponses().get(0) + ")";
                }
                System.out.println(output);
                response.setText("");
               response.append(this.old_text);
                response.append(output);
            }
        });
    }

}
