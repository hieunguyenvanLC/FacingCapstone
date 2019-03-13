package capstone.fps.model.store;

import capstone.fps.common.Methods;
import capstone.fps.entity.FRProduct;

public class MdlProductBuilder {


    public MdlProduct buildBig(FRProduct frProduct) {
        Methods methods = new Methods();
        MdlProduct mdlProduct = new MdlProduct();
        mdlProduct.id = frProduct.getId();
        mdlProduct.name = frProduct.getProductName();
        mdlProduct.price = frProduct.getPrice();
        mdlProduct.image = methods.bytesToBase64(frProduct.getProductImage());
        mdlProduct.description = frProduct.getDescription();
        mdlProduct.createTime = frProduct.getCreateTime();
        mdlProduct.note = frProduct.getNote();
        mdlProduct.status = frProduct.getStatus();
        return mdlProduct;
    }

    public MdlProduct buildProInSto(FRProduct frProduct) {
        MdlProduct mdlProduct = new MdlProduct();
        mdlProduct.id = frProduct.getId();
        mdlProduct.name = frProduct.getProductName();
        mdlProduct.price = frProduct.getPrice();
        mdlProduct.note = frProduct.getNote();
        mdlProduct.status = frProduct.getStatus();
        return mdlProduct;
    }

    public MdlProduct buildProInStoMem(FRProduct frProduct) {
        Methods methods = new Methods();
        MdlProduct mdlProduct = new MdlProduct();
        mdlProduct.id = frProduct.getId();
        mdlProduct.name = frProduct.getProductName();
        mdlProduct.price = frProduct.getPrice();
        mdlProduct.image = methods.bytesToBase64(frProduct.getProductImage());
        return mdlProduct;
    }
}
