package capstone.fps.entity;

import capstone.fps.common.Fix;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "fr_account", catalog = "fpsdb", schema = "fpsdb")
@XmlRootElement
public class FRAccount implements UserDetails {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "phone_number")
    private String phone;
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
    private String natId;
    @Column(name = "national_id_created_date")
    private Long natDate;
    @Column(name = "date_of_birth")
    private Long dob;
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
    @ManyToOne
    @JoinColumn(name = "editor_id")
    private FRAccount editor;
    @Column(name = "avatar")
    private byte[] avatar;



    @OneToOne(cascade = CascadeType.ALL, mappedBy = "account")
    private FRShipper shipper;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private Collection<FRPaymentInformation> paymentInformationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private Collection<FROrder> orderCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private Collection<FRReceiveMember> receiveMemberCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private Collection<FRRating> ratingCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "editor")
    private Collection<FRAccount> accountCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "editor")
    private Collection<FRStore> storeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "editor")
    private Collection<FRProduct> productCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "editor")
    private Collection<FROrder> orderEditorCollection;

    public FRAccount() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public FRRole getRole() {
        return role;
    }

    public void setRole(FRRole role) {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getName()));
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return String.valueOf(phone);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status != Fix.ACC_BAN.index;
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

    public String getNatId() {
        return natId;
    }

    public void setNatId(String nationalId) {
        this.natId = nationalId;
    }

    public Long getNatDate() {
        return natDate;
    }

    public void setNatDate(Long nationalIdCreatedDate) {
        this.natDate = nationalIdCreatedDate;
    }

    public Long getDob() {
        return dob;
    }

    public void setDob(Long dateOfBirth) {
        this.dob = dateOfBirth;
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

    public FRShipper getShipper() {
        return shipper;
    }

    public void setShipper(FRShipper shipper) {
        this.shipper = shipper;
    }

    public FRAccount getEditor() {
        return editor;
    }

    public void setEditor(FRAccount editor) {
        this.editor = editor;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
}
