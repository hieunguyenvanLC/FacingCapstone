package capstone.fps.repository;

import capstone.fps.entity.FRCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<FRCity,Integer> {
}
