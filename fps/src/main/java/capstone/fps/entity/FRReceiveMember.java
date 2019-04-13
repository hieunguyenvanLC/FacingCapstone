package capstone.fps.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "fr_receive_member", catalog = "fpsdb", schema = "fpsdb")
@XmlRootElement
public class FRReceiveMember {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "name", length = 100)
    private String name;
    @Column(name = "face")
    private byte[] face;
    @ManyToOne
    @JoinColumn(name = "FR_Account_id")
    private FRAccount account;

    public FRReceiveMember() {
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

    public byte[] getFace() {
        return face;
    }

    public void setFace(byte[] face) {
        this.face = face;
    }

    public FRAccount getAccount() {
        return account;
    }

    public void setAccount(FRAccount account) {
        this.account = account;
    }
}

