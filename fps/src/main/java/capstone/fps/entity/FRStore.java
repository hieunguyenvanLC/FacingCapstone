package capstone.fps.entity;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "fr_store", catalog = "fpsdb", schema = "fpsdb")
@XmlRootElement
public class FRStore {
    private static final long serialVersionUID = 1L;
    @Id
    @Expose
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Expose
    @Column(name = "store_name", length = 300)
    private String storeName;
    @Expose
    @Column(name = "address", length = 300)
    private String address;
    @Expose
    @ManyToOne
    @JoinColumn(name = "FR_District_id")
    private FRShipper district;
    @Expose
    @Column(name = "store_image")
    private byte[] storeImage;
    @Expose
    @Column(name = "rating")
    private Double rating;
    @Expose
    @Column(name = "rating_count")
    private Integer ratingNumber;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "store")
    private Collection<FRSchedule> scheduleCollection;
    @Expose
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "store")
    private Collection<FRProduct> productCollection;
    @Expose
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "store")
    private Collection<FRStoreRating> storeRatingCollection;

    public FRStore() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public FRShipper getDistrict() {
        return district;
    }

    public void setDistrict(FRShipper district) {
        this.district = district;
    }

    public byte[] getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(byte[] storeImage) {
        this.storeImage = storeImage;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getRatingNumber() {
        return ratingNumber;
    }

    public void setRatingNumber(Integer ratingNumber) {
        this.ratingNumber = ratingNumber;
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
