package capstone.fps.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "fr_store", catalog = "fpsdb", schema = "fpsdb")
@XmlRootElement
public class FRStore {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "store_name", length = 300)
    private String storeName;
    @Column(name = "address", length = 300)
    private String address;
    @ManyToOne
    @JoinColumn(name = "FR_District_id")
    private FRDistrict district;
    @Column(name = "store_image")
    private byte[] storeImage;
    @Column(name = "rating")
    private Double rating;
    @Column(name = "rating_count")
    private Integer ratingCount;
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "store")
    private Collection<FRSchedule> scheduleCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "store")
    private Collection<FRProduct> productCollection;

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

    public FRDistrict getDistrict() {
        return district;
    }

    public void setDistrict(FRDistrict district) {
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

    public Integer getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
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
}
