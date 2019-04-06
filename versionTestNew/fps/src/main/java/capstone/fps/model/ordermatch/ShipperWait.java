package capstone.fps.model.ordermatch;

public class ShipperWait {
  private   boolean isCancel ;

    public ShipperWait() {
        this.isCancel = false;
    }

    public boolean isCancel() {
        return isCancel;
    }

    public void setCancel(boolean cancel) {
        isCancel = cancel;
    }
}
