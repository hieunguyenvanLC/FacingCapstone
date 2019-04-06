package capstone.fps.service;

import capstone.fps.entity.FRCity;
import capstone.fps.entity.FRDistrict;
import capstone.fps.model.district.MdlCity;
import capstone.fps.model.district.MdlDistrict;
import capstone.fps.repository.CityRepo;
import capstone.fps.repository.DistrictRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityService {
    private CityRepo cityRepository;
    private DistrictRepo districtRepository;

    public CityService(CityRepo cityRepository, DistrictRepo districtRepository) {
        this.cityRepository = cityRepository;
        this.districtRepository = districtRepository;
    }

    public List<MdlCity> getCityList() {
        List<MdlCity> mdlCityList = new ArrayList<>();
        List<FRCity> frCityList = cityRepository.findAll();
        for (FRCity frCity : frCityList) {
            MdlCity mdlCity = new MdlCity(frCity.getId(), frCity.getName());
            List<FRDistrict> frDistList = districtRepository.findAllByCity(frCity);
            if (!frDistList.isEmpty()) {
                MdlDistrict[] mdlDistList = new MdlDistrict[frDistList.size()];
                for (int i = 0; i < frDistList.size(); i++) {
                    FRDistrict frDistrict = frDistList.get(i);
                    mdlDistList[i] = new MdlDistrict(frDistrict.getId(), frDistrict.getName());
                }
                mdlCity.setDistList(mdlDistList);
                mdlCityList.add(mdlCity);
            }
        }
        return mdlCityList;
    }
}
