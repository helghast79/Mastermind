package mastermindsgpackage;



import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;


/**
 * Created by Miguel Chambel
 * Comments & Suggestions to: m.a.chambel@gmail.com
 */

public class MidiSound {

    AudioStream audioStream;
    //Sequencer sequencer;


    public void play(String filePath){


        try {
            URL resourceURL = getClass().getResource(filePath);

            File file = new File(resourceURL.getFile());

            FileInputStream fileIS = new FileInputStream(file);

            audioStream = new AudioStream(fileIS);

            AudioPlayer.player.start(audioStream);

        } catch (Throwable e) {
            e.printStackTrace();

        }

    }


    /*public void play(String filePath){


        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            InputStream is = new BufferedInputStream(new FileInputStream(new File(filePath)));
            sequencer.setSequence(is);
            sequencer.start();



        } catch (Throwable e) {
            e.printStackTrace();

        }

    }*/



    public void stop(){


        try {
            //don't try to stop a null object!
            if (audioStream != null) {
                AudioPlayer.player.stop(audioStream);
            }

           /*if(sequencer != null){
               sequencer.stop();
           }*/


        }
        catch(Exception e) {
            System.err.println(e);

        }
    }



}







