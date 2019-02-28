package capstone.fps.model.store;

import capstone.fps.model.product.MdlProInSto;
import com.google.gson.annotations.Expose;

import java.util.List;

public class MdlAdmStoOne {
    @Expose
    private long id;
    @Expose
    private String name;
    @Expose
    private String address;
    @Expose
    private long distId;
    @Expose
    private Double longitude;
    @Expose
    private Double latitude;
    @Expose
    private String phone;
    @Expose
    private String storeImage;
    @Expose
    private Double rating;
    @Expose
    private long ratingCount;
    @Expose
    private Long createTime;
    @Expose
    private Long updateTime;
    @Expose
    private Long deleteTime;
    @Expose
    private String note;
    @Expose
    private int status;
    @Expose
    private String editor;
    @Expose
    private List<MdlProInSto> proList;

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDistId(long distId) {
        this.distId = distId;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setStoreImage(String storeImage) {
        this.storeImage = storeImage;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setRatingCount(long ratingCount) {
        this.ratingCount = ratingCount;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public void setDeleteTime(Long deleteTime) {
        this.deleteTime = deleteTime;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public void setProList(List<MdlProInSto> proList) {
        this.proList = proList;
    }
}
