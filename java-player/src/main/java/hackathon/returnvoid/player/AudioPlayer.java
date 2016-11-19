package hackathon.returnvoid.player;

import javazoom.jl.decoder.JavaLayerException;

/**
 * Created by phil on 18-11-16.
 */
public interface AudioPlayer {
    void play(Song song);
    void stop();
    boolean waitForFinish();
    boolean isPlaying();
}
