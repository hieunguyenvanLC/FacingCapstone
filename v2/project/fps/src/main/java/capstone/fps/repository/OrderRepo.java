package capstone.fps.repository;

import capstone.fps.entity.FROrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<FROrder, Integer> {

//    @Query("SELECT SUM(total_days) FROM MyEntity")
//    Float selectTotals();

//    @Query("SELECT COUNT(o) FROM fr_order as o WHERE o.status = 4 AND o.create_time >= ")
//    public Integer countOrderByStatusAndCreateTime(@Param("timestamp") Integer timestamp);

    public Integer countByStatusAndCreateTimeGreaterThanEqual(Integer status, Long timestamp);

    public Integer countByStatus(Integer status);

    public Integer countByCreateTimeGreaterThanEqual(Long timestamp);
}
