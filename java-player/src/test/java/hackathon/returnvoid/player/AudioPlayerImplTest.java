package hackathon.returnvoid.player;

import javazoom.jl.decoder.JavaLayerException;
import org.junit.Test;
import org.junit.runner.notification.RunListener;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by phil on 18-11-16.
 */
public class AudioPlayerImplTest {

    @Test
    public void oneSongTest() throws JavaLayerException, IOException {
        Song song = new Song(new File(".").getCanonicalPath() + "/src/test/resource/mp3/test.mp3");
        AudioPlayerImpl audioPlayer = new AudioPlayerImpl();
        audioPlayer.play(song);
        try {
            Thread.sleep(1000);
            assertTrue(audioPlayer.waitForFinish());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}