package capstone.fps.model.district;

import com.google.gson.annotations.Expose;

public class MdlDistrict {
    @Expose
    private Integer id;
    @Expose
    private String name;

    public MdlDistrict(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
