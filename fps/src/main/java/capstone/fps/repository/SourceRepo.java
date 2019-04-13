package capstone.fps.repository;

import capstone.fps.entity.FRSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceRepo extends JpaRepository<FRSource, Integer> {

}
