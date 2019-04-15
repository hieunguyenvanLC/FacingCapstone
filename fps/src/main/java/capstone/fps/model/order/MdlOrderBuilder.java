package capstone.fps.model.order;

import capstone.fps.common.Methods;
import capstone.fps.entity.FROrder;
import capstone.fps.entity.FROrderDetail;
import capstone.fps.entity.FRShipper;
import capstone.fps.entity.FRStore;
import capstone.fps.repository.OrderDetailRepo;

import java.util.ArrayList;
import java.util.List;

public class MdlOrderBuilder {

    public MdlOrder buildFull(FROrder frOrder, OrderDetailRepo orderDetailRepo) {
        Methods methods = new Methods();
        MdlOrderDetailBuilder mdlOrderDetailBuilder = new MdlOrderDetailBuilder();
        MdlOrder mdlOrder = new MdlOrder();
        mdlOrder.id = frOrder.getId();
        mdlOrder.buyerName = frOrder.getAccount().getName();
        mdlOrder.buyerPhone = frOrder.getAccount().getPhone();
        mdlOrder.buyerFace = methods.bytesToBase64(frOrder.getBuyerFace());
        FRShipper shipper = frOrder.getShipper();
        if (shipper != null) {
            mdlOrder.shipperName = shipper.getAccount().getName();
            mdlOrder.shipperPhone = shipper.getAccount().getPhone();
        }
        mdlOrder.bill = methods.bytesToBase64(frOrder.getBill());
        mdlOrder.orderCode = frOrder.getOrderCode();
        mdlOrder.totalPrice = frOrder.getTotalPrice();
        mdlOrder.assignTime = frOrder.getAssignTime();
        mdlOrder.buyTime = frOrder.getBuyTime();
        mdlOrder.receiveTime = frOrder.getReceiveTime();
        mdlOrder.shipperEarn = frOrder.getShipperEarn();
        mdlOrder.priceLevel = frOrder.getPriceLevel();
        mdlOrder.address = frOrder.getShipAddress();
        mdlOrder.longitude = frOrder.getLongitude();
        mdlOrder.latitude = frOrder.getLatitude();
        mdlOrder.customerDescription = frOrder.getCustomerDescription();
        mdlOrder.createTime = frOrder.getCreateTime();
        mdlOrder.updateTime = frOrder.getUpdateTime();
        mdlOrder.deleteTime = frOrder.getDeleteTime();
        mdlOrder.note = frOrder.getNote();
        mdlOrder.status = frOrder.getStatus();
        mdlOrder.editor = frOrder.getEditor();
        mdlOrder.rating = frOrder.getRating();

        List<FROrderDetail> frOrderDetails = orderDetailRepo.findAllByOrder(frOrder);
        FRStore store = orderDetailRepo.findAllByOrder(frOrder).get(0).getProduct().getStore();
        mdlOrder.storeName = store.getStoreName();
        mdlOrder.storeAddress = store.getAddress() + " " + store.getDistrict().getName();
        mdlOrder.storeLongitude = store.getLongitude();
        mdlOrder.storeLatitude = store.getLatitude();

        List<MdlOrderDetail> mdlDetailList = new ArrayList<>();
        for (FROrderDetail frDetail : frOrderDetails) {
            MdlOrderDetail mdlDetail = mdlOrderDetailBuilder.buildFull(frDetail);
            mdlDetailList.add(mdlDetail);
        }
        mdlOrder.detailList = mdlDetailList;
        return mdlOrder;
    }


    public MdlOrder buildHistoryBuyer(FROrder frOrder) {
        MdlOrder mdlOrder = new MdlOrder();
        mdlOrder.id = frOrder.getId();
        mdlOrder.orderCode = frOrder.getOrderCode();
        mdlOrder.createTime = frOrder.getCreateTime();
        mdlOrder.totalPrice = frOrder.getTotalPrice();
        mdlOrder.shipperEarn = frOrder.getShipperEarn();
        mdlOrder.priceLevel = frOrder.getPriceLevel();
        mdlOrder.status = frOrder.getStatus();
        return mdlOrder;
    }

    public MdlOrder buildAdminTableRow(FROrder frOrder) {
        MdlOrder mdlOrder = new MdlOrder();
        mdlOrder.id = frOrder.getId();
        mdlOrder.orderCode = frOrder.getOrderCode();
        mdlOrder.buyerName = frOrder.getAccount().getName();
        mdlOrder.buyerPhone = frOrder.getAccount().getPhone();
        mdlOrder.totalPrice = frOrder.getTotalPrice();
        mdlOrder.shipperEarn = frOrder.getShipperEarn();
        mdlOrder.buyTime = frOrder.getBuyTime();
        mdlOrder.status = frOrder.getStatus();
        return mdlOrder;
    }

    public MdlOrder buildDetailWthImg(FROrder frOrder, OrderDetailRepo orderDetailRepo) {
        Methods methods = new Methods();
        MdlOrderDetailBuilder mdlOrderDetailBuilder = new MdlOrderDetailBuilder();
        MdlOrder mdlOrder = new MdlOrder();
        mdlOrder.id = frOrder.getId();
        mdlOrder.buyerName = frOrder.getAccount().getName();
        mdlOrder.buyerPhone = frOrder.getAccount().getPhone();
        mdlOrder.buyerFace = methods.bytesToBase64(frOrder.getBuyerFace());
        FRShipper shipper = frOrder.getShipper();
        if (shipper != null) {
            mdlOrder.shipperName = shipper.getAccount().getName();
            mdlOrder.shipperPhone = shipper.getAccount().getPhone();
        }
        mdlOrder.bill = methods.bytesToBase64(frOrder.getBill());
        mdlOrder.orderCode = frOrder.getOrderCode();
        mdlOrder.totalPrice = frOrder.getTotalPrice();
        mdlOrder.assignTime = frOrder.getAssignTime();
        mdlOrder.buyTime = frOrder.getBuyTime();
        mdlOrder.receiveTime = frOrder.getReceiveTime();
        mdlOrder.shipperEarn = frOrder.getShipperEarn();
        mdlOrder.priceLevel = frOrder.getPriceLevel();
        mdlOrder.address = frOrder.getShipAddress();
        mdlOrder.longitude = frOrder.getLongitude();
        mdlOrder.latitude = frOrder.getLatitude();
        mdlOrder.customerDescription = frOrder.getCustomerDescription();
        mdlOrder.createTime = frOrder.getCreateTime();
        mdlOrder.updateTime = frOrder.getUpdateTime();
        mdlOrder.deleteTime = frOrder.getDeleteTime();
        mdlOrder.note = frOrder.getNote();
        mdlOrder.status = frOrder.getStatus();
        mdlOrder.editor = frOrder.getEditor();
        mdlOrder.rating = frOrder.getRating();

        List<FROrderDetail> frOrderDetails = orderDetailRepo.findAllByOrder(frOrder);
        FRStore store = orderDetailRepo.findAllByOrder(frOrder).get(0).getProduct().getStore();
        mdlOrder.storeName = store.getStoreName();
        mdlOrder.storeAddress = store.getAddress() + " " + store.getDistrict().getName();
        mdlOrder.storeLongitude = store.getLongitude();
        mdlOrder.storeLatitude = store.getLatitude();

        List<MdlOrderDetail> mdlDetailList = new ArrayList<>();
        for (FROrderDetail frDetail : frOrderDetails) {
            MdlOrderDetail mdlDetail = mdlOrderDetailBuilder.buildDetailWthImg(frDetail);
            mdlDetailList.add(mdlDetail);
        }
        mdlOrder.detailList = mdlDetailList;
        return mdlOrder;
    }

}
