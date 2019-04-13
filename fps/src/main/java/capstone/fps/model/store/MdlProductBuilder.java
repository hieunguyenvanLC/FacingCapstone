package capstone.fps.model.store;

import capstone.fps.common.Methods;
import capstone.fps.entity.FRProduct;
import capstone.fps.entity.FRStore;
import org.apache.tomcat.util.codec.binary.Base64;

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

    public MdlProduct buildProBest(FRProduct frProduct) {
        Methods methods = new Methods();
        MdlProduct mdlProduct = new MdlProduct();
        mdlProduct.id = frProduct.getId();
        mdlProduct.name = frProduct.getProductName();
        mdlProduct.price = frProduct.getPrice();
        mdlProduct.image = methods.bytesToBase64(frProduct.getProductImage());
        mdlProduct.address = frProduct.getStore().getAddress()  + " " + frProduct.getStore().getDistrict().getName();
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
