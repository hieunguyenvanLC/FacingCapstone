package capstone.fps.model.product;

import com.google.gson.annotations.Expose;

public class MdlProInSto {
    @Expose
    private Integer id;
    @Expose
    private String productName;
    @Expose
    private Double price;
    @Expose
    private String note;
    @Expose
    private Integer status;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
