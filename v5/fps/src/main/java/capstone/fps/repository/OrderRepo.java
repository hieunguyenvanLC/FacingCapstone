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

    public Integer countByStatusAndCreateTimeGreaterThanEqualAndCreateTimeLessThanEqual(Integer status, Long start, Long end);

    public Integer countByStatusAndCreateTimeGreaterThanAndCreateTimeLessThan(Integer status, Long start, Long end);

    public Integer countByStatus(Integer status);

    public Integer countByCreateTimeGreaterThanEqualAndCreateTimeLessThan(Long start, Long end);

    public Integer countAllBy();

    public Integer countByStatusAndCreateTimeLessThan(Integer status, Long time);

    List<FROrder> findAllByStatus(Integer status);

    @Query(value = "SELECT SUM(od.quantity) FROM fr_order AS o JOIN fr_order_detail AS od ON o.id = od.FR_Order_id WHERE o.status = :stt AND o.create_time >= :start AND o.create_time < :end", nativeQuery = true)
    public Integer sumSoldProduct(@Param("stt") Integer status, @Param("start") Long start, @Param("end") Long end);

    public List<FROrder> findByStatusAndCreateTimeGreaterThanEqualAndCreateTimeLessThan(Integer status, Long start, Long end);

    public List<FROrder> findByCreateTimeGreaterThanEqualAndCreateTimeLessThan(Long start, Long end);

    @Query(value = "SELECT SUM(o.shipper_earn) FROM fr_order AS o WHERE o.status = :stt AND o.create_time >= :start AND o.create_time < :end", nativeQuery = true)
    public Integer sumTotalAmount(@Param("stt") Integer status, @Param("start") Long start, @Param("end") Long end);

//    @Query(value = "select sum(o.shipper_earn * sp.price) " +
//            "from (fr_order as o join fr_shipper as s on (o.FR_Shipper_id = s.id and o.status = :stt and o.create_time >= :start and o.create_time < :end)) " +
//            "join fr_price_level as sp on s.FR_Price_Level_id = sp.id", nativeQuery = true)
//    public Integer sumShipperEarn(@Param("stt") Integer status, @Param("start") Long start, @Param("end") Long end);

    @Query(value = "select sum(o.shipper_earn * o.price_level) " +
            "from fr_order as o " +
            "where o.status = :stt and o.create_time >= :start and o.create_time < :end", nativeQuery = true)
    public Integer sumShipperEarn(@Param("stt") Integer status, @Param("start") Long start, @Param("end") Long end);
}

