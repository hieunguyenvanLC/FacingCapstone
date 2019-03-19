package capstone.fps.model.ordermatch;

import capstone.fps.entity.FROrder;

public class OrderStat {

    private FROrder frOrder;
    private int lockBy;
    private boolean isCancel;

    public OrderStat(FROrder frOrder) {
        this.frOrder = frOrder;
        this.lockBy = 0;
        this.isCancel = false;
    }

    public FROrder getFrOrder() {
        return frOrder;
    }

    public void setFrOrder(FROrder frOrder) {
        this.frOrder = frOrder;
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
