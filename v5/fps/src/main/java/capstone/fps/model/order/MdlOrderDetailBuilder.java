package capstone.fps.model.order;

import capstone.fps.common.Methods;
import capstone.fps.entity.FROrderDetail;
import capstone.fps.entity.FRProduct;

class MdlOrderDetailBuilder {

    MdlOrderDetail buildFull(FROrderDetail frDetail) {
        MdlOrderDetail mdlOrderDetail = new MdlOrderDetail();
        mdlOrderDetail.id = frDetail.getId();
        mdlOrderDetail.proId = frDetail.getProduct().getId();
        mdlOrderDetail.proName = frDetail.getProduct().getProductName();
        mdlOrderDetail.unitPrice = frDetail.getUnitPrice();
        mdlOrderDetail.quantity = frDetail.getQuantity();
        return mdlOrderDetail;
    }

    MdlOrderDetail buildDetailShp(FROrderDetail frDetail) {
        Methods methods = new Methods();
        FRProduct frProduct = frDetail.getProduct();
        MdlOrderDetail mdlOrderDetail = new MdlOrderDetail();
        mdlOrderDetail.id = frDetail.getId();
        mdlOrderDetail.proId = frDetail.getProduct().getId();
        mdlOrderDetail.proName = frProduct.getProductName();
        mdlOrderDetail.proImg =  methods.bytesToBase64(frProduct.getProductImage());
        mdlOrderDetail.unitPrice = frDetail.getUnitPrice();
        mdlOrderDetail.quantity = frDetail.getQuantity();
        return mdlOrderDetail;
    }

}
