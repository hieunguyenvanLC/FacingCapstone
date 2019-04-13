package capstone.fps.repository;

import capstone.fps.entity.FRProduct;
import capstone.fps.entity.FRStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<FRProduct, Integer> {

//    List<FRProduct> findAllByStatusOrderByRatingDesc(int status);

    List<FRProduct> findAllByStoreAndStatusNotOrderByUpdateTimeDesc(FRStore store, int status);

    List<FRProduct> findAllByStore(FRStore store);
}
