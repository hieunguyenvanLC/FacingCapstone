package capstone.fps.model.store;

import capstone.fps.common.Fix;
import capstone.fps.common.Methods;
import capstone.fps.entity.FRProduct;
import capstone.fps.entity.FRStore;
import capstone.fps.model.product.MdlProduct;
import capstone.fps.repository.ProductRepo;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class MdlStore {
    @Expose
    private Integer id;
    @Expose
    private String name;
    @Expose
    private String address;
    @Expose
    private int distId;
    @Expose
    private Double longitude;
    @Expose
    private Double latitude;
    @Expose
    private String image;
    @Expose
    private Double rating;
    @Expose
    private String phone;
    @Expose
    private Integer ratingCount;
    @Expose
    private Long createTime;
    @Expose
    private Long updateTime;
    @Expose
    private Long deleteTime;
    @Expose
    private String note;
    @Expose
    private Integer status;
    @Expose
    private String editor;
    @Expose
    private List<MdlProduct> proList;

    public MdlStore() {
    }


    public List<MdlProduct> getProInSto(FRStore frStore, ProductRepo productRepository) {
        List<FRProduct> frProductList = productRepository.findAllByStoreAndStatusNotOrderByUpdateTimeDesc(frStore, Fix.PRO_HID.index);
        List<MdlProduct> proList = new ArrayList<>();
        MdlProduct mdlPro = new MdlProduct();
        for (FRProduct frProduct : frProductList) {
            proList.add(mdlPro.convertProInSto(frProduct));
        }
        return proList;
    }

    public MdlStore convertFull(FRStore frStore, ProductRepo productRepository) {
        Methods methods = new Methods();
        MdlStore mdlStore = new MdlStore();
        mdlStore.id = frStore.getId();
        mdlStore.name = frStore.getStoreName();
        mdlStore.address = frStore.getAddress();
        mdlStore.distId = frStore.getDistrict().getId();
        mdlStore.longitude = frStore.getLongitude();
        mdlStore.latitude = frStore.getLatitude();
        mdlStore.image = methods.bytesToBase64(frStore.getStoreImage());
        mdlStore.rating = frStore.getRating();
        mdlStore.phone = frStore.getPhone();
        mdlStore.ratingCount = frStore.getRatingCount();
        mdlStore.createTime = frStore.getCreateTime();
        mdlStore.updateTime = frStore.getUpdateTime();
        mdlStore.deleteTime = frStore.getDeleteTime();
        mdlStore.note = frStore.getNote();
        mdlStore.status = frStore.getStatus();
        mdlStore.editor = frStore.getEditor().getPhone();
        mdlStore.proList = getProInSto(frStore, productRepository);
        return mdlStore;
    }

    public MdlStore convertTableRow(FRStore frStore) {
        MdlStore mdlStore = new MdlStore();
        mdlStore.id = frStore.getId();
        mdlStore.name = frStore.getStoreName();
        mdlStore.address = frStore.getAddress();
        mdlStore.distId = frStore.getDistrict().getId();
        mdlStore.note = frStore.getNote();
        mdlStore.status = frStore.getStatus();
        return mdlStore;
    }
}
