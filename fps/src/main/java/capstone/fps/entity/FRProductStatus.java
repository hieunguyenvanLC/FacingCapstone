package capstone.fps.entity;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@Entity
@Table(name = "fr_product_status", catalog = "fpsdb", schema = "")
@XmlRootElement
public class FRProductStatus {

    private static final long serialVersionUID = 1L;
    @Id
    @Expose
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Expose
    @Column(name = "description", length = 300)
    private String description;
    @Expose
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status")
    private Collection<FRProduct> statusCollection;


}
