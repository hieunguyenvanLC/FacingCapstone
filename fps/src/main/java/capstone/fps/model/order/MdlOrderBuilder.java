package capstone.fps.model.order;

import capstone.fps.entity.FROrder;
import capstone.fps.entity.FROrderDetail;
import capstone.fps.entity.FRStore;
import capstone.fps.repository.OrderDetailRepo;

import java.util.ArrayList;
import java.util.List;

public class MdlOrderBuilder {

    public MdlOrder buildFull(FROrder frOrder, OrderDetailRepo orderDetailRepo) {
        MdlOrder mdlOrder = new MdlOrder();
        mdlOrder.id = frOrder.getId();
        mdlOrder.buyerName = frOrder.getAccount().getName();
        mdlOrder.buyerPhone = frOrder.getAccount().getPhone();
        mdlOrder.shipperName = frOrder.getShipper().getAccount().getName();
        mdlOrder.shipperPhone = frOrder.getShipper().getAccount().getPhone();
        mdlOrder.orderCode = frOrder.getOrderCode();
        mdlOrder.totalPrice = frOrder.getTotalPrice();
        mdlOrder.bookTime = frOrder.getBookTime();
        mdlOrder.receiveTime = frOrder.getReceiveTime();
        mdlOrder.shipperEarn = frOrder.getShipperEarn();
        mdlOrder.longitude = frOrder.getLongitude();
        mdlOrder.latitude = frOrder.getLatitude();
        mdlOrder.customerDescription = frOrder.getCustomerDescription();
        mdlOrder.createTime = frOrder.getCreateTime();
        mdlOrder.updateTime = frOrder.getUpdateTime();
        mdlOrder.deleteTime = frOrder.getDeleteTime();
        mdlOrder.note = frOrder.getNote();
        mdlOrder.status = frOrder.getStatus();
        mdlOrder.editor = frOrder.getEditor();

        List<FROrderDetail> frOrderDetails = orderDetailRepo.findAllByOrder(frOrder);
        FRStore store = frOrderDetails.get(0).getProduct().getStore();
        mdlOrder.storeName = store.getStoreName();
        mdlOrder.storeLongitude = store.getLongitude();
        mdlOrder.storeLatitude = store.getLatitude();

        List<MdlOrderDetail> mdlDetailList = new ArrayList<>();
        for (FROrderDetail frDetail : frOrderDetails) {
            MdlOrderDetail mdlDetail = new MdlOrderDetail(frDetail);
            mdlDetailList.add(mdlDetail);
        }
        mdlOrder.detailList = mdlDetailList;
        return mdlOrder;
    }


}
