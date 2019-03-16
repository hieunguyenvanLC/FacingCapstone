package capstone.fps.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "fr_product_rating", catalog = "fpsdb", schema = "fpsdb")
@XmlRootElement
public class FRRating {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "FR_Account_id")
    private FRAccount account;
    @ManyToOne
    @JoinColumn(name = "FR_Store_id")
    private FRStore store;
    @ManyToOne
    @JoinColumn(name = "FR_Product_id")
    private FRProduct product;
    @Column(name = "rating")
    private Integer rating;

    public FRRating() {
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

    public FRStore getStore() {
        return store;
    }

    public void setStore(FRStore store) {
        this.store = store;
    }

    public FRProduct getProduct() {
        return product;
    }

    public void setProduct(FRProduct product) {
        this.product = product;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
