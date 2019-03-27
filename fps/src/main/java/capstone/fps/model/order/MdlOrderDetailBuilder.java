package capstone.fps.model.order;

import capstone.fps.entity.FROrderDetail;

 class MdlOrderDetailBuilder {

     MdlOrderDetail buildFull(FROrderDetail frDetail){
        MdlOrderDetail mdlOrderDetail = new MdlOrderDetail();
        mdlOrderDetail.id = frDetail.getId();
        mdlOrderDetail.proId = frDetail.getProduct().getId();
        mdlOrderDetail.proName = frDetail.getProduct().getProductName();
        mdlOrderDetail.unitPrice = frDetail.getUnitPrice();
        mdlOrderDetail.quantity = frDetail.getQuantity();
        return mdlOrderDetail;
    }
}
