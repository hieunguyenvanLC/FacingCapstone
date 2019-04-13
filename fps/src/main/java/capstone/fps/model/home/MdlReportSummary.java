package capstone.fps.model.home;

import com.google.gson.annotations.Expose;

public class MdlReportSummary {
    @Expose
    private int shipperCount; //Count all shipper
    @Expose
    private int shipperCountTDay; //Count all shipper
    @Expose
    private int shipperCountTWeek; //Count all shipper
    @Expose
    private int shipperCountTMonth; //Count all shipper

    @Expose
    private int CustomerCount; //count all customer
    @Expose
    private int CustomerCountTDay;
    @Expose
    private int CustomerCountTWeek; //count all customer
    @Expose
    private int CustomerCountTMonth; //count all customer

    @Expose
    private int StoreCount; //count all store
    @Expose
    private int StoreCountTDay; //count all store
    @Expose
    private int StoreCountTWeek; //count all store
    @Expose
    private int StoreCountTMonth; //count all store

    @Expose
    private int OrderCount; // count all order
    @Expose
    private int OrderCountTDay; // count all order
    @Expose
    private int OrderCountTWeek; // count all order
    @Expose
    private int OrderCountTMonth; // count all order


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

    @Expose
    private int soldProductCountTDay;
    @Expose
    private double successRateTDay;
    @Expose
    private int totalAmountTDay;
    @Expose
    private int paidShipperTDay;

    @Expose
    private int soldProductCountTWeek;
    @Expose
    private double successRateTWeek;
    @Expose
    private int totalAmountTWeek;
    @Expose
    private int paidShipperTWeek;


    @Expose
    private int soldProductCountTMonth;
    @Expose
    private double successRateTMonth;
    @Expose
    private int totalAmountTMonth;
    @Expose
    private int paidShipperTMonth;

//    @Expose
//    private List<Integer> orders;

    public int getShipperCountTDay() {
        return shipperCountTDay;
    }

    public void setShipperCountTDay(int shipperCountTDay) {
        this.shipperCountTDay = shipperCountTDay;
    }

    public int getShipperCountTWeek() {
        return shipperCountTWeek;
    }

    public void setShipperCountTWeek(int shipperCountTWeek) {
        this.shipperCountTWeek = shipperCountTWeek;
    }

    public int getShipperCountTMonth() {
        return shipperCountTMonth;
    }

    public void setShipperCountTMonth(int shipperCountTMonth) {
        this.shipperCountTMonth = shipperCountTMonth;
    }

    public int getCustomerCountTDay() {
        return CustomerCountTDay;
    }

    public void setCustomerCountTDay(int customerCountTDay) {
        CustomerCountTDay = customerCountTDay;
    }

    public int getCustomerCountTWeek() {
        return CustomerCountTWeek;
    }

    public void setCustomerCountTWeek(int customerCountTWeek) {
        CustomerCountTWeek = customerCountTWeek;
    }

    public int getCustomerCountTMonth() {
        return CustomerCountTMonth;
    }

    public void setCustomerCountTMonth(int customerCountTMonth) {
        CustomerCountTMonth = customerCountTMonth;
    }

    public int getStoreCountTDay() {
        return StoreCountTDay;
    }

    public void setStoreCountTDay(int storeCountTDay) {
        StoreCountTDay = storeCountTDay;
    }

    public int getStoreCountTWeek() {
        return StoreCountTWeek;
    }

    public void setStoreCountTWeek(int storeCountTWeek) {
        StoreCountTWeek = storeCountTWeek;
    }

    public int getStoreCountTMonth() {
        return StoreCountTMonth;
    }

    public void setStoreCountTMonth(int storeCountTMonth) {
        StoreCountTMonth = storeCountTMonth;
    }

    public int getOrderCountTDay() {
        return OrderCountTDay;
    }

    public void setOrderCountTDay(int orderCountTDay) {
        OrderCountTDay = orderCountTDay;
    }

    public int getOrderCountTWeek() {
        return OrderCountTWeek;
    }

    public void setOrderCountTWeek(int orderCountTWeek) {
        OrderCountTWeek = orderCountTWeek;
    }

    public int getOrderCountTMonth() {
        return OrderCountTMonth;
    }

    public void setOrderCountTMonth(int orderCountTMonth) {
        OrderCountTMonth = orderCountTMonth;
    }

    public int getSoldProductCountTDay() {
        return soldProductCountTDay;
    }

    public void setSoldProductCountTDay(int soldProductCountTDay) {
        this.soldProductCountTDay = soldProductCountTDay;
    }

    public double getSuccessRateTDay() {
        return successRateTDay;
    }

    public void setSuccessRateTDay(double successRateTDay) {
        this.successRateTDay = successRateTDay;
    }

    public int getTotalAmountTDay() {
        return totalAmountTDay;
    }

    public void setTotalAmountTDay(int totalAmountTDay) {
        this.totalAmountTDay = totalAmountTDay;
    }

    public int getPaidShipperTDay() {
        return paidShipperTDay;
    }

    public void setPaidShipperTDay(int paidShipperTDay) {
        this.paidShipperTDay = paidShipperTDay;
    }

    public int getSoldProductCountTWeek() {
        return soldProductCountTWeek;
    }

    public void setSoldProductCountTWeek(int soldProductCountTWeek) {
        this.soldProductCountTWeek = soldProductCountTWeek;
    }

    public double getSuccessRateTWeek() {
        return successRateTWeek;
    }

    public void setSuccessRateTWeek(double successRateTWeek) {
        this.successRateTWeek = successRateTWeek;
    }

    public int getTotalAmountTWeek() {
        return totalAmountTWeek;
    }

    public void setTotalAmountTWeek(int totalAmountTWeek) {
        this.totalAmountTWeek = totalAmountTWeek;
    }

    public int getPaidShipperTWeek() {
        return paidShipperTWeek;
    }

    public void setPaidShipperTWeek(int paidShipperTWeek) {
        this.paidShipperTWeek = paidShipperTWeek;
    }

    public int getSoldProductCountTMonth() {
        return soldProductCountTMonth;
    }

    public void setSoldProductCountTMonth(int soldProductCountTMonth) {
        this.soldProductCountTMonth = soldProductCountTMonth;
    }

    public double getSuccessRateTMonth() {
        return successRateTMonth;
    }

    public void setSuccessRateTMonth(double successRateTMonth) {
        this.successRateTMonth = successRateTMonth;
    }

    public int getTotalAmountTMonth() {
        return totalAmountTMonth;
    }

    public void setTotalAmountTMonth(int totalAmountTMonth) {
        this.totalAmountTMonth = totalAmountTMonth;
    }

    public int getPaidShipperTMonth() {
        return paidShipperTMonth;
    }

    public void setPaidShipperTMonth(int paidShipperTMonth) {
        this.paidShipperTMonth = paidShipperTMonth;
    }

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
