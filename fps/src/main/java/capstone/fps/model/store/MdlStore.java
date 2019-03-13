package capstone.fps.model.store;

import capstone.fps.model.product.MdlProduct;
import com.google.gson.annotations.Expose;

import java.util.List;

public class MdlStore {
    @Expose
    Integer id;
    @Expose
    String name;
    @Expose
    String address;
    @Expose
    int distId;
    @Expose
    Double longitude;
    @Expose
    Double latitude;
    @Expose
    String image;
    @Expose
    Double rating;
    @Expose
    String phone;
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
    @Expose
    List<MdlProduct> proList;
    
}
