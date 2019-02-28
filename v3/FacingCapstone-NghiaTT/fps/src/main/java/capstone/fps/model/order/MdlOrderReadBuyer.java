package capstone.fps.model.order;


public class MdlOrderReadBuyer {

    private long id;
    private long frAccountId;
    private long frShipperId;
    private String orderCode;
    private double totalPrice;
    private java.sql.Timestamp bookTime;
    private java.sql.Timestamp receiveTime;
    private double shipperEarn;
    private String shipAddress;
    private long frDistrictId;
    private String customerDescription;
    private long frStatusId;
    private java.sql.Timestamp createTime;
    private java.sql.Timestamp updateTime;
    private java.sql.Timestamp deactivateTime;
    private String note;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getFrAccountId() {
        return frAccountId;
    }

    public void setFrAccountId(long frAccountId) {
        this.frAccountId = frAccountId;
    }


    public long getFrShipperId() {
        return frShipperId;
    }

    public void setFrShipperId(long frShipperId) {
        this.frShipperId = frShipperId;
    }


    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }


    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


    public java.sql.Timestamp getBookTime() {
        return bookTime;
    }

    public void setBookTime(java.sql.Timestamp bookTime) {
        this.bookTime = bookTime;
    }


    public java.sql.Timestamp getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(java.sql.Timestamp receiveTime) {
        this.receiveTime = receiveTime;
    }


    public double getShipperEarn() {
        return shipperEarn;
    }

    public void setShipperEarn(double shipperEarn) {
        this.shipperEarn = shipperEarn;
    }


    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }


    public long getFrDistrictId() {
        return frDistrictId;
    }

    public void setFrDistrictId(long frDistrictId) {
        this.frDistrictId = frDistrictId;
    }


    public String getCustomerDescription() {
        return customerDescription;
    }

    public void setCustomerDescription(String customerDescription) {
        this.customerDescription = customerDescription;
    }


    public long getFrStatusId() {
        return frStatusId;
    }

    public void setFrStatusId(long frStatusId) {
        this.frStatusId = frStatusId;
    }


    public java.sql.Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.sql.Timestamp createTime) {
        this.createTime = createTime;
    }


    public java.sql.Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(java.sql.Timestamp updateTime) {
        this.updateTime = updateTime;
    }


    public java.sql.Timestamp getDeactivateTime() {
        return deactivateTime;
    }

    public void setDeactivateTime(java.sql.Timestamp deactivateTime) {
        this.deactivateTime = deactivateTime;
    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
