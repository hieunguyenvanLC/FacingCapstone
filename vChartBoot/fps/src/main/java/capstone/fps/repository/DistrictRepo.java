package capstone.fps.repository;

import capstone.fps.entity.FRAccount;
import capstone.fps.entity.FRCity;
import capstone.fps.entity.FRDistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistrictRepo extends JpaRepository<FRDistrict, Integer> {


    List<FRDistrict> findAllByCity(FRCity city);
}
