package capstone.fps.repository;

import capstone.fps.entity.FRProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<FRProduct, Integer> {

    @Override
    Optional<FRProduct> findById(Integer integer);
}
