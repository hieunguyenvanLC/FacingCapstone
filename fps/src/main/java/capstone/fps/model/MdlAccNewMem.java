package capstone.fps.model;


import com.google.gson.annotations.Expose;

public class MdlAccNewMem {

    @Expose
    private double phone;
    @Expose
    private String password;
    @Expose
    private String name;

    public double getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

}
