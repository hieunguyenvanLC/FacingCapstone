package tuhoc.lan3.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Respone {
    @Expose
    @SerializedName("success")
    private boolean success;

    @Expose
    @SerializedName("message")
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Respone(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
