package capstone.fps.repository;

import capstone.fps.entity.FROrder;
import capstone.fps.entity.FRStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderStatusRepository extends JpaRepository<FRStatus, Integer> {

    Optional<FRStatus> findByName(String name);
}
