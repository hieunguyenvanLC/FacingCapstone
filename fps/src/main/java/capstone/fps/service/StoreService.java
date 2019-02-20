package capstone.fps.service;

import capstone.fps.common.ConstantList;
import capstone.fps.common.Methods;
import capstone.fps.entity.FRDistrict;
import capstone.fps.entity.FRStore;
import capstone.fps.repository.DistrictRepository;
import capstone.fps.repository.StoreRepository;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class StoreService {
    private StoreRepository storeRepository;
    private DistrictRepository districtRepository;

    public StoreService(StoreRepository storeRepository, DistrictRepository districtRepository) {
        this.storeRepository = storeRepository;
        this.districtRepository = districtRepository;
    }


    public FRStore createStore(String name, String address, Integer distId) {
        Methods methods = new Methods();
        long time = methods.getTimeNow();
        FRStore frStore = new FRStore();

        frStore.setStoreName(name);
        frStore.setAddress(address);
        FRDistrict frDistrict = districtRepository.findById(distId).get();
        frStore.setDistrict(frDistrict);

        frStore.setRatingCount(0);
        frStore.setStatus(ConstantList.STO_NEW);
        frStore.setCreateTime(time);
        frStore.setUpdateTime(time);
        storeRepository.save(frStore);
        return frStore;
    }


//    public FRStore createStore(String name, String address, Integer distId) {
//        Methods methods = new Methods();
//        long time = methods.getTimeNow();
//        FRStore frStore = new FRStore();
//
//        frStore.setStoreName(name);
//        frStore.setAddress(address);
//        FRDistrict frDistrict = districtRepository.findById(distId).get();
//        frStore.setDistrict(frDistrict);
//
//        frStore.setRatingCount(0);
//        frStore.setStatus(ConstantList.STO_NEW);
//        frStore.setCreateTime(time);
//        frStore.setUpdateTime(time);
//        storeRepository.save(frStore);
//        return frStore;
//    }




}
