package capstone.fps.repository;

import capstone.fps.entity.FRAccount;
import capstone.fps.entity.FRRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<FRAccount, Integer> {


    Optional<FRAccount> findByPhone(String phone);

    List<FRAccount> findAllByRole(FRRole role);

    int countByRoleAndStatus(FRRole role, int status);

    int countByRoleAndStatusAndCreateTimeGreaterThanEqualAndCreateTimeLessThan(FRRole role, int status, long start, long end);
}
