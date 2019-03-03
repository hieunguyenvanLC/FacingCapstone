package capstone.fps.repository;

import capstone.fps.entity.FRPriceLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceLevelRepo extends JpaRepository<FRPriceLevel, Integer> {
}
