package capstone.fps.repository;

import capstone.fps.entity.FRStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepo extends JpaRepository<FRStore,Integer> {
    public int countByStatus(int status);

    public int countByStatusAndCreateTimeGreaterThanAndCreateTimeLessThan(int status, long start, long end);
}