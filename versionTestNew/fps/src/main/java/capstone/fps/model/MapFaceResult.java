package capstone.fps.model;


import com.google.gson.annotations.Expose;
import com.sun.xml.internal.ws.developer.Serialization;


public class MapFaceResult {

    String rep;
    String key;

    public MapFaceResult() {
    }

    public MapFaceResult(String rep, String key) {
        this.rep = rep;
        this.key = key;
    }

    public String getRep() {
        return rep;
    }



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
