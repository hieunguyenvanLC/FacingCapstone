package capstone.fps.repository;

import capstone.fps.entity.FRAccount;
import capstone.fps.entity.FROrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<FROrder, Integer> {

    List<FROrder> findAllByAccountAndStatusIn(FRAccount account, int[] status);

    public Integer countByStatusAndCreateTimeGreaterThanEqualAndCreateTimeLessThan(Integer status, Long start, Long end);

    public Integer countByStatus(Integer status);

    public Integer countByCreateTimeGreaterThanEqualAndCreateTimeLessThan(Long start, Long end);

    public Integer countAllBy();

    List<FROrder> findAllByStatus(Integer status);

    @Query(value = "SELECT SUM(od.quantity) FROM fr_order AS o JOIN fr_order_detail AS od ON o.id = od.FR_Order_id WHERE o.status = :stt AND o.create_time >= :start AND o.create_time < :end", nativeQuery = true)
    public Integer sumSoldProduct(@Param("stt") Integer status, @Param("start") Long start, @Param("end") Long end);
}
