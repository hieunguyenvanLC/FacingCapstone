package capstone.fps.model.store;

import com.google.gson.annotations.Expose;


public class MdlProduct {
    @Expose
    Integer id;
    @Expose
    Integer storeId;
    @Expose
    String storeName;
    @Expose
    String address;
    @Expose
    String name;
    @Expose
    String image;
    @Expose
    Double price;
    @Expose
    String description;
    @Expose
    Double rating;
    @Expose
    Integer ratingCount;
    @Expose
    Long createTime;
    @Expose
    Long updateTime;
    @Expose
    Long deleteTime;
    @Expose
    String note;
    @Expose
    Integer status;
    @Expose
    String editor;


}
