package capstone.fps.model;

import com.google.gson.annotations.Expose;

public class MdlReportSummary {
    @Expose
    private int shipperCount;
    @Expose
    private int newCustomerCount;

    public int getShipperCount() {
        return shipperCount;
    }

    public void setShipperCount(int shipperCount) {
        this.shipperCount = shipperCount;
    }

    public int getNewCustomerCount() {
        return newCustomerCount;
    }

    public void setNewCustomerCount(int newCustomerCount) {
        this.newCustomerCount = newCustomerCount;
    }

    public int getNewStoreCount() {
        return newStoreCount;
    }

    public void setNewStoreCount(int newStoreCount) {
        this.newStoreCount = newStoreCount;
    }

    public int getNewOrderCount() {
        return newOrderCount;
    }

    public void setNewOrderCount(int newOrderCount) {
        this.newOrderCount = newOrderCount;
    }

    @Expose
    private int newStoreCount;
    @Expose
    private int newOrderCount;
}
