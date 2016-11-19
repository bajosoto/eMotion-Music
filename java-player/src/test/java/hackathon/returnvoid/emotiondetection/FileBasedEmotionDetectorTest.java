package hackathon.returnvoid.emotiondetection;

import org.junit.Test;

import static hackathon.returnvoid.emotiondetection.Emotion.ANGRY;
import static hackathon.returnvoid.emotiondetection.Emotion.HAPPY;
import static hackathon.returnvoid.emotiondetection.Emotion.SAD;
import static org.junit.Assert.*;

/**
 * Created by phil on 18-11-16.
 */
public class FileBasedEmotionDetectorTest {

    @Test
    public void basicTest(){
        FileBasedEmotionDetector fileBasedEmotionDetector = new FileBasedEmotionDetector("src/test/resource/emotion");
        assertEquals(SAD,fileBasedEmotionDetector.detectEmotion());
        assertEquals(HAPPY,fileBasedEmotionDetector.detectEmotion());
        assertEquals(ANGRY,fileBasedEmotionDetector.detectEmotion());
    }

}