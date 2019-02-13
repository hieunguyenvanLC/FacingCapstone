package capstone.fps.entity;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "fr_shipper", catalog = "fpsdb", schema = "")
@XmlRootElement
public class FRShipper  implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Expose
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Expose
    @Column(name = "bike_registration", length = 100)
    private String bikeRegistration;
    @Expose
    @Column(name = "introduce", length = 300)
    private String introduce;
    @Expose
    @Column(name = "national_id_front_image")
    private byte[] nationalIdFrontImage;
    @Expose
    @Column(name = "national_id_back_image")
    private byte[] nationalIdBackImage;
    @Expose
    @Column(name = "bike_registration_front_image")
    private byte[] bikeRegistrationFrontImage;
    @Expose
    @Column(name = "bike_registration_back_image")
    private byte[] bikeRegistrationBackImage;
    @Expose
    @Column(name = "sum_revenue")
    private Double sumRevenue;
    @Expose
    @OneToOne
    @JoinColumn(name = "account_id")
    private FRAccount account;
    @Expose
    @ManyToOne
    @JoinColumn(name = "resource_id")
    private FRResource resource;
    @Expose
    @ManyToOne
    @JoinColumn(name = "price_id")
    private FRPrice price;
    @Expose
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shipper")
    private Collection<FRPaymentInformation> paymentInformationCollection;
    @Expose
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

    public String getBikeRegistration() {
        return bikeRegistration;
    }

    public void setBikeRegistration(String bikeRegistration) {
        this.bikeRegistration = bikeRegistration;
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

    public Double getSumRevenue() {
        return sumRevenue;
    }

    public void setSumRevenue(Double sumRevenue) {
        this.sumRevenue = sumRevenue;
    }

    public FRAccount getAccount() {
        return account;
    }

    public void setAccount(FRAccount account) {
        this.account = account;
    }

    public FRResource getResource() {
        return resource;
    }

    public void setResource(FRResource resource) {
        this.resource = resource;
    }

    public FRPrice getPrice() {
        return price;
    }

    public void setPrice(FRPrice price) {
        this.price = price;
    }

}
