package capstone.fps.entity;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Table(name = "fr_account", catalog = "fpsdb", schema = "fpsdb")
@XmlRootElement
public class FRAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Expose
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Expose
    @Column(name = "phone_number")
    private Double phone;
    @Expose
    @ManyToOne
    @JoinColumn(name = "FR_Role_id")
    private FRRole role;
    @Expose
    @Column(name = "password", length = 300)
    private String password;
    @Expose
    @Column(name = "name", length = 300)
    private String name;
    @Expose
    @Column(name = "email", length = 100)
    private String email;
    @Expose
    @Column(name = "extra_point")
    private Integer extraPoint;
    @Expose
    @Column(name = "report_point")
    private Integer reportPoint;
    @Expose
    @Column(name = "user_image")
    private byte[] userImage;
    @Expose
    @Column(name = "national_id", length = 50)
    private String nationalId;
    @Expose
    @Column(name = "national_id_created_date")
    private Date nationalIdCreatedDate;
    @Expose
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Expose
    @Column(name = "hash_key", length = 100)
    private String hashKey;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private Collection<FRPaymentInformation> paymentInformationCollection;
    @Expose
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "account")
    private FRShipper shipper;
    @Expose
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private Collection<FROrder> orderCollection;
    @Expose
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private Collection<FRProductRating> productRatingCollection;
    @Expose
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private Collection<FRStoreRating> storeRatingCollection;


    public FRAccount() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPhone() {
        return phone;
    }

    public void setPhone(Double phone) {
        this.phone = phone;
    }

    public FRRole getRole() {
        return role;
    }

    public void setRole(FRRole role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getExtraPoint() {
        return extraPoint;
    }

    public void setExtraPoint(Integer extraPoint) {
        this.extraPoint = extraPoint;
    }

    public Integer getReportPoint() {
        return reportPoint;
    }

    public void setReportPoint(Integer reportPoint) {
        this.reportPoint = reportPoint;
    }

    public byte[] getUserImage() {
        return userImage;
    }

    public void setUserImage(byte[] userImage) {
        this.userImage = userImage;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public Date getNationalIdCreatedDate() {
        return nationalIdCreatedDate;
    }

    public void setNationalIdCreatedDate(Date nationalIdCreatedDate) {
        this.nationalIdCreatedDate = nationalIdCreatedDate;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
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
