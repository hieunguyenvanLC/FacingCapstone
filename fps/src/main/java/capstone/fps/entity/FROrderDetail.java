package capstone.fps.entity;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "fr_order_detail", catalog = "fpsdb", schema = "")
@XmlRootElement
public class FROrderDetail {
    private static final long serialVersionUID = 1L;
    @Id
    @Expose
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Expose
    @Column(name = "product_name", length = 300)
    private String productName;
    @Expose
    @Column(name = "unit_price")
    private Double unitPrice;
    @Expose
    @Column(name = "quantity")
    private Integer quantity;
    @Expose
    @ManyToOne
    @JoinColumn(name = "product_id")
    private FRProduct product;
    @Expose
    @ManyToOne
    @JoinColumn(name = "order_id")
    private FROrder order;

    public FROrderDetail() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public FRProduct getProduct() {
        return product;
    }

    public void setProduct(FRProduct product) {
        this.product = product;
    }

    public FROrder getOrder() {
        return order;
    }

    public void setOrder(FROrder order) {
        this.order = order;
    }
}
