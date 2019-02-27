package capstone.fps.model;

import com.google.gson.annotations.Expose;

public class MdlAccBan {

    @Expose
    private Integer id;
    @Expose
    private String reason;

    public Integer getId() {
        return id;
    }

    public String getReason() {
        return reason;
    }
}
