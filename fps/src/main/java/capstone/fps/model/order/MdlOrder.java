package capstone.fps.model.order;

import capstone.fps.entity.FRAccount;
import capstone.fps.entity.FRDistrict;
import capstone.fps.entity.FRShipper;
import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;

public class MdlOrder {

    @Expose
    Integer id;

    @Expose
    String buyerPhone;

    @Expose
    String buyerName;

    @Expose
    String buyerFace;

    @Expose
    String shipperPhone;

    @Expose
    String shipperName;

    @Expose
    String orderCode;

    @Expose
    String bill;

    @Expose
    Double totalPrice;

    @Expose
    Long bookTime;

    @Expose
    Long receiveTime;

    @Expose
    Double shipperEarn;

    @Expose
    Double longitude;

    @Expose
    Double latitude;

    @Expose
    String customerDescription;

    @Expose
    Long createTime;

    @Expose
    Long updateTime;

    @Expose
    Long deleteTime;

    @Expose
    String note;

    @Expose
    Integer status;

    @Expose
    FRAccount editor;

    @Expose
    String storeName;

    @Expose
    double storeLongitude;

    @Expose
    double storeLatitude;

    @Expose
    List<MdlOrderDetail> detailList;

    public MdlOrder() {
    }


}
