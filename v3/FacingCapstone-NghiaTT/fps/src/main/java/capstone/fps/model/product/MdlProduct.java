package capstone.fps.model.product;

import capstone.fps.entity.FRAccount;
import capstone.fps.entity.FRStore;
import com.google.gson.annotations.Expose;


public class MdlProduct {
    @Expose
    private Integer id;

    @Expose
    private String store;

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

    public MdlProduct(Integer id, String name, String image, Double price, String description, String note, Integer status) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.note = note;
        this.status = status;
    }
}
