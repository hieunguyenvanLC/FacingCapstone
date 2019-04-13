package capstone.fps.model.home;

import com.google.gson.annotations.Expose;

import java.util.List;

public class MdlChartData<E> {
    @Expose
    List<String> labels;
    @Expose
    List<E> data;

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }
}
