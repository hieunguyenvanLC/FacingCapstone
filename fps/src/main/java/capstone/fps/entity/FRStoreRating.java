package capstone.fps.entity;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "fr_store_rating", catalog = "fpsdb", schema = "fpsdb")
@XmlRootElement
public class FRStoreRating {

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
    @JoinColumn(name = "FR_Store_id")
    private FRStore store;
    @Expose
    @Column(name = "rating")
    private Integer rating;

    public FRStoreRating() {
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

    public FRStore getStore() {
        return store;
    }

    public void setStore(FRStore store) {
        this.store = store;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
