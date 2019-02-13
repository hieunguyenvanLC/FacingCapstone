package capstone.fps.entity;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "fr_role", catalog = "fpsdb", schema = "fpsdb")
@XmlRootElement
public class FRRole implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Expose
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Expose
    @Column(name = "name", length = 50)
    private String name;
    @Expose
    @Column(name = "description", length = 300)
    private String description;
    @Expose
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
    private Collection<FRAccount> accountCollection;

    public FRRole() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
