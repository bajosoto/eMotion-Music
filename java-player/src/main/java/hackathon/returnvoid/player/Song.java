package hackathon.returnvoid.player;

/**
 * Created by phil on 18-11-16.
 */
public class Song {
    private String path;

    public Song(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String toString(){
        return path.substring(path.lastIndexOf("/")+1);
    }
}
