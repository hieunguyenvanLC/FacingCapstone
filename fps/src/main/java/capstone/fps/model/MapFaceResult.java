package capstone.fps.model;


public class MapFaceResult {

    public String key;
    public String faceListStr;

    public MapFaceResult() {
    }

    public MapFaceResult(String key, String faceListStr) {
        this.key = key;
        this.faceListStr = faceListStr;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFaceListStr() {
        return faceListStr;
    }

    public void setFaceListStr(String faceListStr) {
        this.faceListStr = faceListStr;
    }
}
