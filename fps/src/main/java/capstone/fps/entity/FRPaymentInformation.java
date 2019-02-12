package capstone.fps.entity;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "fr_payment_information", catalog = "fpsdb", schema = "")
@XmlRootElement
public class FRPaymentInformation  implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Expose
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Expose
    @Column(name = "username", length = 100)
    private String username;
    @Expose
    @Column(name = "password", length = 100)
    private String password;
    @Expose
    @ManyToOne
    @JoinColumn(name = "payment_type_id")
    private FRPaymentType paymentType;
    @Expose
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private FRAccount customer;
    @Expose
    @ManyToOne
    @JoinColumn(name = "shipper_id")
    private FRShipper shipper;


    public FRPaymentInformation() {
    }

    public FRPaymentInformation(Integer id) {
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

    public FRPaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(FRPaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
