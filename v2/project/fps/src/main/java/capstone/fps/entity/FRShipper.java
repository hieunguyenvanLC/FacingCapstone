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
    @Column(name = "bike_registration_id", length = 100)
    private String bikeRegistrationId;
    @Column(name = "introduce", length = 300)
    private String introduce;
    @Column(name = "national_id_front_image")
    private byte[] nationalIdFrontImage;
    @Column(name = "national_id_back_image")
    private byte[] nationalIdBackImage;
    @Column(name = "sum_revenue")
    private Double sumRevenue;
    @ManyToOne
    @JoinColumn(name = "FR_Source_id")
    private FRSource source;
    @Column(name = "bike_registration_front_image")
    private byte[] bikeRegistrationFrontImage;
    @Column(name = "bike_registration_back_image")
    private byte[] bikeRegistrationBackImage;
    @OneToOne
    @JoinColumn(name = "FR_Account_id")
    private FRAccount account;
    @ManyToOne
    @JoinColumn(name = "FR_Price_Level_id")
    private FRPriceLevel priceLevel;

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

    public String getBikeRegistrationId() {
        return bikeRegistrationId;
    }

    public void setBikeRegistrationId(String bikeRegistrationId) {
        this.bikeRegistrationId = bikeRegistrationId;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public byte[] getNationalIdFrontImage() {
        return nationalIdFrontImage;
    }

    public void setNationalIdFrontImage(byte[] nationalIdFrontImage) {
        this.nationalIdFrontImage = nationalIdFrontImage;
    }

    public byte[] getNationalIdBackImage() {
        return nationalIdBackImage;
    }

    public void setNationalIdBackImage(byte[] nationalIdBackImage) {
        this.nationalIdBackImage = nationalIdBackImage;
    }

    public Double getSumRevenue() {
        return sumRevenue;
    }

    public void setSumRevenue(Double sumRevenue) {
        this.sumRevenue = sumRevenue;
    }

    public FRSource getSource() {
        return source;
    }

    public void setSource(FRSource source) {
        this.source = source;
    }

    public byte[] getBikeRegistrationFrontImage() {
        return bikeRegistrationFrontImage;
    }

    public void setBikeRegistrationFrontImage(byte[] bikeRegistrationFrontImage) {
        this.bikeRegistrationFrontImage = bikeRegistrationFrontImage;
    }

    public byte[] getBikeRegistrationBackImage() {
        return bikeRegistrationBackImage;
    }

    public void setBikeRegistrationBackImage(byte[] bikeRegistrationBackImage) {
        this.bikeRegistrationBackImage = bikeRegistrationBackImage;
    }

    public FRAccount getAccount() {
        return account;
    }

    public void setAccount(FRAccount account) {
        this.account = account;
    }

    public FRPriceLevel getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(FRPriceLevel priceLevel) {
        this.priceLevel = priceLevel;
    }
}
