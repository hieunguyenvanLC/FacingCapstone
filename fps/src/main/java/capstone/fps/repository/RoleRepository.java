package capstone.fps.repository;

import capstone.fps.entity.FRRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<FRRole, Integer> {

    FRRole findByName(String name);
}
