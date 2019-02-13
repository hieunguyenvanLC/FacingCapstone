package capstone.fps.entity;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "fr_product", catalog = "fpsdb", schema = "fpsdb")
@XmlRootElement
public class FRProduct {

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
    @Column(name = "price")
    private Double price;
    @Expose
    @Column(name = "product_image")
    private byte[] productImage;
    @Expose
    @Column(name = "description", length = 300)
    private String description;
    @Expose
    @Column(name = "rating")
    private Integer rating;
    @Expose
    @Column(name = "customer_rating_number")
    private Integer ratingNumber;
    @Expose
    @ManyToOne
    @JoinColumn(name = "FR_Store_id")
    private FRStore store;
    @Expose
    @ManyToOne
    @JoinColumn(name = "FR_Product_Status_id")
    private FRProductStatus status;
    @Expose
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private Collection<FROrderDetail> orderDetailCollection;
    @Expose
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private Collection<FRProductRating> productRatingCollection;

    public FRProduct() {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public byte[] getProductImage() {
        return productImage;
    }

    public void setProductImage(byte[] productImage) {
        this.productImage = productImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getRatingNumber() {
        return ratingNumber;
    }

    public void setRatingNumber(Integer ratingNumber) {
        this.ratingNumber = ratingNumber;
    }

    public FRStore getStore() {
        return store;
    }

    public void setStore(FRStore store) {
        this.store = store;
    }

    public FRProductStatus getStatus() {
        return status;
    }

    public void setStatus(FRProductStatus status) {
        this.status = status;
    }
}
