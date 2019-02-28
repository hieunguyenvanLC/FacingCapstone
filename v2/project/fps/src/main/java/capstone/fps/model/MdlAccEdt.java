package capstone.fps.model;

import com.google.gson.annotations.Expose;

public class MdlAccEdt {

    @Expose
    private long id;
    @Expose
    private String name;
    @Expose
    private String nationalId;
    @Expose
    private java.sql.Timestamp nationalIdCreatedDate;
    @Expose
    private java.sql.Timestamp dateOfBirth;
    @Expose
    private java.sql.Timestamp createTime;
    @Expose
    private java.sql.Timestamp updateTime;
    @Expose
    private java.sql.Timestamp deactiveTime;
    @Expose
    private String note;

}
