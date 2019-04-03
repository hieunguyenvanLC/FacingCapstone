package capstone.fps.model;


class MapFaceResult {

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
