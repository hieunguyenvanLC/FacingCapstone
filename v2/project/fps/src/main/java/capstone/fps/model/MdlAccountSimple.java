package capstone.fps.model;

import com.google.gson.annotations.Expose;

public class MdlAccountSimple {
    @Expose
    private Integer id;
    @Expose
    private String name;
    @Expose
    private String fullAddress;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }
}
