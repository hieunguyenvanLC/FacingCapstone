package capstone.fps.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "fr_account", catalog = "fpsdb", schema = "fpsdb")
@XmlRootElement
public class FRAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "phone_number")
    private Double phone;
    @ManyToOne
    @JoinColumn(name = "FR_Role_id")
    private FRRole role;
    @Column(name = "password", length = 300)
    private String password;
    @Column(name = "name", length = 300)
    private String name;
    @Column(name = "email", length = 100)
    private String email;
    @Column(name = "extra_point")
    private Integer extraPoint;
    @Column(name = "report_point")
    private Integer reportPoint;
    @Column(name = "user_image")
    private byte[] userImage;
    @Column(name = "national_id", length = 50)
    private String nationalId;
    @Column(name = "national_id_created_date")
    private Date nationalIdCreatedDate;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column(name = "hash_key", length = 100)
    private String hashKey;
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

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "account")
    private FRShipper shipper;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private Collection<FRPaymentInformation> paymentInformationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private Collection<FROrder> orderCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private Collection<FRRating> ratingCollection;

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

    public FRShipper getShipper() {
        return shipper;
    }

    public void setShipper(FRShipper shipper) {
        this.shipper = shipper;
    }
}
