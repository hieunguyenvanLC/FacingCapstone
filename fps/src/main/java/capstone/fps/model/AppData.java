package capstone.fps.model;

import java.util.HashMap;
import java.util.Map;

public class AppData {

    private static Map<String, String> faceResult = new HashMap<>();

    public static Map<String, String> getFaceResult() {
        return faceResult;
    }
}
