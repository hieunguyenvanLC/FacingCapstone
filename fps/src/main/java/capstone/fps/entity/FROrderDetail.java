package capstone.fps.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "fr_order_detail", catalog = "fpsdb", schema = "fpsdb")
@XmlRootElement
public class FROrderDetail {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "FR_Order_id")
    private FROrder order;
    @ManyToOne
    @JoinColumn(name = "FR_Product_id")
    private FRProduct product;
    @Column(name = "unit_price")
    private Double unitPrice;
    @Column(name = "quantity")
    private Integer quantity;

    public FROrderDetail() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FROrder getOrder() {
        return order;
    }

    public void setOrder(FROrder order) {
        this.order = order;
    }

    public FRProduct getProduct() {
        return product;
    }

    public void setProduct(FRProduct product) {
        this.product = product;
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
}
