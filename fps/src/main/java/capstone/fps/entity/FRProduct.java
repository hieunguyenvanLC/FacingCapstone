package capstone.fps.entity;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "FR_Store_id")
    private FRStore store;
    @Column(name = "product_name", length = 300)
    private String productName;
    @Column(name = "product_image")
    private byte[] productImage;
    @Column(name = "price")
    private Double price;
    @Column(name = "description", length = 300)
    private String description;
    @Column(name = "rating")
    private Integer rating;
    @Column(name = "rating_count")
    private Integer ratingCount;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "delete_time")
    private Date deleteTime;
    @Column(name = "note", length = 300)
    private String note;
    @Column(name = "status")
    private Integer status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private Collection<FROrderDetail> orderDetailCollection;

    public FRProduct() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FRStore getStore() {
        return store;
    }

    public void setStore(FRStore store) {
        this.store = store;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public byte[] getProductImage() {
        return productImage;
    }

    public void setProductImage(byte[] productImage) {
        this.productImage = productImage;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public Integer getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
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

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
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
