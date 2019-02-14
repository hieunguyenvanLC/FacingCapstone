package capstone.fps.repository;

import capstone.fps.entity.FROrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<FROrder,Integer> {


}
