package capstone.fps.repository;

import capstone.fps.entity.FRStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<FRStore,Integer> {
}
