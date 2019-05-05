package capstone.fps.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@Entity
@Table(name = "fr_district", catalog = "fpsdb", schema = "fpsdb")
@XmlRootElement
public class FRDistrict {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "name", length = 300)
    private String name;
    @ManyToOne
    @JoinColumn(name = "FR_City_id")
    private FRCity city;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "district")
//    private Collection<FROrder> orderCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "district")
    private Collection<FRStore> storeCollection;

    public FRDistrict() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FRCity getCity() {
        return city;
    }

    public void setCity(FRCity city) {
        this.city = city;
    }
}
