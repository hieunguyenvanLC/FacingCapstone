package capstone.fps.model.home;

import com.google.gson.annotations.Expose;

public class MdlReportSummaryDetail {
    @Expose
    private int orderCountBy; //count order with day month year
    @Expose
    private int orderCancelBy; //Count order cancel with day month year
    @Expose
    private int orderSuccessBy;  //Count order success with day month year
    @Expose
    private int soldProductBy;  //Count product sold with day month year

    public int getOrderCountBy() {
        return orderCountBy;
    }

    public void setOrderCountBy(int orderCountBy) {
        this.orderCountBy = orderCountBy;
    }

    public int getOrderCancelBy() {
        return orderCancelBy;
    }

    public void setOrderCancelBy(int orderCancelBy) {
        this.orderCancelBy = orderCancelBy;
    }

    public int getOrderSuccessBy() {
        return orderSuccessBy;
    }

    public void setOrderSuccessBy(int orderSuccessBy) {
        this.orderSuccessBy = orderSuccessBy;
    }

    public int getSoldProductBy() {
        return soldProductBy;
    }

    public void setSoldProductBy(int soldProductBy) {
        this.soldProductBy = soldProductBy;
    }
}
