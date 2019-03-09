package capstone.fps.model.order;

import capstone.fps.entity.FROrder;
import capstone.fps.entity.FROrderDetail;
import capstone.fps.entity.FRProduct;
import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class MdlOrderDetail {

    @Expose
    Integer id;
    @Expose
    int proId;
    @Expose
    String proName;
    @Expose
    Double unitPrice;
    @Expose
    Integer quantity;

    public MdlOrderDetail(FROrderDetail frDetail) {
        this.id = frDetail.getId();
        this.proId = frDetail.getProduct().getId();
        this.proName = frDetail.getProduct().getProductName();
        this.unitPrice = frDetail.getUnitPrice();
        this.quantity = frDetail.getQuantity();
    }
}
