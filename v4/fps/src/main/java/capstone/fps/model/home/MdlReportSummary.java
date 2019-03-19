package capstone.fps.model.home;

import com.google.gson.annotations.Expose;

public class MdlReportSummary {
    @Expose
    private int shipperCount; //Count all shipper
    @Expose
    private int CustomerCount; //count all customer
    @Expose
    private int StoreCount; //count all store
    @Expose
    private int OrderCount; // count all order
    @Expose
    private int orderCountBy; //count order with day month year
    @Expose
    private int orderCancelBy; //Count order cancel with day month year


    public int getOrderCancelBy() {
        return orderCancelBy;
    }

    public void setOrderCancelBy(int orderCancelBy) {
        this.orderCancelBy = orderCancelBy;
    }

    public int getOrderCountBy() {
        return orderCountBy;
    }

    public void setOrderCountBy(int orderCountBy) {
        this.orderCountBy = orderCountBy;
    }

    public int getShipperCount() {
        return shipperCount;
    }

    public void setShipperCount(int shipperCount) {
        this.shipperCount = shipperCount;
    }

    public int getCustomerCount() {
        return CustomerCount;
    }

    public void setCustomerCount(int customerCount) {
        CustomerCount = customerCount;
    }

    public int getStoreCount() {
        return StoreCount;
    }

    public void setStoreCount(int storeCount) {
        StoreCount = storeCount;
    }

    public int getOrderCount() {
        return OrderCount;
    }

    public void setOrderCount(int orderCount) {
        OrderCount = orderCount;
    }
}
