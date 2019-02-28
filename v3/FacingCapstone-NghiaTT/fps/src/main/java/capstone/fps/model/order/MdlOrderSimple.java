package capstone.fps.model.order;

import com.google.gson.annotations.Expose;

public class MdlOrderSimple {
    @Expose
    private Integer id;
    @Expose
    private Long bookTime;
    @Expose
    private String customerName;
    @Expose
    private String shipperName;
    @Expose
    private Double totalPrice;
    @Expose
    private Integer status;
    @Expose
    private String orderCode;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getBookTime() {
        return bookTime;
    }

    public void setBookTime(Long bookTime) {
        this.bookTime = bookTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
