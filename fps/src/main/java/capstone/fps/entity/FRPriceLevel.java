package capstone.fps.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@Entity
@Table(name = "fr_price_level", catalog = "fpsdb", schema = "fpsdb")
@XmlRootElement
public class FRPriceLevel {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "level_name", length = 100)
    private String levelName;
    @Column(name = "description", length = 300)
    private String description;
    @Column(name = "price")
    private Double price;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "priceLevel")
    private Collection<FRShipper> shipperCollection;

    public FRPriceLevel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
