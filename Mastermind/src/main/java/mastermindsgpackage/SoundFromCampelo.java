package mastermindsgpackage;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by cadet on 11/10/15.
 */
public class SoundFromCampelo {

    // The wrapper thread is unnecessary, unless it blocks on the
    // Clip finishing; see comments.
    public static HashMap<SoundName, Clip> soundClips;
    private static AudioInputStream inputStream;


    public static void load() {


        soundClips = new HashMap<>(SoundName.values().length);



        for (SoundName soundName : SoundName.values()) {
            try {
                soundClips.put(soundName, AudioSystem.getClip());

                String pathStr = "/resources/Music/" + soundName.getPath() + ".wav";
                URL soundURL = Sound.class.getResource(pathStr);


                if (soundURL == null) {
                    //System.out.println("soundURL null");
                    File file = new File("resources/Music/" + soundName.getPath() + ".wav");
                    //System.out.println("absolutePath: "+pathStr);
                    soundURL = file.toURI().toURL();
                }


                //System.out.println("soundURL: "+soundURL);  // check the URL!
                inputStream = AudioSystem.getAudioInputStream(soundURL);
                soundClips.get(soundName).open(inputStream);


            } catch (Exception e) {
                System.out.println("Error playing sound");
                System.err.println(e);
            }
        }
    }
}