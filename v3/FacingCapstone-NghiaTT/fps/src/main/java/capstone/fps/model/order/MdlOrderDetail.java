package capstone.fps.model.order;

import capstone.fps.model.account.MdlAccountSimple;
import com.google.gson.annotations.Expose;

import java.util.List;

public class MdlOrderDetail {
    @Expose
    private Integer id;
    @Expose
    private Long bookTime;
    @Expose
    private MdlAccountSimple customer;
    @Expose
    private Double totalPrice;
    @Expose
    private Integer status;
    @Expose
    private List<MdlOrderProductDetail> products;
    @Expose
    private String shipAddress;

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

    public MdlAccountSimple getCustomer() {
        return customer;
    }

    public void setCustomer(MdlAccountSimple customer) {
        this.customer = customer;
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

    public List<MdlOrderProductDetail> getProducts() {
        return products;
    }

    public void setProducts(List<MdlOrderProductDetail> products) {
        this.products = products;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }
}
