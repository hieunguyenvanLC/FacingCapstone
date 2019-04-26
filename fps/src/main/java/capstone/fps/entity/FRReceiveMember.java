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
    @Column(name = "face1")
    private byte[] face1;
    @Column(name = "face2")
    private byte[] face2;
    @Column(name = "face3")
    private byte[] face3;
    @Column(name = "id")
    private Integer removePointer;
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

    public byte[] getFace1() {
        return face1;
    }

    public void setFace1(byte[] face1) {
        this.face1 = face1;
    }

    public byte[] getFace2() {
        return face2;
    }

    public void setFace2(byte[] face2) {
        this.face2 = face2;
    }

    public byte[] getFace3() {
        return face3;
    }

    public void setFace3(byte[] face3) {
        this.face3 = face3;
    }

    public Integer getRemovePointer() {
        return removePointer;
    }

    public void setRemovePointer(Integer removePointer) {
        this.removePointer = removePointer;
    }

    public FRAccount getAccount() {
        return account;
    }

    public void setAccount(FRAccount account) {
        this.account = account;
    }
}

