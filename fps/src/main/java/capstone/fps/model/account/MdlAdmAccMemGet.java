package capstone.fps.model.account;

import com.google.gson.annotations.Expose;

public class MdlAdmAccMemGet {
    @Expose
    private Integer id;

    @Expose
    private String phone;

    @Expose
    private String name;

    @Expose
    private String email;

    @Expose
    private String userImg;

    @Expose
    private Integer extraPoint;

    @Expose
    private Integer reportPoint;

    @Expose
    private Long dob;

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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public void setExtraPoint(Integer extraPoint) {
        this.extraPoint = extraPoint;
    }

    public void setReportPoint(Integer reportPoint) {
        this.reportPoint = reportPoint;
    }

    public void setDob(Long dob) {
        this.dob = dob;
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

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }
}
