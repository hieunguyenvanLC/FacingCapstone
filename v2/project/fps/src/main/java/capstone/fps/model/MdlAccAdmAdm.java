package capstone.fps.model;

import com.google.gson.annotations.Expose;

public class MdlAccAdmAdm {
    @Expose
    private Integer id;

    @Expose
    private String username;

    @Expose
    private String password;

    @Expose
    private String name;

    @Expose
    private String email;

    @Expose
    private String avatar;

    @Expose
    private String nationalId;

    @Expose
    private Long natIdDate;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public Long getNatIdDate() {
        return natIdDate;
    }

    public void setNatIdDate(Long natIdDate) {
        this.natIdDate = natIdDate;
    }

    public Long getDob() {
        return dob;
    }

    public void setDob(Long dob) {
        this.dob = dob;
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

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
