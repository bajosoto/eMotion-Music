package hackathon.returnvoid.player;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Semaphore;

/**
 * Created by phil on 18-11-16.
 */
public class AudioPlayerImpl extends PlaybackListener implements AudioPlayer,Runnable {

    private AdvancedPlayer player;
    private Semaphore sem = new Semaphore(1);
    private boolean playing = false;

    @Override
    public void play(Song song) {
        String urlString = "file:///" + song.getPath();
        try {
            sem.acquire();
            playing = true;
            player = new AdvancedPlayer(new URL(urlString).openStream(), FactoryRegistry.systemRegistry().createAudioDevice());
            player.setPlayBackListener(this);
            Thread playerThread = new Thread(this, "AudioPlayerThread");
            playerThread.start();

        } catch (IOException | InterruptedException | JavaLayerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        player.stop();
    }

    @Override
    public void run() {
        try
        {
            this.player.play();
            playing = false;
            sem.release();

        }
         catch (JavaLayerException e) {
            e.printStackTrace();
        }

    }

    public boolean waitForFinish(){
        try {
            sem.acquire();
            sem.release();
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isPlaying() {
        return playing;
    }

    public void playbackStarted(PlaybackEvent playbackEvent)
    {
        System.out.println("playbackStarted()");
    }

    public void playbackFinished(PlaybackEvent playbackEvent)
    {
        System.out.println("playbackEnded()");
    }
}
