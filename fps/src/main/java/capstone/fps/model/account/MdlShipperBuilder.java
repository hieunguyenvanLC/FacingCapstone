package capstone.fps.model.account;

import capstone.fps.common.Methods;
import capstone.fps.entity.FRAccount;
import capstone.fps.entity.FRShipper;

public class MdlShipperBuilder {

    public MdlShipper buildFull(FRAccount frAccount){
        Methods methods = new Methods();
        FRShipper frShipper = frAccount.getShipper();
        MdlShipper mdlShipper = new MdlShipper();

        mdlShipper.id = frAccount.getId();
        mdlShipper.phone = frAccount.getPhone();
        mdlShipper.name = frAccount.getName();
        mdlShipper.email = frAccount.getEmail();
        mdlShipper.extraPoint = frAccount.getExtraPoint();
        mdlShipper.reportPoint = frAccount.getReportPoint();
        mdlShipper.userImage = methods.bytesToBase64(frAccount.getUserImage());
        mdlShipper.natId = frAccount.getNatId();
        mdlShipper.natDate = frAccount.getNatDate();
        mdlShipper.dateOfBirth = frAccount.getDob();
        mdlShipper.createTime = frAccount.getCreateTime();
        mdlShipper.updateTime = frAccount.getUpdateTime();
        mdlShipper.deleteTime = frAccount.getDeleteTime();
        mdlShipper.note = frAccount.getNote();
        mdlShipper.status = frAccount.getStatus();
        mdlShipper.editor = frAccount.getEditor().getPhone();
        mdlShipper.bikeRegId = frShipper.getBikeRegId();
        mdlShipper.bikeRegDate = frShipper.getBikeRegDate();
        mdlShipper.introduce = frShipper.getIntroduce();
        mdlShipper.natFront = methods.bytesToBase64(frShipper.getNatIdFrontImage());
        mdlShipper.natBack = methods.bytesToBase64(frShipper.getNatIdBackImage());
        mdlShipper.sumRevenue = frShipper.getSumRevenue();
        mdlShipper.sourceId = frShipper.getSource().getId();
        mdlShipper.bikeRegFront = methods.bytesToBase64(frShipper.getBikeRegFront());
        mdlShipper.bikeRegBack =  methods.bytesToBase64(frShipper.getBikeRegBack());
        mdlShipper.priceLevelId = frShipper.getPriceLevel().getId();
        return mdlShipper;
    }

    public MdlShipper buildTableRowAdm(FRAccount frAccount){
        FRShipper frShipper = frAccount.getShipper();
        MdlShipper mdlShipper = new MdlShipper();
        mdlShipper.id = frAccount.getId();
        mdlShipper.phone = frAccount.getPhone();
        mdlShipper.name = frAccount.getName();
        mdlShipper.email = frAccount.getEmail();
        mdlShipper.status = frAccount.getStatus();
        mdlShipper.sumRevenue = frShipper.getSumRevenue();

        return mdlShipper;
    }


}
