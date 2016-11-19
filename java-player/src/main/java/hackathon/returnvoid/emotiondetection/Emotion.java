package hackathon.returnvoid.emotiondetection;

/**
 * Created by phil on 18-11-16.
 */
public enum Emotion {

    SAD,HAPPY, ANGRY,CONTEMPTED,DISGUSTED,SURPRISED,FEARED;

    static Emotion fromString(String string){
        switch (string){
            case "SAD": return SAD;
            case "HAPPY" : return HAPPY;
            case "ANGRY" : return ANGRY;
            case "CONTEMPTED" : return CONTEMPTED;
            case "DISGUSTED" : return DISGUSTED;
            case "SURPRISED" : return SURPRISED;
            case "FEARED" : return FEARED;
            default: throw new IllegalStateException("Emotion: " + string + " unkown string");
        }
    }
}
