package capstone.fps.service;

import capstone.fps.common.Fix;
import capstone.fps.common.Methods;
import capstone.fps.common.Repo;
import capstone.fps.common.Validator;
import capstone.fps.entity.*;
import capstone.fps.model.Response;
import capstone.fps.model.store.MdlStore;
import capstone.fps.model.store.MdlStoreBuilder;
import capstone.fps.model.store.StoreDis;
import capstone.fps.repository.DistrictRepo;
import capstone.fps.repository.OrderDetailRepo;
import capstone.fps.repository.ProductRepo;
import capstone.fps.repository.StoreRepo;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreService {
    private StoreRepo storeRepository;
    private DistrictRepo districtRepository;
    private ProductRepo productRepository;

    public StoreService(StoreRepo storeRepository, DistrictRepo districtRepository, ProductRepo productRepository) {
        this.storeRepository = storeRepository;
        this.districtRepository = districtRepository;
        this.productRepository = productRepository;
    }

    // Web Admin - Store - Begin
    public Response<MdlStore> createStore(String name, String phone, String address, Integer distId, Double longitude, Double latitude, MultipartFile storeImg, String note) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        Validator valid = new Validator();
        Repo repo = new Repo();
        FRAccount currentUser = methods.getUser();
        MdlStoreBuilder mdlStoreBuilder = new MdlStoreBuilder();
        Response<MdlStore> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        // To do: set schedule
        FRDistrict frDistrict = repo.getDIst(distId, districtRepository);
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
        frStore.setEditor(currentUser);
        storeRepository.save(frStore);

        MdlStore mdlStore = mdlStoreBuilder.buildFull(frStore, productRepository);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlStore);
        return response;
    }

    public Response<MdlStore> updateStore(Integer storeId, String name, String phone, String address, Integer distId, Double longitude, Double latitude, MultipartFile storeImg, String note, Integer status) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        Validator valid = new Validator();
        Repo repo = new Repo();
        MdlStoreBuilder mdlStoreBuilder = new MdlStoreBuilder();
        FRAccount currentUser = methods.getUser();

        Response<MdlStore> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);

        FRStore frStore = repo.getStore(storeId, storeRepository);
        if (frStore == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find store");
            return response;
        }
        FRDistrict frDistrict = repo.getDIst(distId, districtRepository);
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
        frStore.setEditor(currentUser);
        storeRepository.save(frStore);
        MdlStore mdlStore = mdlStoreBuilder.buildFull(frStore, productRepository);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlStore);
        return response;
    }

    public List<MdlStore> getStoreList() {
        MdlStoreBuilder mdlStoreBuilder = new MdlStoreBuilder();
        List<FRStore> frStoreList = storeRepository.findAll();
        List<MdlStore> mdlStoreList = new ArrayList<>();
        for (FRStore frStore : frStoreList) {
            mdlStoreList.add(mdlStoreBuilder.buildTableRowAdm(frStore));
        }
        return mdlStoreList;
    }

    public Response<MdlStore> getStoreDetailAdm(Integer storeId) {
        MdlStoreBuilder mdlStoreBuilder = new MdlStoreBuilder();
        Repo repo = new Repo();
        Response<MdlStore> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        FRStore frStore = repo.getStore(storeId, storeRepository);
        if (frStore == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find store");
            return response;
        }
        MdlStore mdlStore = mdlStoreBuilder.buildFull(frStore, productRepository);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlStore);
        return response;
    }
    // Web Admin - Store - Begin


    // Mobile Member - Home - Begin
    public Response<List<MdlStore>> getStoreNearby(double longitude, double latitude) {
        MdlStoreBuilder mdlStoreBuilder = new MdlStoreBuilder();
        Response<List<MdlStore>> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        double dis = 5 * Fix.DEGREE_PER_KM;
        double minLon = longitude - dis;
        double maxLon = longitude + dis;
        double minLat = latitude - dis;
        double maxLat = latitude + dis;
        List<FRStore> frStoreList = storeRepository.findAll();
        List<MdlStore> mdlStoreList = new ArrayList<>();
        List<StoreDis> storeDisList = new ArrayList<>();
        for (FRStore frStore : frStoreList) {
            if (minLon <= frStore.getLongitude() && frStore.getLongitude() < maxLon && minLat <= frStore.getLatitude() && frStore.getLatitude() < maxLat) {
                double disLon = frStore.getLongitude() - longitude;
                double disLat = frStore.getLatitude() - latitude;
                storeDisList.add(new StoreDis(disLon * disLon + disLat * disLat, frStore));
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
            mdlStoreList.add(mdlStoreBuilder.buildStoreNearBy(storeDisList.get(i).getStore()));
        }
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlStoreList);
        return response;
    }
    // Mobile Member - Home - End


    // Mobile Member - Store Detail - Begin
    public Response<MdlStore> getStoreDetailMem(Integer storeId) {
        MdlStoreBuilder mdlStoreBuilder = new MdlStoreBuilder();
        Repo repo = new Repo();
        Response<MdlStore> response = new Response<>(Response.STATUS_FAIL, Response.MESSAGE_FAIL);
        FRStore frStore = repo.getStore(storeId, storeRepository);
        if (frStore == null) {
            response.setResponse(Response.STATUS_FAIL, "Cant find store");
            return response;
        }
        MdlStore mdlStore = mdlStoreBuilder.buildDetailMember(frStore, productRepository);
        response.setResponse(Response.STATUS_SUCCESS, Response.MESSAGE_SUCCESS, mdlStore);
        return response;
    }
    // Mobile Member - Store Detail - End
}
