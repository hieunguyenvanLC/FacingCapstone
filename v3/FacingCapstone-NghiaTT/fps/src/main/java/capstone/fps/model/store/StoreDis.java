package capstone.fps.model.store;

import capstone.fps.entity.FRStore;

public class StoreDis {
    private FRStore store;
    private Double dis;

    public StoreDis(FRStore store, Double dis) {
        this.store = store;
        this.dis = dis;
    }

    public FRStore getStore() {
        return store;
    }

    public Double getDis() {
        return dis;
    }
}