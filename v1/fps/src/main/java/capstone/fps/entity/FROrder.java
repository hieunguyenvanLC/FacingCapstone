package capstone.fps.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "fr_order", catalog = "fpsdb", schema = "fpsdb")
@XmlRootElement
public class FROrder {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "FR_Account_id")
    private FRAccount account;
    @ManyToOne
    @JoinColumn(name = "FR_Shipper_id")
    private FRShipper shipper;
    @Column(name = "order_code", length = 50)
    private String orderCode;
    @Column(name = "total_price")
    private Double totalPrice;
    @Column(name = "book_time")
    private Long bookTime;
    @Column(name = "receive_time")
    private Long receiveTime;
    @Column(name = "shipper_earn")
    private Double shipperEarn;
    @Column(name = "ship_address", length = 300)
    private String shipAddress;
    @ManyToOne
    @JoinColumn(name = "FR_District_id")
    private FRDistrict district;
    @Column(name = "customer_description", length = 300)
    private String customerDescription;
    @Column(name = "create_time")
    private Long createTime;
    @Column(name = "update_time")
    private Long updateTime;
    @Column(name = "delete_time")
    private Long deleteTime;
    @Column(name = "note", length = 300)
    private String note;
    @Column(name = "status")
    private Integer status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private Collection<FROrderDetail> orderDetailCollection;

    public FROrder() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FRAccount getAccount() {
        return account;
    }

    public void setAccount(FRAccount account) {
        this.account = account;
    }

    public FRShipper getShipper() {
        return shipper;
    }

    public void setShipper(FRShipper shipper) {
        this.shipper = shipper;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getBookTime() {
        return bookTime;
    }

    public void setBookTime(Long bookTime) {
        this.bookTime = bookTime;
    }

    public Long getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Long receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Double getShipperEarn() {
        return shipperEarn;
    }

    public void setShipperEarn(Double shipperEarn) {
        this.shipperEarn = shipperEarn;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public FRDistrict getDistrict() {
        return district;
    }

    public void setDistrict(FRDistrict district) {
        this.district = district;
    }

    public String getCustomerDescription() {
        return customerDescription;
    }

    public void setCustomerDescription(String customerDescription) {
        this.customerDescription = customerDescription;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Long deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
