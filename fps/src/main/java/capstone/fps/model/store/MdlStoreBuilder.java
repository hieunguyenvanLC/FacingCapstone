package capstone.fps.model.store;

import capstone.fps.common.Fix;
import capstone.fps.common.Methods;
import capstone.fps.entity.FRProduct;
import capstone.fps.entity.FRStore;
import capstone.fps.repository.ProductRepo;

import java.util.ArrayList;
import java.util.List;

public class MdlStoreBuilder {

    private List<MdlProduct> getProInSto(FRStore frStore, ProductRepo productRepository) {
        List<FRProduct> frProductList = productRepository.findAllByStoreAndStatusNotOrderByUpdateTimeDesc(frStore, Fix.PRO_HID.index);
        List<MdlProduct> proList = new ArrayList<>();
        MdlProductBuilder mdlProductBuilder = new MdlProductBuilder();
        for (FRProduct frProduct : frProductList) {
            proList.add(mdlProductBuilder.buildProInSto(frProduct));
        }
        return proList;
    }

    public MdlStore buildFull(FRStore frStore, ProductRepo productRepository) {
        Methods methods = new Methods();
        MdlStore mdlStore = new MdlStore();
        mdlStore.id = frStore.getId();
        mdlStore.name = frStore.getStoreName();
        mdlStore.address = frStore.getAddress();
        mdlStore.distId = frStore.getDistrict().getId();
        mdlStore.longitude = frStore.getLongitude();
        mdlStore.latitude = frStore.getLatitude();
        mdlStore.image = methods.bytesToBase64(frStore.getStoreImage());
        mdlStore.phone = frStore.getPhone();
        mdlStore.createTime = frStore.getCreateTime();
        mdlStore.updateTime = frStore.getUpdateTime();
        mdlStore.deleteTime = frStore.getDeleteTime();
        mdlStore.note = frStore.getNote();
        mdlStore.status = frStore.getStatus();
        mdlStore.editor = frStore.getEditor().getPhone();
        mdlStore.proList = getProInSto(frStore, productRepository);
        return mdlStore;
    }

    public MdlStore buildTableRowAdm(FRStore frStore) {
        MdlStore mdlStore = new MdlStore();
        mdlStore.id = frStore.getId();
        mdlStore.name = frStore.getStoreName();
        mdlStore.address = frStore.getAddress();
        mdlStore.distId = frStore.getDistrict().getId();
        mdlStore.note = frStore.getNote();
        mdlStore.status = frStore.getStatus();
        return mdlStore;
    }

    public MdlStore buildStoreNearBy(FRStore frStore) {
        Methods methods = new Methods();
        MdlStore mdlStore = new MdlStore();
        mdlStore.id = frStore.getId();
        mdlStore.name = frStore.getStoreName();
        mdlStore.image = methods.bytesToBase64(frStore.getStoreImage());
        mdlStore.address = frStore.getAddress() + " " + frStore.getDistrict().getName();
        return mdlStore;
    }

    public MdlStore buildDetailMember(FRStore frStore, ProductRepo productRepository) {
        Methods methods = new Methods();
        MdlStore mdlStore = new MdlStore();
        mdlStore.id = frStore.getId();
        mdlStore.name = frStore.getStoreName();
        mdlStore.address = frStore.getAddress();
        mdlStore.distStr = frStore.getDistrict().getName();
        mdlStore.longitude = frStore.getLongitude();
        mdlStore.latitude = frStore.getLatitude();
        mdlStore.image = methods.bytesToBase64(frStore.getStoreImage());
        mdlStore.phone = frStore.getPhone();

        List<FRProduct> frProductList = productRepository.findAllByStoreAndStatusNotOrderByUpdateTimeDesc(frStore, Fix.PRO_HID.index);
        List<MdlProduct> proList = new ArrayList<>();
        MdlProductBuilder mdlProductBuilder = new MdlProductBuilder();
        for (FRProduct frProduct : frProductList) {
            proList.add(mdlProductBuilder.buildProInStoMem(frProduct));
        }
        mdlStore.proList = proList;
        return mdlStore;
    }
}
