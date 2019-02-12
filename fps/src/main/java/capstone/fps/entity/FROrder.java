package capstone.fps.entity;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "fr_order", catalog = "fpsdb", schema = "")
@XmlRootElement
public class FROrder {

    private static final long serialVersionUID = 1L;
    @Id
    @Expose
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Expose
    @Column(name = "order_code", length = 50)
    private String orderCode;
    @Expose
    @Column(name = "sum_money")
    private Double sumMoney;
    @Expose
    @Column(name = "time_book")
    private Date timeBook;
    @Expose
    @Column(name = "time_received")
    private Date timeReceived;
    @Expose
    @Column(name = "ship_earn")
    private Double shipEarn;
    @Expose
    @Column(name = "ship_address", length = 300)
    private String shipAddress;
    @Expose
    @Column(name = "customer_description", length = 300)
    private String customerDescription;
    @Expose
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private FRAccount customer;
    @Expose
    @ManyToOne
    @JoinColumn(name = "shipper_id")
    private FRShipper shipper;
    @Expose
    @ManyToOne
    @JoinColumn(name = "town_id")
    private FRShipper town;
    @Expose
    @ManyToOne
    @JoinColumn(name = "status_id")
    private FRStatus status;
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

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Double getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(Double sumMoney) {
        this.sumMoney = sumMoney;
    }

    public Date getTimeBook() {
        return timeBook;
    }

    public void setTimeBook(Date timeBook) {
        this.timeBook = timeBook;
    }

    public Date getTimeReceived() {
        return timeReceived;
    }

    public void setTimeReceived(Date timeReceived) {
        this.timeReceived = timeReceived;
    }

    public Double getShipEarn() {
        return shipEarn;
    }

    public void setShipEarn(Double shipEarn) {
        this.shipEarn = shipEarn;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public String getCustomerDescription() {
        return customerDescription;
    }

    public void setCustomerDescription(String customerDescription) {
        this.customerDescription = customerDescription;
    }

    public FRAccount getCustomer() {
        return customer;
    }

    public void setCustomer(FRAccount customer) {
        this.customer = customer;
    }

    public FRShipper getShipper() {
        return shipper;
    }

    public void setShipper(FRShipper shipper) {
        this.shipper = shipper;
    }

    public FRShipper getTown() {
        return town;
    }

    public void setTown(FRShipper town) {
        this.town = town;
    }

    public FRStatus getStatus() {
        return status;
    }

    public void setStatus(FRStatus status) {
        this.status = status;
    }
}
