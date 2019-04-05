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
    private int countOrderLess; // Count order with <12h
    @Expose
    private int countOrderEqual; //Count order with 12-24h
    @Expose
    private int countOrderMore; //Count order >24h

    @Expose
    private int soldProductCount;
    @Expose
    private double successRate;
    @Expose
    private int totalAmount;
    @Expose
    private int paidShipper;
//    @Expose
//    private List<Integer> orders;

    public int getCountOrderLess() {
        return countOrderLess;
    }

    public void setCountOrderLess(int countOrderLess) {
        this.countOrderLess = countOrderLess;
    }

    public int getCountOrderEqual() {
        return countOrderEqual;
    }

    public void setCountOrderEqual(int countOrderEqual) {
        this.countOrderEqual = countOrderEqual;
    }

    public int getCountOrderMore() {
        return countOrderMore;
    }

    public void setCountOrderMore(int countOrderMore) {
        this.countOrderMore = countOrderMore;
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

    public int getSoldProductCount() {
        return soldProductCount;
    }

    public void setSoldProductCount(int soldProductCount) {
        this.soldProductCount = soldProductCount;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getPaidShipper() {
        return paidShipper;
    }

    public void setPaidShipper(int paidShipper) {
        this.paidShipper = paidShipper;
    }
}
