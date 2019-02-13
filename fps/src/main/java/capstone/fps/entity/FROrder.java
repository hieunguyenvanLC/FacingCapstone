package capstone.fps.entity;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "fr_order", catalog = "fpsdb", schema = "fpsdb")
@XmlRootElement
public class FROrder {

    private static final long serialVersionUID = 1L;
    @Id
    @Expose
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Expose
    @ManyToOne
    @JoinColumn(name = "FR_Account_id")
    private FRAccount account;
    @Expose
    @ManyToOne
    @JoinColumn(name = "FR_Shipper_id")
    private FRShipper shipper;
    @Expose
    @Column(name = "order_code", length = 50)
    private String orderCode;
    @Expose
    @Column(name = "total_price")
    private Double totalPrice;
    @Expose
    @Column(name = "book_time")
    private Date bookTime;
    @Expose
    @Column(name = "receive_time")
    private Date receiveTime;
    @Expose
    @Column(name = "shipper_earn")
    private Double shipperEarn;
    @Expose
    @Column(name = "ship_address", length = 300)
    private String shipAddress;
    @Expose
    @ManyToOne
    @JoinColumn(name = "FR_District_id")
    private FRShipper district;
    @Expose
    @Column(name = "customer_description", length = 300)
    private String customerDescription;
    @Expose
    @ManyToOne
    @JoinColumn(name = "FR_Status_id")
    private FRStatus status;
    @Expose
    @Column(name = "create_time")
    private Date createTime;
    @Expose
    @Column(name = "update_time")
    private Date updateTime;
    @Expose
    @Column(name = "deactivate_time")
    private Date deactivateTime;
    @Expose
    @Column(name = "note", length = 300)
    private String note;
    @Expose
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

    public Date getBookTime() {
        return bookTime;
    }

    public void setBookTime(Date bookTime) {
        this.bookTime = bookTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
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

    public FRShipper getDistrict() {
        return district;
    }

    public void setDistrict(FRShipper district) {
        this.district = district;
    }

    public String getCustomerDescription() {
        return customerDescription;
    }

    public void setCustomerDescription(String customerDescription) {
        this.customerDescription = customerDescription;
    }

    public FRStatus getStatus() {
        return status;
    }

    public void setStatus(FRStatus status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getDeactivateTime() {
        return deactivateTime;
    }

    public void setDeactivateTime(Date deactivateTime) {
        this.deactivateTime = deactivateTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
