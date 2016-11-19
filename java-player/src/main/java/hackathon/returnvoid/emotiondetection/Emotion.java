package hackathon.returnvoid.emotiondetection;

/**
 * Created by phil on 18-11-16.
 */
public enum Emotion {

    SAD,HAPPY, ANGRY,CONTEMPTED,DISGUSTED,SURPRISED,FEARED;

    static Emotion fromString(String string){
        switch (string){
            case "sadness": return SAD;
            case "happiness" : return HAPPY;
            case "angry" : return ANGRY;
            case "contempted" : return CONTEMPTED;
            case "disgusted" : return DISGUSTED;
            case "surprised" : return SURPRISED;
            case "feared" : return FEARED;
            case "neutral" : return SAD;
            default: throw new IllegalStateException("Emotion: " + string + " unkown string");
        }
    }
}
