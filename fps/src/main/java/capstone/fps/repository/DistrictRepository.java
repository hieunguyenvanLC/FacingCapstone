package capstone.fps.repository;

import capstone.fps.entity.FRAccount;
import capstone.fps.entity.FRDistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<FRDistrict,Integer> {

    @Override
    Optional<FRDistrict> findById(Integer integer);
}
