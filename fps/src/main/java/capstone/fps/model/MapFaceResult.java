package capstone.fps.model;


import com.google.gson.annotations.Expose;
import com.sun.xml.internal.ws.developer.Serialization;


public class MapFaceResult {

    String rep;

    public MapFaceResult() {
    }

    public MapFaceResult(String rep) {
        this.rep = rep;
    }

    public String getRep() {
        return rep;
    }

    public void setRep(String rep) {
        this.rep = rep;
    }

    @Override
    public String toString() {
        return "MapFaceResult{" +
                "rep='" + rep + '\'' +
                '}';
    }
}
