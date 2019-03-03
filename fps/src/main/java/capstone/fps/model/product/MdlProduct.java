package capstone.fps.model.product;

import capstone.fps.common.Methods;
import capstone.fps.entity.FRAccount;
import capstone.fps.entity.FRProduct;
import capstone.fps.entity.FRStore;
import capstone.fps.model.store.MdlStore;
import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


public class MdlProduct {
    @Expose
    private Integer id;
    @Expose
    private int storeId;
    @Expose
    private String name;
    @Expose
    private String image;
    @Expose
    private Double price;
    @Expose
    private String description;
    @Expose
    private Double rating;
    @Expose
    private Integer ratingCount;
    @Expose
    private Long createTime;
    @Expose
    private Long updateTime;
    @Expose
    private Long deleteTime;
    @Expose
    private String note;
    @Expose
    private Integer status;
    @Expose
    private String editor;

    public MdlProduct() {
    }

    public MdlProduct(Integer id, int storeId, String name, String image, Double price, String description, Double rating, Integer ratingCount, Long createTime, Long updateTime, Long deleteTime, String note, Integer status, String editor) {
        this.id = id;
        this.storeId = storeId;
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.rating = rating;
        this.ratingCount = ratingCount;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.deleteTime = deleteTime;
        this.note = note;
        this.status = status;
        this.editor = editor;
    }

    public MdlProduct convertBig(FRProduct frProduct) {
        Methods methods = new Methods();
        MdlProduct mdlProduct = new MdlProduct();
        mdlProduct.id = frProduct.getId();
        mdlProduct.name = frProduct.getProductName();
        mdlProduct.price = frProduct.getPrice();
        mdlProduct.image = methods.bytesToBase64(frProduct.getProductImage());
        mdlProduct.description = frProduct.getDescription();
        mdlProduct.createTime = frProduct.getCreateTime();
        mdlProduct.note = frProduct.getNote();
        mdlProduct.status = frProduct.getStatus();
        return mdlProduct;
    }

    public MdlProduct convertProInSto(FRProduct frProduct) {
        MdlProduct mdlProduct = new MdlProduct();
        mdlProduct.id = frProduct.getId();
        mdlProduct.name = frProduct.getProductName();
        mdlProduct.price = frProduct.getPrice();
        mdlProduct.note = frProduct.getNote();
        mdlProduct.status = frProduct.getStatus();
        return mdlProduct;
    }


}
