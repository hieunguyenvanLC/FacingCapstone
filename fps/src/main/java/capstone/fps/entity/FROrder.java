package capstone.fps.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
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
    @Column(name = "buyer_face")
    private byte[] buyerFace;
    @Column(name = "bill")
    private byte[] bill;
    @Column(name = "order_code", length = 50)
    private String orderCode;
    @Column(name = "total_price")
    private Double totalPrice;
    @Column(name = "buy_time")
    private Long buyTime;
    @Column(name = "receive_time")
    private Long receiveTime;
    @Column(name = "shipper_earn")
    private Double shipperEarn;
    @Column(name = "price_level")
    private Double priceLevel;
    @Column(name = "ship_address", length = 300)
    private String shipAddress;
    @ManyToOne
    @JoinColumn(name = "FR_District_id")
    private FRDistrict district;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "latitude")
    private Double latitude;
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
    @ManyToOne
    @JoinColumn(name = "editor_id")
    private FRAccount editor;
    @Column(name = "buyer_token", length = 100)
    private String buyerToken;

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

    public Long getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Long bookTime) {
        this.buyTime = bookTime;
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

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
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

    public FRAccount getEditor() {
        return editor;
    }

    public void setEditor(FRAccount editor) {
        this.editor = editor;
    }

    public byte[] getBill() {
        return bill;
    }

    public void setBill(byte[] bill) {
        this.bill = bill;
    }

    public byte[] getBuyerFace() {
        return buyerFace;
    }

    public void setBuyerFace(byte[] buyerFace) {
        this.buyerFace = buyerFace;
    }

    public String getBuyerToken() {
        return buyerToken;
    }

    public void setBuyerToken(String buyerToken) {
        this.buyerToken = buyerToken;
    }

    public Double getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(Double priceLevel) {
        this.priceLevel = priceLevel;
    }
}
