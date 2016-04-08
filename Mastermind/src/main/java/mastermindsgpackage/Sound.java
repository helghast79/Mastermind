package mastermindsgpackage;


import java.io.*;


import java.util.*;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.AudioStreamSequence;

import javax.sound.midi.Sequence;
import javax.sound.sampled.AudioInputStream;

/**
 * Created by Miguel Chambel
 * Comments & Suggestions to: m.a.chambel@gmail.com
 */

public class Sound {

    //list of music files
    private List<File> musicList;

    //iterator for the music files stored in the list
    private Iterator<File> musicIterator;

    //audioStream - global scope needed since we need to start and stop current audioStream
    private AudioStream audioStream;


    public Sound() {


        //this array will hold the midi files prior to populate de arraylist
        File[] midiFiles;

        //temporary file to mediate transfer process to the array
        File tempFile = new File(Config.fileResourcesPath + Config.musicAddToPath);

        //populate array with the files matching filter in the tempFile folder
        midiFiles = tempFile.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith("") && name.endsWith("mid");
            }
        });


        //create the arrayList
        musicList = new ArrayList<File>();

        for (File file : midiFiles) {
            musicList.add(file);
        }


        //Sort alphabetically - In this case I want to random music so no need to do this
        /*musicList.sort(new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });*/

        //random music list
        long seed = System.nanoTime();
        Collections.shuffle(musicList, new Random(seed));

        //create iterator
        musicIterator = musicList.iterator();




    }



    public void playNext(){


        if(!musicIterator.hasNext()) {

            //shuffle musiclist before creating a new iterator
            long seed = System.nanoTime();
            Collections.shuffle(musicList, new Random(seed));

            //since iterator reached last item recreate it
            musicIterator = musicList.iterator();

        }

        //goto next music
        File file = musicIterator.next();




        try {
            FileInputStream fileIS = new FileInputStream(file);

            AudioStream audiostream1 = new AudioStream(fileIS);

           /* Vector v = new Vector();
            v.addElement(audiostream1);
            v.addElement(audiostream2);
            AudioStreamSequence ass = new AudioStreamSequence(v.elements());

            */



            audioStream = new AudioStream(fileIS);
            AudioPlayer.player.start(audioStream);



        } catch (Throwable e) {
            e.printStackTrace();

        }

    }

    public void stop(){
        try {
            //don't try to stop a null object!
            if (audioStream != null) {
                AudioPlayer.player.stop(audioStream);
            }

        }
        catch(Exception e) {
            System.err.println(e);

        }
    }



}







