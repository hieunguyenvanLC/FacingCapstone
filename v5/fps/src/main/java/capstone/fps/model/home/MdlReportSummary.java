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
//    @Expose
//    private List<Integer> canceledOrders;
//    @Expose
//    private List<Integer> successOrders;
//    @Expose
//    private List<String> labels;
//    @Expose
//    private int orderCountBy; //count order with day month year
//    @Expose
//    private int orderCancelBy; //Count order cancel with day month year
//    @Expose
//    private int orderSuccessBy;  //Count order success with day month year
//    @Expose
//    private int soldProductBy;  //Count product sold with day month year

//    public int getSoldProductBy() {
//        return soldProductBy;
//    }
//
//    public void setSoldProductBy(int soldProductBy) {
//        this.soldProductBy = soldProductBy;
//    }
//
//    public int getOrderSuccessBy() {
//        return orderSuccessBy;
//    }
//
//    public void setOrderSuccessBy(int orderSuccessBy) {
//        this.orderSuccessBy = orderSuccessBy;
//    }
//
//    public int getOrderCancelBy() {
//        return orderCancelBy;
//    }
//
//    public void setOrderCancelBy(int orderCancelBy) {
//        this.orderCancelBy = orderCancelBy;
//    }
//
//    public int getOrderCountBy() {
//        return orderCountBy;
//    }
//
//    public void setOrderCountBy(int orderCountBy) {
//        this.orderCountBy = orderCountBy;
//    }


//    public List<Integer> getOrders() {
//        return orders;
//    }
//
//    public void setOrders(List<Integer> orders) {
//        this.orders = orders;
//    }
//
//    public List<Integer> getCanceledOrders() {
//        return canceledOrders;
//    }
//
//    public void setCanceledOrders(List<Integer> canceledOrders) {
//        this.canceledOrders = canceledOrders;
//    }
//
//    public List<Integer> getSuccessOrders() {
//        return successOrders;
//    }
//
//    public void setSuccessOrders(List<Integer> successOrders) {
//        this.successOrders = successOrders;
//    }
//
//    public List<String> getLabels() {
//        return labels;
//    }
//
//    public void setLabels(List<String> labels) {
//        this.labels = labels;
//    }

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
