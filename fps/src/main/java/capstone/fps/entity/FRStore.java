package capstone.fps.entity;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "fr_store", catalog = "fpsdb", schema = "")
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
    @Column(name = "rating")
    private Integer rating;
    @Expose
    @Column(name = "open_time")
    private Date openTime;
    @Expose
    @Column(name = "store_image")
    private byte[] storeImage;
    @Expose
    @Column(name = "customer_rating_number")
    private Integer ratingNumber;


    @Expose
    @Column(name = "phone_number")
    private Double phone;

    @Expose
    @Column(name = "email", length = 100)
    private String email;

    @Expose
    @Column(name = "report_point")
    private Integer reportPoint;
    @Expose
    @Column(name = "customer_image")
    private byte[] customerImage;
    @Expose
    @Column(name = "national_id", length = 50)
    private String nationalId;
    @Expose
    @Column(name = "national_id_created_date")
    private Date nationalIdCreatedDate;

    @Expose
    @ManyToOne
    @JoinColumn(name = "role_id")
    private FRRole roleId;
    @Expose
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Collection<FRPaymentInformation> paymentInformationCollection;
    @Expose
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "account")
    private FRShipper shipper;
    @Expose
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Collection<FROrder> orderCollection;

}
