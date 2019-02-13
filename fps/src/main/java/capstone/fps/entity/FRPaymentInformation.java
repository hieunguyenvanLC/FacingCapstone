package capstone.fps.entity;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "fr_payment_information", catalog = "fpsdb", schema = "fpsdb")
@XmlRootElement
public class FRPaymentInformation  implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Expose
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Expose
    @ManyToOne
    @JoinColumn(name = "FR_Account_id")
    private FRAccount account;
    @Expose
    @ManyToOne
    @JoinColumn(name = "FR_Payment_Type_id")
    private FRPaymentType paymentType;
    @Expose
    @ManyToOne
    @JoinColumn(name = "FR_Shipper_id")
    private FRShipper shipper;
    @Expose
    @Column(name = "username", length = 100)
    private String username;
    @Expose
    @Column(name = "password", length = 300)
    private String password;

    public FRPaymentInformation() {
    }

    public FRPaymentInformation(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FRAccount getAccount() {
        return account;
    }

    public void setAccount(FRAccount account) {
        this.account = account;
    }

    public FRPaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(FRPaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public FRShipper getShipper() {
        return shipper;
    }

    public void setShipper(FRShipper shipper) {
        this.shipper = shipper;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
