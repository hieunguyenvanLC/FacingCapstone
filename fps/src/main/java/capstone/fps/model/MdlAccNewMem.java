package capstone.fps.model;


import com.google.gson.annotations.Expose;

public class MdlAccNewMem {

    @Expose
    private double phone;
    @Expose
    private String password;
    @Expose
    private String name;
    @Expose
    private String email;
    @Expose
    private long dob;

    public double getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public long getDob() {
        return dob;
    }
}
