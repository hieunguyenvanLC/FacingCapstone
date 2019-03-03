package capstone.fps.model.district;

import com.google.gson.annotations.Expose;

public class MdlCity {
    @Expose
    private Integer id;
    @Expose
    private String name;
    @Expose
    private MdlDistrict[] distList;

    public MdlCity() {
    }

    public MdlCity(Integer id, String name) {
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

    public MdlDistrict[] getDistList() {
        return distList;
    }

    public void setDistList(MdlDistrict[] distList) {
        this.distList = distList;
    }
}
