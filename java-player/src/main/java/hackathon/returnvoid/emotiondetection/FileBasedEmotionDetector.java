package hackathon.returnvoid.emotiondetection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Created by phil on 18-11-16.
 */
public class FileBasedEmotionDetector implements EmotionDetector {

    private Iterator<Emotion> emotionIterator;
    private String emotionFile;

    public FileBasedEmotionDetector(String emotionFile){
        this.emotionFile = emotionFile;
        readFromFile();
    }

    private void readFromFile(){
        List<Emotion> emotions = new LinkedList<>();
        try {
            Runtime.getRuntime().exec("python ../03_VideoRecognition/emotion.py");

            Scanner fs = new Scanner(new File(emotionFile));
            while(fs.hasNextLine()){
                emotions.add(Emotion.fromString(fs.nextLine()));
            }
            emotionIterator = emotions.iterator();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Emotion detectEmotion() {

        if(!emotionIterator.hasNext())
            readFromFile();

        return emotionIterator.next();
    }
}
