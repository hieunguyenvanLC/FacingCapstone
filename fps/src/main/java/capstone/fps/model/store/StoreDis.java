package capstone.fps.model.store;

import capstone.fps.entity.FRStore;

public class StoreDis {
    private double dis;
    private FRStore store;

    public StoreDis(double dis, FRStore store) {
        this.dis = dis;
        this.store = store;
    }

    public double getDis() {
        return dis;
    }

    public FRStore getStore() {
        return store;
    }
}
