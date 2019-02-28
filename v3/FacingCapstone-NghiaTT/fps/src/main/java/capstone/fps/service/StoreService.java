package capstone.fps.service;

import capstone.fps.common.Fix;
import capstone.fps.common.Methods;
import capstone.fps.common.Validator;
import capstone.fps.entity.FRDistrict;
import capstone.fps.entity.FRProduct;
import capstone.fps.entity.FRStore;
import capstone.fps.model.Response;
import capstone.fps.model.product.MdlProInSto;
import capstone.fps.model.store.MdlAdmStoAll;
import capstone.fps.model.store.MdlAdmStoOne;
import capstone.fps.model.store.MdlMemStoNear;
import capstone.fps.model.store.StoreDis;
import capstone.fps.repository.DistrictRepo;
import capstone.fps.repository.ProductRepo;
import capstone.fps.repository.StoreRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
public class StoreService {
    private StoreRepo storeRepository;
    private DistrictRepo districtRepository;
    private ProductRepo productRepository;

    public StoreService(StoreRepo storeRepository, DistrictRepo districtRepository, ProductRepo productRepository) {
        this.storeRepository = storeRepository;
        this.districtRepository = districtRepository;
        this.productRepository = productRepository;
    }

    // To do: set schedule
    public Response<MdlAdmStoOne> createStore(String name, String address, Integer distId, Double longitude, Double latitude, MultipartFile storeImg, String note) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        Validator valid = new Validator();
        Base64.Decoder decoder = Base64.getDecoder();
        Response<MdlAdmStoOne> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);


        Optional<FRDistrict> frDistrictOptional = districtRepository.findById(distId);
        if (!frDistrictOptional.isPresent()) {
            response.setResponse(Response.STATUS_FAIL, "Cant find dist");
            return response;
        }
        String fileName = methods.handleImage(storeImg);
        if(fileName == null){
            response.setResponse(Response.STATUS_FAIL, "Cant read storeImg");
            return response;
        }

        byte[] storeImgBytes = null;
        if (storeImg != null) {
//            storeImgBytes = decoder.decode(storeImg);
            try {
                storeImgBytes = storeImg.getBytes();
            } catch (IOException e) {
                response.setResponse(Response.STATUS_FAIL, "Cant read storeImg");
                e.printStackTrace();
                return response;

            }
        }


        FRStore frStore = new FRStore();
        frStore.setStoreName(name);
        frStore.setAddress(address);
        frStore.setDistrict(frDistrictOptional.get());
        frStore.setLongitude(longitude);
        frStore.setLatitude(latitude);
        frStore.setStoreImage(storeImgBytes);
        frStore.setRating(0D);
        frStore.setRatingCount(0);
        frStore.setCreateTime(time);
        frStore.setNote(note);
        frStore.setStatus(Fix.STO_NEW.index);
        frStore.setEditor(methods.getUser());
        storeRepository.save(frStore);


        MdlAdmStoOne mdlStore = new MdlAdmStoOne();
        mdlStore.setId(frStore.getId());
        mdlStore.setName(frStore.getStoreName());
        mdlStore.setAddress(frStore.getAddress());
        mdlStore.setDistId(distId);
        mdlStore.setLongitude(frStore.getLongitude());
        mdlStore.setLatitude(frStore.getLatitude());
        mdlStore.setPhone(frStore.getPhone());
        mdlStore.setStoreImage(fileName);
        mdlStore.setRating(frStore.getRating());
        mdlStore.setRatingCount(frStore.getRatingCount());
        mdlStore.setCreateTime(frStore.getCreateTime());
        mdlStore.setNote(frStore.getNote());
        mdlStore.setStatus(frStore.getStatus());
        mdlStore.setEditor(methods.getUser().getPhone());
        mdlStore.setProList(getProInSto(frStore));
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlStore);
        return response;

    }

    public boolean deactivateStore(Integer id, String note) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        Optional<FRStore> optional = storeRepository.findById(id);
        if (!optional.isPresent()) {
            return false;
        }
        FRStore frStore = optional.get();
        frStore.setDeleteTime(time);
        if (!methods.nullOrSpace(note)) {
            frStore.setNote(note);
        }
        frStore.setStatus(Fix.STO_HID.index);
        frStore.setEditor(methods.getUser());
        storeRepository.save(frStore);
        return true;
    }

    public Response<MdlAdmStoOne> updateStore(Integer storeId, String name, String phone, String address, Integer distId, Double longitude, Double latitude, String note) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        Validator valid = new Validator();
        Base64.Encoder encoder = Base64.getEncoder();
        Response<MdlAdmStoOne> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        Optional<FRStore> optional = storeRepository.findById(storeId);
        if (!optional.isPresent()) {
            response.setResponse(Response.STATUS_FAIL, "Cant find store");
            return response;
        }
        FRStore frStore = optional.get();
        phone = valid.checkPhone(phone);
        if (phone != null) {
            frStore.setPhone(phone);
        }
        if (!methods.nullOrSpace(name)) {
            frStore.setStoreName(name);
        }
        if (!methods.nullOrSpace(address)) {
            frStore.setAddress(address);
        }
        Optional<FRDistrict> frDistrict = districtRepository.findById(distId);
        if (frDistrict.isPresent()) {
            frStore.setDistrict(frDistrict.get());
        }
        if (longitude != null) {
            frStore.setLongitude(longitude);
        }
        if (latitude != null) {
            frStore.setLatitude(latitude);
        }
        frStore.setUpdateTime(time);
        if (!methods.nullOrSpace(note)) {
            frStore.setNote(note);
        }
        frStore.setEditor(methods.getUser());
        storeRepository.save(frStore);


        String storeImg = null;
        if (frStore.getStoreImage() != null) {
            storeImg = encoder.encodeToString(frStore.getStoreImage());
        }


        MdlAdmStoOne mdlStore = new MdlAdmStoOne();
        mdlStore.setId(storeId);
        mdlStore.setName(frStore.getStoreName());
        mdlStore.setAddress(frStore.getAddress());
        mdlStore.setDistId(frStore.getDistrict().getId());
        mdlStore.setLongitude(frStore.getLongitude());
        mdlStore.setLatitude(frStore.getLatitude());
        mdlStore.setPhone(frStore.getPhone());
        mdlStore.setStoreImage(storeImg);
        mdlStore.setRating(frStore.getRating());
        mdlStore.setRatingCount(frStore.getRatingCount());
        mdlStore.setCreateTime(frStore.getCreateTime());
        mdlStore.setUpdateTime(frStore.getUpdateTime());
        mdlStore.setDeleteTime(frStore.getDeleteTime());
        mdlStore.setNote(frStore.getNote());
        mdlStore.setStatus(frStore.getStatus());
        mdlStore.setEditor(frStore.getEditor().getName());
        mdlStore.setProList(getProInSto(frStore));
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlStore);
        return response;
    }

    public List<MdlProInSto> getProInSto(FRStore frStore) {

        List<FRProduct> frProductList = productRepository.findAllByStoreAndStatusNotOrderByUpdateTimeDesc(frStore, Fix.PRO_HID.index);
        List<MdlProInSto> proList = new ArrayList<>();
        for (FRProduct frProduct : frProductList) {
            MdlProInSto mdlPro = new MdlProInSto();
            mdlPro.setId(frProduct.getId());
            mdlPro.setProductName(frProduct.getProductName());
            mdlPro.setPrice(frProduct.getPrice());
            mdlPro.setNote(frProduct.getNote());
            mdlPro.setStatus(frProduct.getStatus());
            proList.add(mdlPro);
        }
        return proList;
    }

    public List<MdlMemStoNear> getStoreNearby(double longitude, double latitude) {
        double dis2km = 2 * Fix.DEGREE_PER_KM;
        double dis2kmNeg = -dis2km;
        List<FRStore> frStoreList = storeRepository.findAll();
        List<StoreDis> storeDisList = new ArrayList<>();
        for (FRStore frStore : frStoreList) {
            double disLon = longitude - frStore.getLongitude();
            double disLat = latitude - frStore.getLatitude();
            if (dis2kmNeg <= disLon && disLon < dis2km && dis2kmNeg <= disLat && disLat < dis2km) {
                double dis = disLon * disLon + disLat * disLat;
                StoreDis storeDis = new StoreDis(frStore, dis);
                storeDisList.add(storeDis);
            }
        }
        storeDisList.sort((o1, o2) -> {
            if (o1.getDis() > o2.getDis()) {
                return 1;
            }
            return -1;
        });
        return null;
    }


    public List<MdlAdmStoAll> getStoreList() {
        List<FRStore> frStoreList = storeRepository.findAll();
        List<MdlAdmStoAll> mdlStoreList = new ArrayList<>();
        for (FRStore frStore : frStoreList) {
            MdlAdmStoAll mdlStore = new MdlAdmStoAll();
            mdlStore.setId(frStore.getId());
            mdlStore.setName(frStore.getStoreName());
            mdlStore.setAddress(frStore.getAddress());
            mdlStore.setDistId(frStore.getDistrict().getId());
            mdlStore.setStatus(frStore.getStatus());
            mdlStore.setNote(frStore.getNote());
            mdlStoreList.add(mdlStore);
        }
        return mdlStoreList;
    }

    public Response<MdlAdmStoOne> getStoreDetailAdm(int storeId) {
        Methods methods = new Methods();
        Base64.Encoder encoder = Base64.getEncoder();
        Response<MdlAdmStoOne> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        Optional<FRStore> optional = storeRepository.findById(storeId);
        if (!optional.isPresent()) {
            response.setResponse(Response.STATUS_FAIL, "Cant find store");
            return response;
        }
        FRStore frStore = optional.get();


        String storeImg = null;
        if (frStore.getStoreImage() != null) {
//            storeImg = encoder.encodeToString(frStore.getStoreImage());
//            storeImg = new String(frStore.getStoreImage());
            storeImg = methods.toBase64(frStore.getStoreImage());
        }
        String editor = null;
        if (frStore.getEditor() != null) {
            editor = frStore.getEditor().getPhone();
        }

        MdlAdmStoOne mdlStore = new MdlAdmStoOne();
        mdlStore.setId(storeId);
        mdlStore.setName(frStore.getStoreName());
        mdlStore.setAddress(frStore.getAddress());
        mdlStore.setDistId(frStore.getDistrict().getId());
        mdlStore.setLongitude(frStore.getLongitude());
        mdlStore.setLatitude(frStore.getLatitude());
        mdlStore.setPhone(frStore.getPhone());
        mdlStore.setStoreImage(storeImg);
        mdlStore.setRating(frStore.getRating());
        mdlStore.setRatingCount(frStore.getRatingCount());
        mdlStore.setCreateTime(frStore.getCreateTime());
        mdlStore.setUpdateTime(frStore.getUpdateTime());
        mdlStore.setDeleteTime(frStore.getDeleteTime());
        mdlStore.setNote(frStore.getNote());
        mdlStore.setStatus(frStore.getStatus());
        mdlStore.setEditor(editor);
        mdlStore.setProList(getProInSto(frStore));
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlStore);
        return response;
    }
}
