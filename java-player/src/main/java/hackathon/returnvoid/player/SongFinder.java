package hackathon.returnvoid.player;

import hackathon.returnvoid.emotiondetection.Emotion;

import java.util.List;

/**
 * Created by phil on 18-11-16.
 */
public interface SongFinder {
    List<Song> findSong(Emotion emotion);
}
