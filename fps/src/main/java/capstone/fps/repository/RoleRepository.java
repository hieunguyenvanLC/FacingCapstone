package capstone.fps.repository;

import capstone.fps.entity.FRRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<FRRole,Integer> {

    Optional<FRRole> findByName(String name);
}
