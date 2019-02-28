package capstone.fps.repository;

import capstone.fps.entity.FRShipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipperRepo extends JpaRepository<FRShipper, Integer> {

}
