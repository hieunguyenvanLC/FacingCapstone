package capstone.fps.repository;

import capstone.fps.entity.FROrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<FROrderDetail, Integer> {
}
