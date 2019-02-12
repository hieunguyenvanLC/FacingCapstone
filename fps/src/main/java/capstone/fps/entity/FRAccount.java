package capstone.fps.entity;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Table(name = "fr_acount", catalog = "fpsdb", schema = "")
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
    @Column(name = "password", length = 100)
    private String password;
    @Expose
    @Column(name = "account_name", length = 100)
    private String accountName;
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
    @Column(name = "customer_image")
    private byte[] customerImage;
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

    public FRAccount() {
    }

    public FRAccount(Integer id) {
        this.id = id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
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

    public byte[] getCustomerImage() {
        return customerImage;
    }

    public void setCustomerImage(byte[] customerImage) {
        this.customerImage = customerImage;
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

    public FRRole getRoleId() {
        return roleId;
    }

    public void setRoleId(FRRole roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "FRAccount{" +
                "id=" + id +
                ", phone=" + phone +
                ", password='" + password + '\'' +
                ", accountName='" + accountName + '\'' +
                ", email='" + email + '\'' +
                ", extraPoint=" + extraPoint +
                ", reportPoint=" + reportPoint +
                ", customerImage=" + Arrays.toString(customerImage) +
                ", nationalId='" + nationalId + '\'' +
                ", nationalIdCreatedDate=" + nationalIdCreatedDate +
                ", dateOfBirth=" + dateOfBirth +
                ", roleId=" + roleId +
                '}';
    }
}
