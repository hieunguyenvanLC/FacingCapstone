package capstone.fps.model;


import com.google.gson.annotations.Expose;
import com.sun.xml.internal.ws.developer.Serialization;


public class MapFaceResult {

    public String rep;

    public String key;

    public MapFaceResult(String rep, String key) {
        this.rep = rep;
        this.key = key;
    }

    public MapFaceResult() {
    }
}
