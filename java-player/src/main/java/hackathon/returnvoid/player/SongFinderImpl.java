package hackathon.returnvoid.player;

import hackathon.returnvoid.emotiondetection.Emotion;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by phil on 18-11-16.
 */
public class SongFinderImpl implements SongFinder {

    private String folder;

    public SongFinderImpl(String folder){
        this.folder = folder;
    }

    @Override
    public List<Song> findSong(Emotion emotion) {
        List<Song> songs = new LinkedList<>();
        File[] files;
        try {
            files = new File( new File(".").getCanonicalPath() + folder+emotion + "/").listFiles();

        if(files != null && files.length>0)
            for(File file : files){
            songs.add(new Song(file.getAbsolutePath()));
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
        return songs;

    }
}
