package capstone.fps.repository;

import capstone.fps.entity.FROrder;
import capstone.fps.entity.FROrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepo extends JpaRepository<FROrderDetail, Integer> {

    List<FROrderDetail> findAllByOrder(FROrder order);

    FROrderDetail findFirstByOrder(FROrder frOrder);
}
