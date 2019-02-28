package capstone.fps.service;

import capstone.fps.common.Fix;
import capstone.fps.common.Methods;
import capstone.fps.common.Validator;
import capstone.fps.entity.FRDistrict;
import capstone.fps.entity.FRStore;
import capstone.fps.model.MdlStoreReadNear;
import capstone.fps.repository.DistrictRepo;
import capstone.fps.repository.StoreRepo;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

class StoreDis {
    private FRStore store;
    private Double dis;

    public StoreDis(FRStore store, Double dis) {
        this.store = store;
        this.dis = dis;
    }

    public FRStore getStore() {
        return store;
    }

    public Double getDis() {
        return dis;
    }
}

@Service
public class StoreService {
    private StoreRepo storeRepository;
    private DistrictRepo districtRepository;

    public StoreService(StoreRepo storeRepository, DistrictRepo districtRepository) {
        this.storeRepository = storeRepository;
        this.districtRepository = districtRepository;
    }

    // To do: set schedule
    public boolean createStore(String name, String address, Integer distId, Double longitude, Double latitude, String note) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        Validator valid = new Validator();

        Optional<FRDistrict> frDistrictOptional = districtRepository.findById(distId);
        if (!frDistrictOptional.isPresent()) {
            return false;
        }

        FRStore frStore = new FRStore();
        frStore.setStoreName(name);
        frStore.setAddress(address);
        frStore.setDistrict(frDistrictOptional.get());
        frStore.setLongitude(longitude);
        frStore.setLatitude(latitude);
        frStore.setRatingCount(0);
        frStore.setCreateTime(time);
        frStore.setNote(note);
        frStore.setStatus(Fix.STO_NEW);
        frStore.setEditor(methods.getUser());
        storeRepository.save(frStore);
        return true;
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
        frStore.setStatus(Fix.STO_REV);
        frStore.setEditor(methods.getUser());
        storeRepository.save(frStore);
        return true;
    }

    public boolean updateStore(Integer storeId, String name, String address, Integer distId, Double longitude, Double latitude, String phone, String note) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        Validator valid = new Validator();

        Optional<FRStore> optional = storeRepository.findById(storeId);
        if (!optional.isPresent()) {
            return false;
        }
        FRStore frStore = optional.get();
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
        return true;
    }

    public List<MdlStoreReadNear> getStoreNearby(double longitude, double latitude) {
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

    public List<MdlStoreReadNear> findAll() {
        List<FRStore> frStoreList = storeRepository.findAll();
        List<MdlStoreReadNear> storeList = new ArrayList<>();
        for (FRStore frStore : frStoreList) {
            MdlStoreReadNear mdlStore = new MdlStoreReadNear();
            mdlStore.setAddress(frStore.getAddress());
            mdlStore.setId(frStore.getId());
            mdlStore.setImage(Base64.encodeBase64String(frStore.getStoreImage()));
            mdlStore.setName(frStore.getStoreName());
            mdlStore.setRating(frStore.getRating() != null ? frStore.getRating() : 0.0);
            mdlStore.setStatus(frStore.getStatus());

            storeList.add(mdlStore);
        }

        return storeList;
    }
}
