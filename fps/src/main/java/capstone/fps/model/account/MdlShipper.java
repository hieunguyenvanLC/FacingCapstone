package capstone.fps.model.account;

import com.google.gson.annotations.Expose;

public class MdlShipper {

    @Expose
    Integer id;
    @Expose
    String phone;
    @Expose
    String name;
    @Expose
    String email;
    @Expose
    Integer extraPoint;
    @Expose
    Integer reportPoint;
    @Expose
    String userImage;
    @Expose
    String natId;
    @Expose
    Long natDate;
    @Expose
    Long dateOfBirth;
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
    String avatar;
    @Expose
    String editor;


    @Expose
    String bikeRegId;
    @Expose
    Long bikeRegDate;
    @Expose
    String introduce;
    @Expose
    String natFront;
    @Expose
    String natBack;
    @Expose
    Double sumRevenue;
    @Expose
    int sourceId;
    @Expose
    String bikeRegFront;
    @Expose
    String bikeRegBack;
    @Expose
    int priceLevelId;
    @Expose
    double rating;
    @Expose
    int orderCount;
}
