package hackathon.returnvoid.runner;

import hackathon.returnvoid.emotiondetection.Emotion;
import hackathon.returnvoid.emotiondetection.EmotionDetector;
import hackathon.returnvoid.emotiondetection.FileBasedEmotionDetector;
import hackathon.returnvoid.player.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static hackathon.returnvoid.emotiondetection.Emotion.HAPPY;
import static hackathon.returnvoid.emotiondetection.Emotion.SAD;

/**
 * Created by phil on 18-11-16.
 */
public class EmotionPlayer implements Runnable {

    private static final String EMOTION_PATH = "src/test/resource/emotion";
    private EmotionDetector emotionDetector = new FileBasedEmotionDetector(EMOTION_PATH);
    private AudioPlayer audioPlayer = new AudioPlayerImpl();
    private TextArea textArea;
    private HashMap<Emotion,List<Song>> playlists;
    private boolean next = false;
    private static final String MP3_PATH = "/src/test/resource/mp3/";
    private SongFinder songFinder;
    public EmotionPlayer(){

    }

    public EmotionPlayer(TextArea textArea){
        this.textArea = textArea;
        songFinder = new SongFinderImpl(MP3_PATH);

        List<Emotion> emotions = Arrays.asList(SAD, HAPPY);
        playlists = new HashMap<>();

        for(Emotion e: emotions){
            playlists.put(e,songFinder.findSong(e));
        }
    }

    public void next(){
        next = true;
    }

    public void run() {
        boolean running = true;

        HashMap<Emotion,Iterator<Song>> iterators = new HashMap<>();

        for(Emotion e : playlists.keySet()){
            iterators.put(e,playlists.get(e).listIterator());
        }

        Emotion currentEmotion = emotionDetector.detectEmotion();
        Iterator<Song> currentIt = iterators.get(currentEmotion);

        do{

            if(!currentIt.hasNext()){
                List<Song> newSongs = songFinder.findSong(currentEmotion);
                Collections.shuffle(newSongs);
                playlists.put(currentEmotion,newSongs);
                iterators.put(currentEmotion, newSongs.listIterator());
                currentIt = iterators.get(currentEmotion);
            }
            Song currentSong = currentIt.next();

            this.textArea.setText("Currently playing:\n" +
                      currentSong +
                    "\nYour emotion seems to be: \n"
                    + currentEmotion +
                    "\n I chose the following playlist\n" +
                    playlists.get(currentEmotion).stream().map(Object::toString).collect(Collectors.joining("\n")));



            audioPlayer.play(currentSong);

            do {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while(audioPlayer.isPlaying() && !next);
            currentIt.remove();


            if(next){
                next = false;
                audioPlayer.stop();
            }
            Emotion nextEmotion = emotionDetector.detectEmotion();
            if(nextEmotion != currentEmotion){
                currentEmotion = nextEmotion;
                currentIt = iterators.get(currentEmotion);

            }

        }while(running);

    }}


