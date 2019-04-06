package capstone.fps.model.ordermatch;

import capstone.fps.entity.FROrder;

public class OrderStat {

    private FROrder frOrder;

    private double storeLon;
    private double storeLat;

    private int lockBy;
    private boolean isCancel;

    public OrderStat(FROrder frOrder, double storeLon, double storeLat) {
        this.frOrder = frOrder;
        this.storeLon = storeLon;
        this.storeLat = storeLat;
        this.lockBy = 0;
        this.isCancel = false;
    }

    public FROrder getFrOrder() {
        return frOrder;
    }

    public double getStoreLon() {
        return storeLon;
    }

    public double getStoreLat() {
        return storeLat;
    }

    public Integer getLockBy() {
        return lockBy;
    }

    public void setLockBy(Integer lockBy) {
        this.lockBy = lockBy;
    }

    public boolean isCancel() {
        return isCancel;
    }

    public void setCancel(boolean cancel) {
        isCancel = cancel;
    }


}
