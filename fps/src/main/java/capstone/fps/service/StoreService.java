package capstone.fps.service;

import capstone.fps.common.Fix;
import capstone.fps.common.Methods;
import capstone.fps.common.Validator;
import capstone.fps.entity.FRAccount;
import capstone.fps.entity.FRDistrict;
import capstone.fps.entity.FRStore;
import capstone.fps.model.Response;
import capstone.fps.model.store.MdlStore;
import capstone.fps.model.store.StoreDis;
import capstone.fps.repository.DistrictRepo;
import capstone.fps.repository.ProductRepo;
import capstone.fps.repository.StoreRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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


    public FRDistrict getDIst(Integer distId) {
        if (distId == null) {
            return null;
        }
        Optional<FRDistrict> optional = districtRepository.findById(distId);
        return optional.orElse(null);
    }

    public FRStore getStore(Integer storeId) {
        if (storeId == null) {
            return null;
        }
        Optional<FRStore> optional = storeRepository.findById(storeId);
        return optional.orElse(null);
    }

    // To do: set schedule
    public Response<MdlStore> createStore(String name, String phone, String address, Integer distId, Double longitude, Double latitude, MultipartFile storeImg, String note) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        Validator valid = new Validator();
        Response<MdlStore> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        FRAccount account = methods.getUser();

        FRDistrict frDistrict = getDIst(distId);
        if (frDistrict == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find dist");
            return response;
        }
        phone = valid.checkPhone(phone);
        if (phone == null) {
            response.setResponse(Response.STATUS_FAIL, "Please enter valid phone number");
            return response;
        }

        FRStore frStore = new FRStore();
        frStore.setStoreName(name);
        frStore.setPhone(phone);
        frStore.setAddress(address);
        frStore.setDistrict(frDistrict);
        frStore.setLongitude(longitude);
        frStore.setLatitude(latitude);
        frStore.setStoreImage(methods.multipartToBytes(storeImg));
        frStore.setRating(0D);
        frStore.setRatingCount(0);
        frStore.setCreateTime(time);
        frStore.setNote(valid.nullProof(note));
        frStore.setStatus(Fix.STO_NEW.index);
        frStore.setEditor(account);
        storeRepository.save(frStore);

        MdlStore mdlStore = new MdlStore().convertFull(frStore, productRepository);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlStore);
        return response;
    }

//    public boolean deactivateStore(Integer id, String note) {
//        Methods methods = new Methods();
//        long time = methods.getTimeNow();
//        Optional<FRStore> optional = storeRepository.findById(id);
//        if (!optional.isPresent()) {
//            return false;
//        }
//        FRStore frStore = optional.get();
//        frStore.setDeleteTime(time);
//        if (!methods.nullOrSpace(note)) {
//            frStore.setNote(note);
//        }
//        frStore.setStatus(Fix.STO_HID.index);
//        frStore.setEditor(methods.getUser());
//        storeRepository.save(frStore);
//        return true;
//    }

    public Response<MdlStore> updateStore(Integer storeId, String name, String phone, String address, Integer distId, Double longitude, Double latitude, MultipartFile storeImg, String note, Integer status) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        Validator valid = new Validator();
        Response<MdlStore> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        FRAccount account = methods.getUser();

        FRStore frStore = getStore(storeId);
        if (frStore == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find store");
            return response;
        }
        FRDistrict frDistrict = getDIst(distId);
        if (frDistrict == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find dist");
            return response;
        }

        if (!methods.nullOrSpace(name)) {
            frStore.setStoreName(name);
        }
        phone = valid.checkPhone(phone);
        if (phone != null) {
            frStore.setPhone(phone);
        }
        if (!methods.nullOrSpace(address)) {
            frStore.setAddress(address);
        }
        frStore.setDistrict(frDistrict);
        if (longitude != null) {
            frStore.setLongitude(longitude);
        }
        if (latitude != null) {
            frStore.setLatitude(latitude);
        }
        if (storeImg != null) {
            frStore.setStoreImage(methods.multipartToBytes(storeImg));
        }
        frStore.setUpdateTime(time);
        frStore.setNote(valid.nullProof(note));
        frStore.setStatus(valid.checkUpdateStatus(frStore.getStatus(), status, Fix.STO_STAT_LIST));
        frStore.setEditor(account);
        storeRepository.save(frStore);
        MdlStore mdlStore = new MdlStore().convertFull(frStore, productRepository);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlStore);
        return response;
    }


    public List getStoreNearby(double longitude, double latitude) {
        double dis5km = 5 * Fix.DEGREE_PER_KM;
        double dis5kmNeg = -dis5km;
        MdlStore mdlStore = new MdlStore();
        List<FRStore> frStoreList = storeRepository.findAll();
        List<StoreDis> storeDisList = new ArrayList<>();
        List<MdlStore> mdlStoreList = new ArrayList<>();
        for (FRStore frStore : frStoreList) {
            double disLon = longitude - frStore.getLongitude();
            double disLat = latitude - frStore.getLatitude();
            if (dis5kmNeg <= disLon && disLon < dis5km && dis5kmNeg <= disLat && disLat < dis5km) {
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
        int size = Math.min(5, storeDisList.size());
        for (int i = 0; i < size; i++) {
            mdlStoreList.add(mdlStore.convertStoreNearBy(storeDisList.get(i).getStore()));
        }
        return mdlStoreList;
    }


    public List<MdlStore> getStoreList() {
        List<FRStore> frStoreList = storeRepository.findAll();
        List<MdlStore> mdlStoreList = new ArrayList<>();
        MdlStore mdlStore = new MdlStore();
        for (FRStore frStore : frStoreList) {
            mdlStoreList.add(mdlStore.convertTableRow(frStore));
        }
        return mdlStoreList;
    }

    public Response<MdlStore> getStoreDetailAdm(Integer storeId) {
        Response<MdlStore> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        if (storeId == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find store");
            return response;
        }
        Optional<FRStore> optional = storeRepository.findById(storeId);
        if (!optional.isPresent()) {
            response.setResponse(Response.STATUS_FAIL, "Cant find store");
            return response;
        }
        FRStore frStore = optional.get();
        MdlStore mdlStore = new MdlStore().convertFull(frStore, productRepository);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlStore);
        return response;
    }
}
