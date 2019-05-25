package capstone.fps.model.account;

import com.google.gson.annotations.Expose;

import java.util.List;

public class MdlMember {

    @Expose
    Integer id;
    @Expose
    String phone;
    @Expose
    String role;
    @Expose
    String password;
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
    Long dob;
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
    String avatar;
    @Expose
    MdlFace[] faceList;
    @Expose
    Double wallet;
}
