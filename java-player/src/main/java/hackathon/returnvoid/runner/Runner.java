package hackathon.returnvoid.runner;

import hackathon.returnvoid.emotiondetection.Emotion;
import hackathon.returnvoid.player.Song;
import hackathon.returnvoid.player.SongFinder;
import hackathon.returnvoid.player.SongFinderImpl;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static hackathon.returnvoid.emotiondetection.Emotion.HAPPY;
import static hackathon.returnvoid.emotiondetection.Emotion.SAD;

/**
 * Created by phil on 19-11-16.
 */
public class Runner extends Application{


    public static void main(String... args){
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
                TextArea textArea = new TextArea();

        EmotionPlayer emotionPlayer = new EmotionPlayer(textArea);

        textArea.setOnMouseClicked(event -> emotionPlayer.next());

        Thread playerThread = new Thread(emotionPlayer);
        playerThread.start();

        VBox vbox = new VBox(textArea);

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
