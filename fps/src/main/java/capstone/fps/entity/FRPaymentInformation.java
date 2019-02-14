package capstone.fps.entity;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "FR_Account_id")
    private FRAccount account;
    @ManyToOne
    @JoinColumn(name = "FR_Payment_Type_id")
    private FRPaymentType paymentType;
    @Column(name = "username", length = 100)
    private String username;
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
