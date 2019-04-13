package capstone.fps.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "fr_shipper", catalog = "fpsdb", schema = "fpsdb")
@XmlRootElement
public class FRShipper implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "introduce", length = 300)
    private String introduce;
    @Column(name = "sum_revenue")
    private Double sumRevenue;
    @Column(name = "national_id_front_image")
    private byte[] natIdFrontImage;
    @Column(name = "national_id_back_image")
    private byte[] natIdBackImage;
    @Column(name = "bike_registration_id", length = 100)
    private String bikeRegId;
    @Column(name = "bike_registration_date")
    private Long bikeRegDate;
    @Column(name = "bike_registration_front_image")
    private byte[] bikeRegFront;
    @Column(name = "bike_registration_back_image")
    private byte[] bikeRegBack;
    @OneToOne
    @JoinColumn(name = "FR_Account_id")
    private FRAccount account;
    @ManyToOne
    @JoinColumn(name = "FR_Source_id")
    private FRSource source;
    @ManyToOne
    @JoinColumn(name = "FR_Price_Level_id")
    private FRPriceLevel priceLevel;
    @Column(name = "rating")
    private Double rating;
    @Column(name = "rating_count")
    private Integer ratingCount;
    @Column(name = "order_count")
    private Integer orderCount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shipper")
    private Collection<FROrder> orderCollection;

    public FRShipper() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Double getSumRevenue() {
        return sumRevenue;
    }

    public void setSumRevenue(Double sumRevenue) {
        this.sumRevenue = sumRevenue;
    }

    public byte[] getNatIdFrontImage() {
        return natIdFrontImage;
    }

    public void setNatIdFrontImage(byte[] nationalIdFrontImage) {
        this.natIdFrontImage = nationalIdFrontImage;
    }

    public byte[] getNatIdBackImage() {
        return natIdBackImage;
    }

    public void setNatIdBackImage(byte[] nationalIdBackImage) {
        this.natIdBackImage = nationalIdBackImage;
    }

    public String getBikeRegId() {
        return bikeRegId;
    }

    public void setBikeRegId(String bikeRegId) {
        this.bikeRegId = bikeRegId;
    }

    public Long getBikeRegDate() {
        return bikeRegDate;
    }

    public void setBikeRegDate(Long bikeRegDate) {
        this.bikeRegDate = bikeRegDate;
    }

    public byte[] getBikeRegFront() {
        return bikeRegFront;
    }

    public void setBikeRegFront(byte[] bikeRegFront) {
        this.bikeRegFront = bikeRegFront;
    }

    public byte[] getBikeRegBack() {
        return bikeRegBack;
    }

    public void setBikeRegBack(byte[] bikeRegBack) {
        this.bikeRegBack = bikeRegBack;
    }

    public FRAccount getAccount() {
        return account;
    }

    public void setAccount(FRAccount account) {
        this.account = account;
    }

    public FRSource getSource() {
        return source;
    }

    public void setSource(FRSource source) {
        this.source = source;
    }

    public FRPriceLevel getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(FRPriceLevel priceLevel) {
        this.priceLevel = priceLevel;
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

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }
}
