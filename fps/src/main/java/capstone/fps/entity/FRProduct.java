package capstone.fps.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "fr_product", catalog = "fpsdb", schema = "fpsdb")
@XmlRootElement
public class FRProduct {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "FR_Store_id")
    private FRStore store;
    @Column(name = "product_name", length = 300)
    private String productName;
    @Column(name = "product_image")
    private byte[] productImage;
    @Column(name = "price")
    private Double price;
    @Column(name = "description", length = 300)
    private String description;
    @Column(name = "create_time")
    private Long createTime;
    @Column(name = "update_time")
    private Long updateTime;
    @Column(name = "delete_time")
    private Long deleteTime;
    @Column(name = "note", length = 300)
    private String note;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Collection<FROrderDetail> getOrderDetailCollection() {
        return orderDetailCollection;
    }

    public void setOrderDetailCollection(Collection<FROrderDetail> orderDetailCollection) {
        this.orderDetailCollection = orderDetailCollection;
    }

    public FRProduct(FRStore store, String productName, byte[] productImage, Double price, String description, Long createTime, Long updateTime, Long deleteTime, String note, Integer status, FRAccount editor, Collection<FROrderDetail> orderDetailCollection) {
        this.store = store;
        this.productName = productName;
        this.productImage = productImage;
        this.price = price;
        this.description = description;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.deleteTime = deleteTime;
        this.note = note;
        this.status = status;
        this.editor = editor;
        this.orderDetailCollection = orderDetailCollection;
    }

    @Column(name = "status")
    private Integer status;
    @ManyToOne
    @JoinColumn(name = "editor_id")
    private FRAccount editor;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private Collection<FROrderDetail> orderDetailCollection;

    public FRProduct() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FRStore getStore() {
        return store;
    }

    public void setStore(FRStore store) {
        this.store = store;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public byte[] getProductImage() {
        return productImage;
    }

    public void setProductImage(byte[] productImage) {
        this.productImage = productImage;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Long deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public FRAccount getEditor() {
        return editor;
    }

    public void setEditor(FRAccount editor) {
        this.editor = editor;
    }
}
