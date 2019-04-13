package capstone.fps.repository;

import capstone.fps.entity.FRAccount;
import capstone.fps.entity.FROrder;
import capstone.fps.entity.FRShipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<FROrder, Integer> {

    List<FROrder> findAllByAccountAndStatusIn(FRAccount account, int[] status);

    List<FROrder> findAllByAccount(FRAccount account);

    List<FROrder> findAllByShipper(FRShipper shipper);
}
