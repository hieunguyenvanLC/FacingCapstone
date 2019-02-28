package capstone.fps.model.store;

import com.google.gson.annotations.Expose;

public class MdlAdmStoAll {
    @Expose
    private long id;
    @Expose
    private String name;
    @Expose
    private String address;
    @Expose
    private int distId;
    @Expose
    private String note;
    @Expose
    private long status;

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDistId(int distId) {
        this.distId = distId;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setStatus(long status) {
        this.status = status;
    }
}
