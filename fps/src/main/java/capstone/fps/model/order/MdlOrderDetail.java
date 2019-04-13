package capstone.fps.model.order;

import com.google.gson.annotations.Expose;

public class MdlOrderDetail {
    @Expose
    Integer id;
    @Expose
    Integer proId;
    @Expose
    String proName;
    @Expose
    String proImg;
    @Expose
    Double unitPrice;
    @Expose
    Integer quantity;
}
