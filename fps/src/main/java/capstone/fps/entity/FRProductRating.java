package capstone.fps.entity;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "fr_product_rating", catalog = "fpsdb", schema = "fpsdb")
@XmlRootElement
public class FRProductRating {

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
    @JoinColumn(name = "FR_Product_id")
    private FRProduct product;
    @Expose
    @Column(name = "rating")
    private Integer rating;

    public FRProductRating() {
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
